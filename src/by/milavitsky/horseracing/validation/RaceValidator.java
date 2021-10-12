package by.milavitsky.horseracing.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RaceValidator {
    private static final String LOCATION_REGEX =
            "^[ a-zA-Zа-яА-Я'-]{3,50}$";
    private static final String DATE_TIME_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}$";

    public static boolean isValidLocation(String location) {
        Pattern pattern = Pattern.compile(LOCATION_REGEX);
        Matcher matcher = pattern.matcher(location);
        return matcher.matches();
    }

    public static boolean isValidDateTime(String dateTime) {
        Pattern pattern = Pattern.compile(DATE_TIME_REGEX);
        Matcher matcher = pattern.matcher(dateTime);
        return matcher.matches();
    }

}
