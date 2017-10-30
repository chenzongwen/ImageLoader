package com.owen.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import com.owen.imageloader.target.Target;
import com.owen.imageloader.util.BitmapDecoder;

import java.io.File;

/**
 * Created by Owen Chan
 * On 2017-10-24.
 */

public class NativeLoader extends AbsLoader {

    @Override
    protected Bitmap onLoadBitmap(Target request) {
        final String imagePath = Uri.parse(request.imgUri).getPath();
        File imgFile = new File(imagePath);
        if (!imgFile.exists()) {
            return null;
        }
        request.justCacheInMem = true;
        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                return BitmapFactory.decodeFile(imagePath, options);
            }
        };
        return decoder.decodeBitmap(request.getImageWidth(), request.getImageHeight());
    }
}
