package com.owen.imageloader.core;

import com.owen.imageloader.target.Target;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Owen Chan
 * On 2017-10-24.
 */

public final class TargetQueue {

    private BlockingQueue<Target> mRequestQueue = new PriorityBlockingQueue<>();

    private AtomicInteger mAtomicInteger = new AtomicInteger(0);

    private static int CORE_NUM = Runtime.getRuntime().availableProcessors();

    private int mDispatchNum = CORE_NUM;

    private Executor[] mDispatchers = null;


    public TargetQueue() {

    }

    private void startDispatchers() {
        mDispatchers = new Executor[mDispatchNum];
        for (int i = 0; i < mDispatchNum; i++) {
            mDispatchers[i] = new Executor(mRequestQueue);
            mDispatchers[i].start();
        }
    }


    public void addRequest(Target request) {
        if (!mRequestQueue.contains(request)) {
            request.sequenceNumber = mAtomicInteger.incrementAndGet();
            mRequestQueue.add(request);
        }
    }

    /**
     * 开始请求图片
     */
    public void start() {
        stop();
        startDispatchers();
    }

    /**
     * 停止所有的请求
     */
    public void stop() {
        if (mDispatchers != null && mDispatchers.length > 0) {
            for (Executor dispatcher : mDispatchers) {
                dispatcher.interrupt();
            }
        }
    }

    public BlockingQueue<Target> getAllRequest() {
        return mRequestQueue;
    }

    public void clear() {
        mRequestQueue.clear();
    }

}





















