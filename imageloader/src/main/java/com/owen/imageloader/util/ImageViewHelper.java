package com.owen.imageloader.util;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by Owen Chan
 * On 2017-10-24.
 */

public class ImageViewHelper {


    // todo : 配置类中
    private static int DEFAULT_WIDTH = 200;
    private static int DEFAULT_HEIGHT = 200;


    public static int getImageViewWidth(ImageView imageView) {
        int width = DEFAULT_WIDTH;
        if (imageView != null) {
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
                width = imageView.getWidth();
            }
            if (width <= 0 && params != null) {
                width = params.width;
            }
            if (width <= 0) {
                width = getImageViewFieldValue(imageView, "mMaxWidth");
            }
        }
        return width;
    }


    public static int getImageViewHeight(ImageView imageView) {
        int height = DEFAULT_HEIGHT;
        if (imageView != null) {
            final ViewGroup.LayoutParams params = imageView.getLayoutParams();
            if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
                height = imageView.getHeight();
            }
            if (height <= 0 && params != null) {
                height = params.height;
            }
            if (height <= 0) {
                height = getImageViewFieldValue(imageView, "mMaxHeight");
            }
        }
        return height;
    }

    private static int getImageViewFieldValue(Object object, String fieldName) {
        int value = 0;
        try {
            Field field = ImageView.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(object);
            if (fieldValue > 0 && fieldValue < Integer.MAX_VALUE) {
                value = fieldValue;
            }
        } catch (Exception e) {
            Log.e("", e.getMessage());
        }
        return value;
    }
}
