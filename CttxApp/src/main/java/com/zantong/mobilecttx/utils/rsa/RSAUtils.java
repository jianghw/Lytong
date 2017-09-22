package com.zantong.mobilecttx.utils.rsa;

import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;

import com.tzly.annual.base.util.ContextUtils;

/**
 * Created by zhoujie on 2016/11/25.
 */

public class RSAUtils {

    public static boolean isEncryption = true;

    /**
     * 通过加密的字符串
     */
    public static String strByEncryption(String source, boolean isEncryp) {

        String strEncryption = source;
        if (isEncryption && isEncryp) {
            try {
                RSAEncryptor rsaEncryptor = new RSAEncryptor();
                InputStream inPublic = ContextUtils.getContext().getResources()
                        .getAssets().open("rsa_public_key.pem");
                rsaEncryptor.loadPublicKey(inPublic);
                strEncryption = rsaEncryptor.encryptWithBase64(source);

            } catch (IOException e) {
                e.printStackTrace();
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
                InputStream inPublic = ContextUtils.getContext().getResources().getAssets().open("rsa_public_key.pem");
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
