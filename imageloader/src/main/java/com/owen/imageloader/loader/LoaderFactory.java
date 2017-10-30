package com.owen.imageloader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Owen Chan
 * On 2017-10-23.
 */

public class LoaderFactory {

    private static LoaderFactory INSTANCE;

    public static final String HTTP = "http";

    public static final String FILE = "file";

    private BaseLoader mNullBaseLoader = new NullLoader();

    private static Map<String, BaseLoader> mLoaderMap = new HashMap<>();

    static {
        mLoaderMap.put(HTTP, new UrlLoader());
        mLoaderMap.put(FILE, new NativeLoader());
    }

    private LoaderFactory() {

    }

    public static LoaderFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (LoaderFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LoaderFactory();
                }
            }
        }
        return INSTANCE;
    }

    public BaseLoader getLoader(String schema) {
        if (mLoaderMap.containsKey(schema)) {
            return mLoaderMap.get(schema);
        }
        return mNullBaseLoader;
    }
}
