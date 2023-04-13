package com.cookos;

import java.util.regex.Pattern;

public class PasswordValidator {

    private static final String REGEX_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";

    public static boolean validate(String password) {
        return Pattern.compile(REGEX_PATTERN).matcher(password).matches();
    }
}
