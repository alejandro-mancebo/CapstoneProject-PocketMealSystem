package ca.georgebrown.comp3074.pocketmealapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class PasswordUtil {
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA-256");
        mDigest.reset();
        byte[] hash = mDigest.digest(password.getBytes());
        StringBuilder sb = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            int v = b & 0xFF;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString();
    }

   // @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getSalt() {
        Random random = new SecureRandom();
        byte[] saltBytes = new byte[32];
        random.nextBytes(saltBytes);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(saltBytes);
        }
        // TODO - review the null return
        return null;
    }

    public static String hashAndsaltPassword(String password) throws NoSuchAlgorithmException {
        String salt = getSalt();
        return hashPassword(password + salt);
    }

}
