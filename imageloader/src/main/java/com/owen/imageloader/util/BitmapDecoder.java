package com.owen.imageloader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Owen Chan
 * On 2017-10-23.
 */

public abstract class BitmapDecoder {

    public abstract Bitmap decodeBitmapWithOption(BitmapFactory.Options options);

    public final Bitmap decodeBitmap(int width, int height) {
        if (width <= 0 || height <= 0) {
            return decodeBitmapWithOption(null);
        }

        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        decodeBitmapWithOption(options);

        configBitmapOptions(options, width, height);

        return decodeBitmapWithOption(options);

    }

    public int configBitmapOptions(BitmapFactory.Options options, int desWidth, int desHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;

        if (height > desHeight || width > desHeight) {
            final int heightRatio = Math.round((float) height / (float) desHeight);
            final int widthRatio = Math.round((float) width / (float) desWidth);

            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;

            final float totalPixels = width * height;

            final float totalReqPixelCap = desHeight * desWidth * 2;
             while (totalPixels / (inSampleSize * inSampleSize ) > totalReqPixelCap) {
                 inSampleSize ++;
             }
        }
        return inSampleSize;
    }

}
