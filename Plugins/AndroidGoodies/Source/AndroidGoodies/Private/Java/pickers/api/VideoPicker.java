// Copyright (c) 2021 Nineva Studios

package com.ninevastudios.androidgoodies.pickers.api;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.ninevastudios.androidgoodies.pickers.api.exceptions.PickerException;
import com.ninevastudios.androidgoodies.pickers.core.VideoPickerImpl;

/**
 * Choose a video on your device. Gallery, Google Photos, Dropbox, Downloads etc.
 */
public class VideoPicker extends VideoPickerImpl {
    /**
     * Constructor to choose a video from an {@link Activity}
     *
     * @param activity
     */
    public VideoPicker(Activity activity) {
        super(activity, Picker.PICK_VIDEO_DEVICE);
    }

    /**
     * Constructor to choose a video from a {@link Fragment}
     *
     * @param fragment
     */
    public VideoPicker(Fragment fragment) {
        super(fragment, Picker.PICK_VIDEO_DEVICE);
    }

    /**
     * Constructor to choose a video from a {@link android.app.Fragment}
     *
     * @param appFragment
     */
    public VideoPicker(android.app.Fragment appFragment) {
        super(appFragment, Picker.PICK_VIDEO_DEVICE);
    }

    /**
     * Default is false. Set this to true if you want to choose multiple videos. This will only work with applications which support multiple video selections
     */
    public void allowMultiple() {
        this.allowMultiple = true;
    }

    /**
     * Triggers video selection
     */
    public void pickVideo() {
        try {
            super.pick();
        } catch (PickerException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onError(e.getMessage());
            }
        }
    }
}
