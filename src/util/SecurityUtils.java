package util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class SecurityUtils {
    public static String hashPassword(String password) {
        return BCrypt.withDefaults().hashToString(10, password.toCharArray());
    }

    public static boolean checkPassword(String input, String hashedPassword) {
        return BCrypt.verifyer().verify(input.toCharArray(), hashedPassword).verified;
    }

}
