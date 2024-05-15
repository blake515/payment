package org.demo.paymentdemo.utils;

import java.util.Random;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class TokenGenerator {
    private TokenGenerator() {
        throw new IllegalStateException("Utility class");
    }
    public static String generateToken() {
        // 무작위로 32자리 토큰 생성
        int length = 32;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            token.append(characters.charAt(random.nextInt(characters.length())));
        }
        log.info("token====={}",token.toString());
        return token.toString();
    }
}
