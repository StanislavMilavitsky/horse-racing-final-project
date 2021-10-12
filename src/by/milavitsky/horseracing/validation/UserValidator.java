package by.milavitsky.horseracing.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    private static final String NAME_REGEX =
            "^[a-zA-Zа-яА-Я'-]{3,20}$";
    private static final String SURNAME_REGEX =
            "^[a-zA-Zа-яА-Я'-]{3,40}$";
    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$";

    private UserValidator() {
    }

    public static boolean isValidName(String userName) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    public static boolean isValidSurname(String userSurname) {
        Pattern pattern = Pattern.compile(SURNAME_REGEX);
        Matcher matcher = pattern.matcher(userSurname);
        return matcher.matches();
    }

    public static boolean isValidPassword(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
