package by.milavitsky.horse_racing.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BetValidator {
    private static final String BET_INFO_REGEX = "Ratio\\{raceId=\\d{1,7}, horseId=\\d{1,7}, typeId=\\d{1,7}, ratio=\\d+.\\d+}";
    private static final String DATE_TIME_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$";
    private static final String KEY_REGEX = "^\\d{1,7}\\|\\d{1,7}\\|(win|show)$";
    private static final String VALUE_REGEX = "(?:\\d+(?:\\.\\d+)?|\\.\\d+)";

    private BetValidator() {
    }

    public static boolean isInfoValid(String info) {
        Pattern pattern = Pattern.compile(BET_INFO_REGEX);
        Matcher matcher = pattern.matcher(info);
        return matcher.matches();
    }

    public static boolean isValidDateTime(String dateTime) {
        Pattern pattern = Pattern.compile(DATE_TIME_REGEX);
        Matcher matcher = pattern.matcher(dateTime);
        return matcher.matches();
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
