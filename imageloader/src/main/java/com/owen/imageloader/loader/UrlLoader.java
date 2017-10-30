package com.owen.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.owen.imageloader.target.Target;
import com.owen.imageloader.util.IOUtil;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Owen Chan
 * On 2017-10-24.
 */

public class UrlLoader extends AbsLoader {

    @Override
    protected Bitmap onLoadBitmap(Target request) {
        final String imageUrl = request.imgUri;
        InputStream inputStream = null;
        Bitmap bitmap = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(imageUrl);
            connection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(connection.getInputStream());
            bitmap = BitmapFactory.decodeStream(inputStream, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeQuietly(inputStream);
            if (connection != null) {
                connection.disconnect();
            }
        }
        return bitmap;
    }
}
