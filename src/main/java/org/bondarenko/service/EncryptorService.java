package org.bondarenko.service;

public interface EncryptorService {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String securedPassword);
}
