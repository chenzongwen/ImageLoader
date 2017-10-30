package com.owen.imageloader.config;

import com.owen.imageloader.cache.BitmapCache;
import com.owen.imageloader.cache.MemoryCache;
import com.owen.imageloader.policy.LoadPolicy;
import com.owen.imageloader.policy.SerialPolicy;

/**
 * Created by Owen Chan
 * On 2017-10-24.
 */

public class ImageLoaderConfig {

    private BitmapCache mBitmapCache = new MemoryCache();

    private LoadPolicy mLoadPolicy = new SerialPolicy();

    public ImageLoaderConfig setBitmapCache(BitmapCache bitmapCache) {
        mBitmapCache = bitmapCache;
        return this;
    }

    public ImageLoaderConfig setLoadPolicy(LoadPolicy loadPolicy) {
        mLoadPolicy = loadPolicy;
        return this;
    }

    public BitmapCache getBitmapCache() {
        return mBitmapCache;
    }

    public LoadPolicy getLoadPolicy() {
        return mLoadPolicy;
    }
}
