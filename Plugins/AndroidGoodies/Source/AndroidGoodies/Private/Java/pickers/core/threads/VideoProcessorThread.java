// Copyright (c) 2021 Nineva Studios

package com.ninevastudios.androidgoodies.pickers.core.threads;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import com.ninevastudios.androidgoodies.pickers.api.callbacks.VideoPickerCallback;
import com.ninevastudios.androidgoodies.pickers.api.entity.ChosenFile;
import com.ninevastudios.androidgoodies.pickers.api.entity.ChosenVideo;
import com.ninevastudios.androidgoodies.pickers.api.exceptions.PickerException;
import com.ninevastudios.androidgoodies.pickers.utils.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.ninevastudios.androidgoodies.pickers.utils.StreamHelper.close;
import static com.ninevastudios.androidgoodies.pickers.utils.StreamHelper.flush;

public final class VideoProcessorThread extends FileProcessorThread {

    private final static String TAG = VideoProcessorThread.class.getSimpleName();

    private VideoPickerCallback callback;
    private boolean shouldGenerateMetadata;
    private boolean shouldGeneratePreviewImages;
    private int quality = 100;

    public VideoProcessorThread(Context context, List<? extends ChosenFile> files, int cacheLocation) {
        super(context, files, cacheLocation);
    }

    public void setVideoPickerCallback(VideoPickerCallback callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        super.run();
        postProcessVideos();
        onDone();
    }

    private void postProcessVideos() {
        for (ChosenFile file : files) {
            ChosenVideo video = (ChosenVideo) file;
            try {
                postProcessVideo(video);
                video.setSuccess(true);
            } catch (PickerException e) {
                e.printStackTrace();
                video.setSuccess(false);
            }
        }
    }

    private void postProcessVideo(ChosenVideo video) throws PickerException {
        if (shouldGenerateMetadata) {
            MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();
            try {
                metadataRetriever.setDataSource(video.getOriginalPath());
                String duration = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                String orientation = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    orientation = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
                }
                String height = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    height = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
                }
                String width = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    width = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
                }

                if (duration != null) {
                    video.setDuration(Long.parseLong(duration));
                }
                if (orientation != null) {
                    video.setOrientation(Integer.parseInt(orientation));
                }

                if (height != null) {
                    video.setHeight(Integer.parseInt(height));
                }

                if (width != null) {
                    video.setWidth(Integer.parseInt(width));
                }
            } catch (Exception e) {
                LogUtils.d(TAG, "postProcessVideo: Error generating metadata");
                e.printStackTrace();
            } finally {
            try {
                metadataRetriever.release();
            }  catch (Exception e) {
            }
            }
        }

        if (shouldGeneratePreviewImages) {
            String previewPath = createPreviewImage(video.getOriginalPath());
            video.setPreviewImage(previewPath);
            String previewThumbnail = downScaleAndSaveImage(previewPath, THUMBNAIL_BIG, quality);
            String previewThumbnailSmall = downScaleAndSaveImage(previewPath, THUMBNAIL_SMALL, quality);
            video.setPreviewThumbnail(previewThumbnail);
            video.setPreviewThumbnailSmall(previewThumbnailSmall);
        }
    }

    private void onDone() {
        try {
            if (callback != null) {
                getActivityFromContext().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onVideosChosen((List<ChosenVideo>) files);
                    }
                });
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setShouldGenerateMetadata(boolean shouldGenerateMetadata) {
        this.shouldGenerateMetadata = shouldGenerateMetadata;
    }

    public void setShouldGeneratePreviewImages(boolean shouldGeneratePreviewImages) {
        this.shouldGeneratePreviewImages = shouldGeneratePreviewImages;
    }

    private String createPreviewImage(String videoPath) throws PickerException {
        String previewImage;
        previewImage = null;
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath,
                MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
        if (bitmap != null) {
            previewImage = generateFileNameForVideoPreviewImage();
            File file = new File(previewImage);

            FileOutputStream stream = null;
            try {
                stream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            } catch (IOException e) {
                throw new PickerException(e);
            } finally {
                flush(stream);
                close(stream);
            }

        }
        return previewImage;
    }

    public void setThumbnailsImageQuality(int quality) {
        this.quality = quality;
    }
}
