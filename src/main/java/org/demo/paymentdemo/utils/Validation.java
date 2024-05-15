package org.demo.paymentdemo.utils;

public class Validation {
    private Validation() {
        throw new IllegalStateException("Utility class");
    }
    public static boolean isValidCardNumber(String cardNumber) {
        // 숫자로만 이루어진 16자리의 문자열인지를 확인하는 정규 표현식
        String regex = "^[0-9]{16}$";
        return cardNumber.matches(regex);
    }
}
