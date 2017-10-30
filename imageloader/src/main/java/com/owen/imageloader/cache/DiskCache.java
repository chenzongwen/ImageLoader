package com.owen.imageloader.cache;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.jakewharton.disklrucache.DiskLruCache;
import com.owen.imageloader.target.Target;
import com.owen.imageloader.util.BitmapDecoder;
import com.owen.imageloader.util.Md5Helper;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Owen Chan
 * On 2017-10-23.
 */

public class DiskCache implements BitmapCache {

    private static final String DISK_IMG_PATH = "disk_cache_path";

    private DiskLruCache mDiskLruCache;

    private static DiskCache sDiskCache;

    private DiskCache(Context context) {
        initial(context);
    }

    public static DiskCache getInstance(Context context) {
        if (sDiskCache == null) {
            synchronized (DiskCache.class) {
                if (sDiskCache == null) {
                    sDiskCache = new DiskCache(context);
                }
            }
        }
        return sDiskCache;
    }

    private void initial(Context context) {
        try {
            File cacheDir =  getDiskCacheDir(context, DISK_IMG_PATH);
            if (!cacheDir.exists()) {
                boolean success = cacheDir.mkdir();
            }
            int versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
            mDiskLruCache = DiskLruCache.open(cacheDir, versionCode, 1, 50 * 1024 * 1024);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = null;
        try {
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                cachePath = context.getExternalCacheDir().getPath();
            } else {
                cachePath = context.getCacheDir().getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    @Override
    public Bitmap get(final Target request) {
        BitmapDecoder decoder = new BitmapDecoder() {
            @Override
            public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                final InputStream inputStream = getInputStream(request.imgUriMd5);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }
        };
        return decoder.decodeBitmap(0, 0);
    }

    @Override
    public void put(Target target, Bitmap value) {
        if (target.justCacheInMem) {
            return;
        }
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(target.imgUriMd5);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                if (writeBitmap2Disk(value, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Target key) {
        try {
            mDiskLruCache.remove(Md5Helper.toMD5(key.imgUriMd5));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean writeBitmap2Disk(Bitmap bitmap, OutputStream outputStream) {
        BufferedOutputStream bos = new BufferedOutputStream(outputStream, 8 * 1024);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        boolean result = true;
        try {
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private InputStream getInputStream (String md5)  {
        DiskLruCache.Snapshot snapshot;
        try {
            snapshot = mDiskLruCache.get(md5);
            if (snapshot != null) {
                return snapshot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}













