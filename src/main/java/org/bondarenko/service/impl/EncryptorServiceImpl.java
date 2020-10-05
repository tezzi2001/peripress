package org.bondarenko.service.impl;

import org.bondarenko.service.EncryptorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;


public class EncryptorServiceImpl implements EncryptorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EncryptorServiceImpl.class);
    private static final SecretKeyFactory SECRET_KEY_FACTORY;
    private static final Random RANDOM = new SecureRandom();
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final String LOCAL_PARAMETER = "HF7Tgd7tydfY987FDHF7dgtfd6f6tD6FG8D8F87fd78";
    private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    static {
        SecretKeyFactory secretKeyFactory;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.warn("Error while creating secret key factory: ", e);
            LOGGER.warn("NPE can be thrown");
            secretKeyFactory = null;
        }
        SECRET_KEY_FACTORY = secretKeyFactory;
    }

    private String getSalt(int length) {
        char[] salt = new char[length];
        for (int i = 0; i < length; i++) {
            salt[i] = ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length()));
        }
        return String.valueOf(salt);
    }

    private byte[] hash(String password, String salt) {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, KEY_LENGTH);
        try {
            return SECRET_KEY_FACTORY.generateSecret(spec).getEncoded();
        } catch (NullPointerException | InvalidKeySpecException e) {
            LOGGER.warn("Error while hashing a password: ", e);
        } finally {
            spec.clearPassword();
        }
        return new byte[0];
    }

    private String encode(String rawPassword, String salt) {
        String password = rawPassword + LOCAL_PARAMETER;
        String securePassword = new String(hash(password, salt));
        return salt + '$' + securePassword;
    }

    @Override
    public String encode(String rawPassword) {
        String salt = getSalt(20);
        return encode(rawPassword, salt);
    }

    @Override
    public boolean matches(String rawPassword, String securedPassword) {
        String salt = securedPassword.split("\\$")[0];
        String newSecurePassword = encode(rawPassword, salt);
        return newSecurePassword.equals(securedPassword);
    }
}
