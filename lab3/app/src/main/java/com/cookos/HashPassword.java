package com.cookos;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class HashPassword {

    public static byte[] getHash(String password)
    {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.reset();

        return digest.digest(password.getBytes(StandardCharsets.UTF_8));
    }
}
