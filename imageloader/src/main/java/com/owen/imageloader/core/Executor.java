package com.owen.imageloader.core;

import com.owen.imageloader.loader.BaseLoader;
import com.owen.imageloader.loader.LoaderFactory;
import com.owen.imageloader.target.Target;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Owen Chan
 * On 2017-10-24.
 */

public class Executor extends Thread {

    private BlockingQueue<Target> mTargetQueue;

    Executor(BlockingQueue<Target> targetQueue) {
        mTargetQueue = targetQueue;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                final Target request  = mTargetQueue.take();
                if (request.isCancelled) {
                    continue;
                }
                String target = parseSchema(request.imgUri);
                BaseLoader imgBaseLoader = LoaderFactory.getInstance().getLoader(target);
                imgBaseLoader.loadImage(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String parseSchema(String uri) {
        if (uri.contains("://")) {
            return uri.split("://")[0];
        }
        return "";
    }
}
