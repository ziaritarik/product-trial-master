package com.althen.Ecommerce.utils;

import org.springframework.lang.NonNull;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.regex.Pattern;

public class CommonUtils {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    private CommonUtils() throws IllegalAccessException {
        throw new IllegalAccessException("You cannot instance an utility class.");
    }

    public static String getNextCode(@NonNull String prefix, String lastCode) {
        // Generate the next code based on the last code
        String nextCode;
        if (Objects.isNull(lastCode)) {
            nextCode = prefix.trim() + "00001";
        } else {
            int codeNumber = Integer.parseInt(lastCode.trim().substring(prefix.length())) + 1;
            nextCode = String.format("%s%05d", prefix.trim(), codeNumber);
        }
        return nextCode;
    }

    public static boolean isValidEmail(@NonNull String email) {
        return Pattern.compile(EMAIL_PATTERN)
                .matcher(email.trim())
                .matches();
    }

    public static String generatePassword(int length) {
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            password.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return password.toString();
    }
}
