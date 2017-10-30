package com.owen.imageloader.target;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.owen.imageloader.util.ImageLoaderUtil;
import com.owen.imageloader.policy.LoadPolicy;
import com.owen.imageloader.util.ImageViewHelper;
import com.owen.imageloader.util.Md5Helper;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by Owen Chan
 * On 2017-10-23.
 */

public class Target implements Comparable<Target> {

    private Reference<ImageView> imageViewTarget;

    public ImageLoaderUtil.ImageListener mImageListener;

    public boolean isCancelled = false;

    public boolean justCacheInMem = false;

    public String imgUri = null;
    public String imgUriMd5 = null;

    public int loadingRes;
    public int failedRes;

    public int sequenceNumber = 0;



    private LoadPolicy mLoadPolicy;

    public Target(TargetParams option) {
        imageViewTarget = new WeakReference<>(option.getImageView());
        mImageListener = option.getImageListener();
        loadingRes = option.getLoadingRes();
        failedRes = option.getFailedRes();
        imgUri = option.getUri();
        option.getImageView().setTag(imgUri);
        imgUriMd5 = Md5Helper.toMD5(imgUri);
        mLoadPolicy = option.getLoadPolicy();
    }


    public boolean isImageViewTagValid() {
        return imageViewTarget.get() != null && imageViewTarget.get().getTag().equals(imgUri);
    }

    public ImageView getImageView() {
        return imageViewTarget.get();
    }

    public int getImageWidth() {
        return ImageViewHelper.getImageViewWidth(imageViewTarget.get());
    }

    public int getImageHeight() {
        return ImageViewHelper.getImageViewHeight(imageViewTarget.get());
    }

    @Override
    public int compareTo(@NonNull Target o) {
        return mLoadPolicy.compare(this, o);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((imgUri == null) ? 0 : imgUri.hashCode());
        result = prime * result + ((imageViewTarget == null) ? 0 : imageViewTarget.get().hashCode());
        result = prime * result + sequenceNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Target other = (Target) obj;
        if (imgUri == null) {
            if (other.imgUri != null) {
                return false;
            }
        } else if (!imgUri.equals(other.imgUri)) {
            return false;
        }
        if (imageViewTarget == null) {
            if (other.imageViewTarget != null)
                return false;
        } else if (!imageViewTarget.get().equals(other.imageViewTarget.get())) {
            return false;
        }
        return sequenceNumber != other.sequenceNumber;
    }
}
