package com.tzly.ctcyh.router.util.rea;

import android.text.TextUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 3DES加密工具类
 */
public class Des3 {

    private static boolean isDecode = true;
    // 瀵嗛挜
    private final static String secretKey = "liuyunqiang@lx100$#365#$";
    // 鍚戦噺
    private final static String mIV = "01234567";
    // 鍔犺В瀵嗙粺涓€浣跨敤鐨勭紪鐮佹柟寮?
    private final static String encoding = "utf-8";

    /**
     * 3DES鍔犲瘑
     *
     * @param plainText 鏅€氭枃鏈?
     * @return
     * @throws Exception
     */
    public static String encode(String plainText) {
        String encodeStr;
        try {

            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
            SecretKey secretKey = secretKeyFactory.generateSecret(deSedeKeySpec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec parameterSpec = new IvParameterSpec(mIV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            encodeStr = BaseUtil64.encode(encryptData);
        } catch (Exception e) {
            e.printStackTrace();
            encodeStr = plainText;
        }
        return encodeStr;
    }

    /**
     * 3DES瑙ｅ瘑
     *
     * @param encryptText 鍔犲瘑鏂囨湰
     * @return
     * @throws Exception
     */
    public static String decode(String encryptText) {
        if (TextUtils.isEmpty(encryptText)) return encryptText;
        String strDecode;
        try {
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("desede");
            SecretKey deskey = secretKeyFactory.generateSecret(deSedeKeySpec);

            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec parameterSpec = new IvParameterSpec(mIV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, parameterSpec);
            byte[] decryptData = cipher.doFinal(BaseUtil64.decode(encryptText));
            strDecode = new String(decryptData, encoding);
        } catch (Exception e) {
            e.printStackTrace();
            strDecode = encryptText;
        }
        return strDecode;
    }

}
