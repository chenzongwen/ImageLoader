package com.owen.imageloader.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Owen Chan
 * On 2017-10-23.
 */

public class Md5Helper {

    private static MessageDigest mDigest = null;

    static {
        try {
            mDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    public static String toMD5(String key) {
        if (mDigest == null) {
            return String.valueOf(key.hashCode());
        }
        mDigest.update(key.getBytes());
        return byte2HexString(mDigest.digest());
    }

    private static String byte2HexString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte tmpByte : bytes) {
            String hex = Integer.toHexString(0xFF & tmpByte);
            if (hex.length() == 1) {
                stringBuilder.append('0');
            }
            stringBuilder.append(hex);
        }
        return stringBuilder.toString();
    }

}
