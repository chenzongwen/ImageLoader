package com.owen.imageloader.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.owen.imageloader.target.Target;

/**
 * Created by Owen Chan
 * On 2017-10-25.
 */

public class DoubleCache implements BitmapCache {

    private DiskCache mDiskCache;

    private MemoryCache mMemoryCache = new MemoryCache();

    public DoubleCache(Context context) {
        mDiskCache = DiskCache.getInstance(context);
    }

    @Override
    public synchronized Bitmap get(Target key) {
        Bitmap bitmap = mMemoryCache.get(key);
        if (bitmap == null) {
            bitmap = mDiskCache.get(key);
            if (bitmap != null) {
                mMemoryCache.put(key, bitmap);
            }
        }
        return bitmap;
    }

    @Override
    public synchronized void put(Target key, Bitmap value) {
        mDiskCache.put(key, value);
        mMemoryCache.put(key, value);
    }

    @Override
    public synchronized void remove(Target key) {
        mDiskCache.remove(key);
        mMemoryCache.remove(key);
    }
}
