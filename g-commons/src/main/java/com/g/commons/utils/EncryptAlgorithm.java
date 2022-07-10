package com.g.commons.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: Gtf
 * @Date: 2022/5/27-05-27-19:46
 * @Description: 来自网络的加密算法
 * @Version: 1.0
 */
public class EncryptAlgorithm {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    public static String useSHA1(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes("utf-8"));
            String result = getFormattedText(messageDigest.digest());
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * MD5加密
     * @param text
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String MD5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        return MD5.MD5Encode(text);
    }

    /**
     * MD5类
     */
    public static class MD5 {
        private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9", "a", "b", "c", "d", "e", "f"};

        /**
         * 转换字节数组为16进制字串
         * @param b 字节数组
         * @return 16进制字串
         */
        public static String byteArrayToHexString(byte[] b) {
            StringBuilder resultSb = new StringBuilder();
            for (byte aB : b) {
                resultSb.append(byteToHexString(aB));
            }
            return resultSb.toString();
        }

        /**
         * 转换byte到16进制
         * @param b 要转换的byte
         * @return 16进制格式
         */
        private static String byteToHexString(byte b) {
            int n = b;
            if (n < 0) {
                n = 256 + n;
            }
            int d1 = n / 16;
            int d2 = n % 16;
            return hexDigits[d1] + hexDigits[d2];
        }

        /**
         * MD5编码
         * @param origin 原始字符串
         * @return 经过MD5加密之后的结果
         */
        public static String MD5Encode(String origin) {
            String resultString = null;
            try {
                resultString = origin;
                MessageDigest md = MessageDigest.getInstance("MD5");
                resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultString;
        }
    }
}
