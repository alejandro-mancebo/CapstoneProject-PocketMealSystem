package ca.georgebrown.comp3074.pocketmealapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashingPassword {

    private String passwork;
    private static String algorithm = "SHA-256";

    public static String generateHash(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(algorithm);
        digest.reset();
        byte[] salt = createSalt();
        digest.update(salt);
        byte[] hash = digest.digest(password.getBytes());

        return bytesToStringHex(hash);
    }

    private static byte[] createSalt() {
        byte[] bytes = new byte[32];
        SecureRandom random = new SecureRandom();
        random.nextBytes(bytes);
        return bytes;
    }

    private final static char[] hexArray = "0123456789ABCDEf".toCharArray();

    public static String bytesToStringHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = hexArray[v >>> 4];
            hexChars[i * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }
}
