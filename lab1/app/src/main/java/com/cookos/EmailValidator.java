package com.cookos;

import java.util.regex.Pattern;

public class EmailValidator {

    private static final String REGEX_PATTERN
            = "^(?=.{1,64}@)[A-Za-z\\d_-]+(\\.[A-Za-z\\d_-]+)*@[^-][A-Za-z\\d-]+(\\.[A-Za-z\\d-]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validate(String email) {
        return Pattern.compile(REGEX_PATTERN).matcher(email).matches();
    }
}
