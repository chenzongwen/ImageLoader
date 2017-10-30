package com.owen.simpleimageloader;

import android.app.Application;

import com.owen.imageloader.cache.MemoryCache;
import com.owen.imageloader.config.ImageLoaderConfig;
import com.owen.imageloader.policy.ReversePolicy;
import com.owen.imageloader.util.ImageLoaderUtil;

/**
 * Created by Owen Chan
 * On 2017-10-27.
 */

public class ClientApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfig imageLoaderConfig = new ImageLoaderConfig()
                .setBitmapCache(new MemoryCache())
                .setLoadPolicy(new ReversePolicy());

        ImageLoaderUtil.getInstance().init(imageLoaderConfig);
    }
}
