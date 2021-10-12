package by.milavitsky.horseracing.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidator {
    private static final String ID_REGEX = "^[1-9]\\d{0,6}$";
    private static final String DECIMAL_REGEX = "-?(?:\\d+(?:\\.\\d+)?|\\.\\d+)";

    private CommonValidator() {
    }

    public static boolean isIdValid(String raceId) {
        Pattern pattern = Pattern.compile(ID_REGEX);
        Matcher matcher = pattern.matcher(raceId);
        return matcher.matches();
    }

    public static boolean isBigDecimalValid(String bigDecimal){
        Pattern pattern = Pattern.compile(DECIMAL_REGEX);
        Matcher matcher = pattern.matcher(bigDecimal);
        return matcher.matches();
    }
}
