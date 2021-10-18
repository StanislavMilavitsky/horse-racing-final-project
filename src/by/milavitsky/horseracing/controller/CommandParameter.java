package by.milavitsky.horseracing.controller;

public final class CommandParameter {
    /**
     * Parameters
     */
    public static final String PARAM_NAME = "name";
    public static final String PARAM_SURNAME = "surname";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_LOCATION = "location";
    public static final String PARAM_DATE = "date";
    public static final String PARAM_HORSE = "horse";
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_RACE_ID = "raceId";
    public static final String PARAM_RACE_CHECK = "checkAll";
    public static final String PARAM_ADD_CASH = "addCash";
    public static final String PARAM_COOKIE_LOCALE = "locale";
    public static final String PARAM_BET_INFO = "info";
    public static final String PARAM_BET_CASH = "cash";
    /**
     * Attributes
     */
    public static final String ATTR_USER_AUTH = "userAuth";
    public static final String ATTR_USER_MAP = "userMap";
    public static final String ATTR_RACE_SET = "horseSet";
    public static final String ATTR_RATIO_LIST = "ratioList";
    public static final String ATTR_HORSE_LIST = "horseList";
    public static final String ATTR_PAGE_NUMBER = "pageNumber";
    public static final String ATTR_RACES_LIST = "races";
    public static final String ATTR_RACE_INFO = "raceInfo";
    public static final String ATTR_USERS_LIST = "users";
    public static final String ATTR_USER_BETS = "userBets";
    public static final String ATTR_EMPTY_PARAM = "emptyParams";
    public static final String ATTR_INCORRECT_DATA = "incorrectData";
    public static final String ATTR_EMPTY_MESSAGE = "There should be no empty fields!";
    public static final String ATTR_INCORRECT_MESSAGE = "Incorrect email or password! Try again.";
    public static final String ATTR_CHECKBOX = "switch";
    public static final String ATTR_CHECKBOX_ON = "on";
    public static final String ATTR_CHECKBOX_OFF = "off";
    /**
     * PAGES
     */
    public static final String PAGE_INDEX = "/index.jsp";
    public static final String PAGE_REDIRECT_INDEX = "/horseracing";
    public static final String PAGE_RACES = "/WEB-INF/pages/races.jsp";
    public static final String PAGE_USERS = "/WEB-INF/pages/users.jsp";
    public static final String PAGE_ADD_RACE = "/WEB-INF/pages/addRace.jsp";
    public static final String PAGE_REGISTRATION = "/WEB-INF/pages/registration.jsp";
    public static final String PAGE_RACE = "/WEB-INF/pages/race.jsp";
    public static final String PAGE_PROFILE = "/WEB-INF/pages/profile.jsp";
    public static final String PAGE_PROFILE_BETS = "/WEB-INF/pages/profileBets.jsp";
    public static final String PAGE_ENTER_RESULT = "/WEB-INF/pages/enterResult.jsp";
    public static final String PAGE_SET_RATIO = "/WEB-INF/pages/setRatio.jsp";
    public static final String PAGE_404 = "/WEB-INF/pages/error/pages-404.jsp";
    public static final String PAGE_403 = "/WEB-INF/pages/error/pages-403.jsp";
    public static final String PAGE_500 = "/WEB-INF/pages/error/pages-500.jsp";
    public static final String PAGE_START_PREFIX = "http://localhost:8083/horseracing/";
    /**
     * LOCALIZATION
     */
    public static final String LOCALE_LANGUAGE_RU = "ru";
    public static final String LOCALE_LANGUAGE_EN = "en";
    public static final String LOCALE_COUNTRY_RU = "RU";
    public static final String LOCALE_COUNTRY_US = "US";
    /**
     * OTHERS
     */
    public static final String URL_REFERER = "referer";
    public static final String BLANK = "";
    public static final int MIN_HORSE_IN_RACE = 3;
    public static final String CHECKBOX = "on";
    public static final String COMMAND_PARAMETER = "command";

    private CommandParameter() {
    }
}
