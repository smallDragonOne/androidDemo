package com.example.apple.sometestdemo.ApiManage;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES加密
 */
public class DESEncrypt {
    final private String encode = "UTF-8";
    final private String defalutKey = "dferdiphergy";
    final private byte[] iv = "acd1233f".getBytes();
    final private String ALGORITHM_DES = "DES/CBC/PKCS5Padding";

    public String encrypt(String text) throws Exception{
        return encrypt(text, defalutKey);
    }
    public String decrypt(String text) throws Exception{
        return decrypt(text, defalutKey);
    }
    /**
     * 加密
     *
     * @param text           内容
     * @param key           密钥
     * @return
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     */
    public String encrypt(String text, String key) throws Exception {
        byte[] inputByteArray = text.getBytes(encode);
        // 密码，长度要是8的倍数
        String password = MD5Util.getSubString(key, 3, 11);
        IvParameterSpec random = new IvParameterSpec(iv);
        SecretKeySpec securekey = new SecretKeySpec(password.getBytes(encode), "DES");
        Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
        cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
        byte[] outByteArray = cipher.doFinal(inputByteArray);
        String HEX = "0123456789ABCDEF";
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < outByteArray.length; i++) {
            byte b = outByteArray[i];
            ret.append( HEX.charAt( ( b >> 4 ) & 0x0f ) );
            ret.append( HEX.charAt( b & 0x0f ) );
        }
        return ret.toString();
    }


    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /**
     * 解密
     *
     * @param text           内容
     * @param key           密钥
     * @return
     */
    public String decrypt(String text, String key) throws Exception {
        text = text.toUpperCase();
        int length = text.length() / 2;
        char[] hexChars = text.toCharArray();
        byte[] inputByteArray = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            inputByteArray[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }

        // 密码，长度要是8的倍数
        String password = MD5Util.getSubString(key, 3, 11);

        IvParameterSpec random = new IvParameterSpec(iv);
        SecretKeySpec securekey = new SecretKeySpec(password.getBytes(encode), "DES");
        Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        byte[] midbytes = cipher.doFinal(inputByteArray);
        return new String(midbytes, encode);
    }

}