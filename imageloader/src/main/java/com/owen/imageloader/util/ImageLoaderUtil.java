package com.owen.imageloader.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.owen.imageloader.cache.MemoryCache;
import com.owen.imageloader.config.ImageLoaderConfig;
import com.owen.imageloader.core.TargetQueue;
import com.owen.imageloader.policy.LoadPolicy;
import com.owen.imageloader.policy.ReversePolicy;
import com.owen.imageloader.policy.SerialPolicy;
import com.owen.imageloader.target.Target;
import com.owen.imageloader.target.TargetParams;

/**
 * Created by Owen Chan
 * On 2017-10-23.
 */

public final class ImageLoaderUtil {

    private static ImageLoaderUtil INSTANCE;

    private TargetQueue mTargetQueue;

    private ImageLoaderConfig mImageLoaderConfig;

    private ImageLoaderUtil() {

    }

    /**
     * 单例模式
     * @return 单例
     */
    public static ImageLoaderUtil getInstance() {

        if (INSTANCE == null) {
            synchronized (ImageLoaderUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImageLoaderUtil();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 配置图片加载策略
     */
    public void init(ImageLoaderConfig config) {

        mImageLoaderConfig = config;

        if (mImageLoaderConfig.getLoadPolicy() == null) {
            mImageLoaderConfig.setLoadPolicy(new SerialPolicy());
        }

        if (mImageLoaderConfig.getBitmapCache() == null) {
            mImageLoaderConfig.setBitmapCache(new MemoryCache());
        }

        mTargetQueue = new TargetQueue();
        mTargetQueue.start();
    }

    public void into(TargetParams targetParams) {
        Target target = new Target(targetParams);
        mTargetQueue.addRequest(target);
    }


    public ImageLoaderConfig getConfig() {
        return mImageLoaderConfig;
    }

    public void stop() {
        mTargetQueue.stop();
    }

    public interface ImageListener {
        void onComplete(ImageView imageView, Bitmap bitmap, String uri);
    }

}
