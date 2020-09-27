package org.bondarenko.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class PasswordEncryptor {
    private static final SecretKeyFactory FACTORY;
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordEncryptor.class);

    static {
        SecretKeyFactory tempFactory = null;
        try {
            tempFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("", e);
        }
        FACTORY = tempFactory;
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public byte[] encode(String rawPassword) {
        KeySpec spec = new PBEKeySpec(rawPassword.toCharArray(), generateSalt(), 65536, 128);
        try {
            return FACTORY.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            LOGGER.warn("", e);
            return new byte[0];
        }
    }

    public boolean matches(String rawPassword, byte[] encodedPassword) {
        return Arrays.equals(encodedPassword, encode(rawPassword));
    }
}
