package com.netease.pineapple.common.utils;

/**
 * Created by zhaonan on 2018/2/16.
 */


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密
 */
public class AESUtils {

    public static final String AES_PRODUCT_KEY = "neteasepineapple";

    public static final String AES_PRODUCT_KEY_TUIJIAN = "neteasenewsboard";

    public static final String AES_PRODUCT_KEY_GALAXY = "neteasemobiledat";

    public static final String TAG = "AESUtil";

    /**
     * 加密
     *
     * @param sKey
     * @param sSrc
     * @return
     */
    public static String encrypt(String sKey, String sSrc) {
        try {
            if (sKey == null) {
                return sSrc;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return sSrc;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            return Base64.encodeToString(encrypted,Base64.NO_WRAP);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return sSrc;
    }

    /**
     * 解密
     *
     * @param sKey
     * @param sSrc
     * @return
     */
    public static String decrypt(String sKey, String sSrc) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                return sSrc;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                return sSrc;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = Base64.decode(sSrc, Base64.NO_WRAP);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return sSrc;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return sSrc;
        }
    }

    @SuppressLint("TrulyRandom")
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        sr.setSeed(seed);
        kgen.init(128, sr);
        SecretKey sKey = kgen.generateKey();
        byte[] raw = sKey.getEncoded();
        return raw;
    }

    /**
     * 加密字节
     *
     * @param raw
     * @param clear
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    /**
     * 解密字节
     *
     * @param raw
     * @param encrypted
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding","BC");
        cipher.init(2, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }


    public static String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public static String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }
        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }
        return result.toString();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        final String HEX = "0123456789ABCDEF";
        sb.append(HEX.charAt(b >> 4 & 0xF)).append(HEX.charAt(b & 0xF));
    }

    public static String encrypt2(String content, String key) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        if (TextUtils.isEmpty(key) || key.length() != 16) {
            return "";
        }
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding", "BC");// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);// 初始化

            byte[] byteContent = content.getBytes("utf-8");
            byte[] result = cipher.doFinal(byteContent);

            return new String(it.sauronsoftware.base64.Base64.encode(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @Name byte2hex
     * @Description 二进制转十六进制字符串
     * @Param [b]
     * @Return java.lang.String
     */
    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp;
        for (int n = 0; n < b.length; n++) {
            // 整数转成十六进制表示
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
        }
        return hs.toUpperCase();
    }

    public static byte[] hex2byte(String s) {
        byte[] b = s.getBytes();
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数！");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * @Name encrypt
     * @Description AES加密
     * @Param [src, kv]
     * @Return byte[]
     */
    public static byte[] encryptUrs(byte[] src, byte[] kv) {
        SecretKey key = new SecretKeySpec(kv, "AES");

        try {
            Cipher cp = Cipher.getInstance("AES");
            cp.init(Cipher.ENCRYPT_MODE, key);
            return cp.doFinal(src);
        } catch (NoSuchAlgorithmException
                | NoSuchPaddingException
                | BadPaddingException
                | IllegalBlockSizeException
                | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Name decrypt
     * @Description 解密
     * @Param [src:待解密内容, kv:解密密钥]
     * @Return byte[]
     */
    public static byte[] decryptUrs(byte[] src, byte[] kv) {
        SecretKey key = new SecretKeySpec(kv, "AES");
        try {
            Cipher cp = Cipher.getInstance("AES");
            cp.init(Cipher.DECRYPT_MODE, key);
            return cp.doFinal(src);
        } catch (NoSuchAlgorithmException
                | InvalidKeyException
                | NoSuchPaddingException
                | BadPaddingException
                | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encryptWithTuiJianKey(String content) {
        return encrypt2(content, AES_PRODUCT_KEY_TUIJIAN);
    }

    public static String signWithTuijianKey(String... params) {

        if (params == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String param : params) {
            builder.append(param);
        }

        String ori = builder.toString();
        //DebugLog.e("ori = " + ori);

        String md5 = Md5Utils.md5(ori);
        //DebugLog.e("md5 = " + md5);

        try {
            String result = AESUtils.encryptWithTuiJianKey(md5);
            //DebugLog.e("result = " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

