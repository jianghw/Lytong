package com.zantong.mobilecttx.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by zhengyingbing on 16/11/1.
 */

public class EncryptorUtils {

//    public static void main(String[] args) throws IOException, NoSuchAlgorithmException,
//            InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
//        PrivateKey privateKey = readPrivateKey();
//
//        String message = "AFppaFPTbm boMZD55cjCfrVaWUW7+hZkaq16Od+6fP0lwz/yC+Rshb/8cf5BpBlUao2EunchnzeKxzpiPqtCcCITKvk6HcFKZS0sN9wOhlQFYT+I4f/CZITwBVAJaldZ7mkyOiuvM+raXMwrS+7MLKgYXkd5cFPxEsTxpMSa5Nk=";
//        System.out.println(message.format("- decrypt rsa encrypted base64 message: %s", message));
//        // hello ~,  encrypted and encoded with Base64:
//        byte[] data = encryptedData(message);
//        String text = decrypt(privateKey, data);
//        System.out.println(text);
//    }

    private static String decrypt(PrivateKey privateKey, byte[] data)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedData = cipher.doFinal(data);

        return new String(decryptedData);
    }

//    private static byte[] encryptedData(String base64Text) {
//        return Base64.getDecoder().decode(base64Text.getBytes(Charset.forName("UTF-8")));
//    }
//
//    private static PrivateKey readPrivateKey() throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        byte[] privateKeyData = MediaStore.Files.readAllBytes(
//                Paths.get("/Users/twer/macspace/ios_workshop/Security/SecurityLogin/tools/pkcs8_private_key.pem"));
//
//        byte[] decodedKeyData = Base64.getDecoder()
//                .decode(new String(privateKeyData)
//                        .replaceAll("-----\\w+ PRIVATE KEY-----", "")
//                        .replace("\n", "")
//                        .getBytes());
//
//        return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decodedKeyData));
//    }
}
