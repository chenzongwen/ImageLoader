package com.owen.imageloader.target;

import android.widget.ImageView;

import com.owen.imageloader.policy.LoadPolicy;
import com.owen.imageloader.policy.ReversePolicy;
import com.owen.imageloader.util.ImageLoaderUtil;

/**
 * Created by Owen Chan
 * On 2017-10-27.
 */

public class TargetParams {

    private LoadPolicy mLoadPolicy = new ReversePolicy();
    private ImageView mImageView;
    private String uri;
    private ImageLoaderUtil.ImageListener mImageListener;

    private int loadingRes = 0;
    private int failedRes = 0;

    public TargetParams setImageView(ImageView imageView) {
        mImageView = imageView;
        return this;
    }

    public ImageView getImageView() {
        return mImageView;
    }

    public ImageLoaderUtil.ImageListener getImageListener() {
        return mImageListener;
    }

    public TargetParams setImageListener(ImageLoaderUtil.ImageListener imageListener) {
        mImageListener = imageListener;
        return this;
    }

    public TargetParams setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public TargetParams setLoadingRes(int loadingRes) {
        this.loadingRes = loadingRes;
        return this;
    }

    public int getLoadingRes() {
        return loadingRes;
    }

    public TargetParams setFailedRes(int failedRes) {
        this.failedRes = failedRes;
        return this;
    }

    public int getFailedRes() {
        return failedRes;
    }

    public void setLoadPolicy(LoadPolicy loadPolicy) {
        mLoadPolicy = loadPolicy;
    }

    public LoadPolicy getLoadPolicy() {
        return mLoadPolicy;
    }
}
