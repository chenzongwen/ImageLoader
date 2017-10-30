package com.owen.imageloader.loader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.owen.imageloader.cache.BitmapCache;
import com.owen.imageloader.util.ImageLoaderUtil;
import com.owen.imageloader.target.Target;

/**
 * Created by Owen Chan
 * On 2017-10-24.
 */

public abstract class AbsLoader implements BaseLoader {

    private static BitmapCache mCache = ImageLoaderUtil.getInstance().getConfig().getBitmapCache();

    @Override
    public void loadImage(Target request) {
        Bitmap resultBitmap = mCache.get(request);

        if (resultBitmap == null) {
            showLoading(request);
            resultBitmap = onLoadBitmap(request);
            cacheBitmap(request, resultBitmap);
        } else {
            request.justCacheInMem = true;
        }
        delivery2UiThread(request, resultBitmap);
    }

    protected abstract Bitmap onLoadBitmap(Target request);

    private void cacheBitmap(Target request, Bitmap bitmap) {
        if (bitmap != null && mCache != null) {
            synchronized (AbsLoader.class) {
                mCache.put(request, bitmap);
            }
        }
    }

    private void showLoading(final Target request) {
        final ImageView imageView = request.getImageView();
        if (request.isImageViewTagValid() && request.loadingRes != 0) {
            imageView.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(request.loadingRes);
                }
            });
        }
    }

    private void delivery2UiThread(final Target request, final Bitmap bitmap) {
        final ImageView imageView = request.getImageView();
        if (imageView == null) {
            return;
        }
        imageView.post(new Runnable() {
            @Override
            public void run() {
                updateImageView(request, bitmap);
            }
        });
    }

    private void updateImageView(Target request, Bitmap bitmap) {
        final  ImageView imageView = request.getImageView();
        final String uri = request.imgUri;
        if (bitmap != null && imageView.getTag().equals(uri)) {
            imageView.setImageBitmap(bitmap);
        }

        if (bitmap == null && request.failedRes != 0) {
            imageView.setImageResource(request.failedRes);
        }
        if (request.mImageListener != null) {
            request.mImageListener.onComplete(imageView, bitmap, uri);
        }
    }
}
