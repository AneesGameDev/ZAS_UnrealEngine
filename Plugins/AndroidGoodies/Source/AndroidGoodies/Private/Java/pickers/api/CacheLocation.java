// Copyright (c) 2021 Nineva Studios

package com.ninevastudios.androidgoodies.pickers.api;
/**
 * Cache Locations for the imported files, thumbnails of images, preview images of videos etc
 */
public interface CacheLocation {
    /**
     * Save files directly under various folders of the external storage,
     * ex: /sdcard/App Name/App Name Pictures/, /sdcard/App Name/App Name/Videos, /sdcard/App Name/App Name Files etc
     *
     * These files won't be deleted when the application is deleted.
     *
     * This will be eventually deprecated due to security reasons in Android Nougat.
     * Please use another cache location, which are more restricted for other applications to read from.
     */

    @Deprecated
    int EXTERNAL_STORAGE_PUBLIC_DIR = 100;
    /**
     * Save files under your application's files directory on the external storage
     * ex: /sdcard/Android/data/your_package/Pictures, /sdcard/Android/data/your_package/Videos
     * <p/>
     * These files will be deleted when your app is uninstalled.
     * These files are internal to the applications, and not typically visible to the user as media.
     *
     */
    int EXTERNAL_STORAGE_APP_DIR = 200;
    /**
     * Save files under your application's cache directory on the external storage
     * ex: /sdcard/Android/data/your_package/cache/
     * <p/>
     * These files will be ones that get deleted first when the device runs low on storage.
     * There is no guarantee when these files will be deleted.
     */
    int EXTERNAL_CACHE_DIR = 300;
    /**
     * Save files under your application's internal storage directory.
     */
    int INTERNAL_APP_DIR = 400;
}
