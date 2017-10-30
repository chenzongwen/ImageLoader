package com.owen.imageloader.cache;

import android.graphics.Bitmap;

import com.owen.imageloader.target.Target;

/**
 * Created by Owen Chan
 * On 2017-10-20.
 */

public interface BitmapCache {

    Bitmap get(Target key);

    void put(Target key, Bitmap value);

    void remove(Target key);
}
