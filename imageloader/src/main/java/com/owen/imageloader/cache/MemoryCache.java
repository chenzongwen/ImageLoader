package com.owen.imageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.owen.imageloader.target.Target;

/**
 * Created by Owen Chan
 * On 2017-10-23.
 */

public class MemoryCache implements BitmapCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache() {

        final int totalMemory = (int) (Runtime.getRuntime().totalMemory() / 1024);

        int cacheSize = totalMemory / 4;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public synchronized Bitmap get(Target key) {
        return mMemoryCache.get(key.imgUri);
    }

    @Override
    public synchronized void put(Target key, Bitmap bitmap) {
        mMemoryCache.put(key.imgUri, bitmap);
    }

    @Override
    public synchronized void remove(Target key) {
        mMemoryCache.remove(key.imgUri);
    }
}
