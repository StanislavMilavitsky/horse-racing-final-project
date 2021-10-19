package by.milavitsky.horseracing.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RatioValidator {
    private static final String KEY_REGEX = "^\\d{1,7}\\|\\d{1,7}\\|(win|show)$";
    private static final String VALUE_REGEX = "(?:\\d+(?:\\.\\d+)?|\\.\\d+)";

    private RatioValidator() {
    }

    public static boolean isMapKeyValid(String key) {
        Pattern pattern = Pattern.compile(KEY_REGEX);
        Matcher matcher = pattern.matcher(key);
        return matcher.matches();
    }

    public static boolean isMapValueValid(String value){
        Pattern pattern = Pattern.compile(VALUE_REGEX);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
