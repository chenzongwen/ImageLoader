package com.owen.imageloader.loader;

import android.graphics.Bitmap;
import android.util.Log;

import com.owen.imageloader.target.Target;

/**
 * Created by Owen Chan
 * On 2017-10-24.
 */

public class NullLoader extends AbsLoader {

    @Override
    protected Bitmap onLoadBitmap(Target request) {
        Log.e(getClass().getSimpleName(), "wrong schema uri is " + request.imgUri);
        return null;
    }
}
