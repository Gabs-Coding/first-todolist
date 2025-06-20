package code.gabs.todolist.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordEncryption {

    public static String encryptPassword(String password) {
        return BCrypt.withDefaults().hashToString(12,
                password.toCharArray());
    }

    public static boolean isPasswordsEquals(String typedPassword, String bcryptedPassword) {
        return BCrypt.verifyer()
                .verify(typedPassword.toCharArray(), bcryptedPassword.toCharArray())
                .verified;
    }
}
