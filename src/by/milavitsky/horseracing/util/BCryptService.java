package by.milavitsky.horseracing.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * The type BCrypt service.
 */
public class BCryptService {
    /**
     * Hash password string.
     *
     * @param password the password
     * @return the string
     */
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(10, password.toCharArray());
    }

    /**
     * Verify password boolean.
     *
     * @param hashPassword       the hash password
     * @param verifiablePassword the verifiable password
     * @return the boolean
     */
    public static boolean verifyPassword(String hashPassword, String verifiablePassword) {
        BCrypt.Result verify = BCrypt.verifyer().verify(hashPassword.trim().toCharArray(), verifiablePassword.trim());
        return verify.verified;
    }
}
