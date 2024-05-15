package org.demo.paymentdemo.utils;

import java.util.Random;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class NumberGenerator {

    private NumberGenerator() {
        throw new IllegalStateException("Utility class");
    }

    //    client로 부터 cardNumber 를 입력 받을 예정.
    public static String generateCardNumber() {

        Random random = new Random();
        int randomNumber = random.nextInt(1_000_000_000);
        String formattedNumber = String.format("%012d", randomNumber);
        log.info("generateCardNumber====={}", formattedNumber);
        return formattedNumber;
    }

    public static String generateCardRefId() {

        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString().replaceAll("-", "");
        log.info("generateCardRefId===={}", ("ref_" + uuidString).substring(0, 19));
        return ("ref_" + uuidString).substring(0, 19);
    }
}
