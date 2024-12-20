// Copyright (c) 2021 Nineva Studios

package com.ninevastudios.androidgoodies.pickers.utils;

public class BitmapUtils {
    public static int[] getScaledDimensions(int originalWidth, int originalHeight, int maxWidth, int maxHeight) {
        int[] values = new int[2];
        if (originalWidth <= maxWidth && originalHeight <= maxHeight) {
            values[0] = originalWidth;
            values[1] = originalHeight;
        } else {
            if (originalHeight >= maxHeight && originalWidth <= maxWidth) {
                int outHeight = maxHeight;
                float ratio = (float) originalWidth / originalHeight;
                int outWidth = (int) (ratio * (float) outHeight);
                values[0] = outWidth;
                values[1] = outHeight;
            } else if (originalWidth >= maxWidth && originalHeight <= maxHeight) {
                int outWidth = maxWidth;
                float ratio = (float) originalWidth / originalHeight;
                int outHeight = (int) ((float) outWidth / ratio);
                values[0] = outWidth;
                values[1] = outHeight;
            } else if (originalWidth >= maxWidth && originalHeight >= maxHeight) {
                if (originalHeight > maxHeight) {
                    int outHeight = maxHeight;
                    float ratio = (float) originalWidth / originalHeight;
                    int outWidth = (int) (ratio * (float) outHeight);
                    values[0] = outWidth;
                    values[1] = outHeight;
                } else {
                    int outWidth = maxWidth;
                    float ratio = (float) originalWidth / originalHeight;
                    int outHeight = (int) ((float) outWidth / ratio);
                    values[0] = outWidth;
                    values[1] = outHeight;
                }
            }
        }
        return values;
    }
}
