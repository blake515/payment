package org.demo.paymentdemo.utils;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.log4j.Log4j2;
import org.mindrot.jbcrypt.BCrypt;

@Log4j2
public class EncryptionUtil {
    private EncryptionUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final String AES_ALGORITHM = "AES";
    private static final String AES_SECRET_KEY = "1234567890abcdef"; // 16자 이상의 임의의 키

    /**
     * 암호화 함수
     * @param input 암호화 전 평문
     * @return 암호화 된 문자열
     */
    public static String encrypt(String input) {
        try {
            Key key = new SecretKeySpec(AES_SECRET_KEY.getBytes(), AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedBytes = cipher.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            log.error("암호화 예외 발생!!!");
            return null;
        }
    }

    /**
     * 복호화 함수
     * @param encryptedInput 암호화된 문자열
     * @return 문자열
     */
    public static String decrypt(String encryptedInput) {
        try {
            Key key = new SecretKeySpec(AES_SECRET_KEY.getBytes(), AES_ALGORITHM);
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedInput);
            byte[] decryptedBytes = cipher.doFinal(decodedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            log.error("복호화 예외 발생!");
            return null;
        }
    }

}
