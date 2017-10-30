package com.owen.imageloader.cache;

import android.graphics.Bitmap;

import com.owen.imageloader.target.Target;

/**
 * Created by Owen Chan
 * On 2017-10-25.
 */

public class NullCache implements BitmapCache {

    @Override
    public Bitmap get(Target key) {
        return null;
    }

    @Override
    public void put(Target key, Bitmap value) {
        //do nothing
    }

    @Override
    public void remove(Target key) {
        //do nothing
    }
}
