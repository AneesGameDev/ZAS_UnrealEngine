// Copyright (c) 2021 Nineva Studios

package com.ninevastudios.androidgoodies.pickers.api;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.ninevastudios.androidgoodies.pickers.api.exceptions.PickerException;
import com.ninevastudios.androidgoodies.pickers.core.ImagePickerImpl;

/**
 * Choose an image(s) on your device. Gallery, Google Photos, Dropbox etc.
 */
public class ImagePicker extends ImagePickerImpl {
    /**
     * Constructor for choosing an image from an {@link Activity}
     * @param activity
     */
    public ImagePicker(Activity activity) {
        super(activity, Picker.PICK_IMAGE_DEVICE);
    }

    /**
     * Constructor for choosing an image from a {@link Fragment}
     * @param fragment
     */
    public ImagePicker(Fragment fragment) {
        super(fragment, Picker.PICK_IMAGE_DEVICE);
    }

    /**
     * Constructor for choosing an image from a {@link android.app.Fragment}
     * @param appFragment
     */
    public ImagePicker(android.app.Fragment appFragment) {
        super(appFragment, Picker.PICK_IMAGE_DEVICE);
    }

    /**
     * Allows you to select multiple images at once. This will only work for the applications that
     * support multiple image selection.
     */
    public void allowMultiple() {
        this.allowMultiple = true;
    }

    /**
     * Triggers Image selection
     */
    public void pickImage() {
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
