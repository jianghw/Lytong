package com.tzly.ctcyh.router.util.rea;

import android.text.TextUtils;

import com.tzly.ctcyh.router.util.Utils;

import java.io.InputStream;

/**
 * rsa 工具
 */

public class RSAUtils {

    public static boolean isEncryption = true;

    /**
     * 通过加密的字符串
     */
    public static String strByEncryption(String source, boolean isEncryp) {
        if (TextUtils.isEmpty(source)) return source;
        String strEncryption = source.trim();

        if (isEncryp) {
            try {
                RSAEncryptor rsaEncryptor = new RSAEncryptor();
                InputStream inPublic = Utils.getContext().getResources()
                        .getAssets().open("rsa_public_key.pem");
                rsaEncryptor.loadPublicKey(inPublic);
                strEncryption = rsaEncryptor.encryptWithBase64(source);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strEncryption;
    }

    /**
     * 通过加密的字符串
     *
     * @return
     */
    public static String strByEncryptionLiYing(String source, boolean isEncryp) {
        if (TextUtils.isEmpty(source)) return source;

        String strEncryption = source;
        if (isEncryp) {
            try {
                RSAEncryptor rsaEncryptor = new RSAEncryptor();
                InputStream inPublic = Utils.getContext().getResources().getAssets().open("rsa_public_key.pem");
                rsaEncryptor.loadPublicKey(inPublic);
                strEncryption = rsaEncryptor.encryptWithBase64(source);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            strEncryption = source;
        }
        return strEncryption;
    }
}
