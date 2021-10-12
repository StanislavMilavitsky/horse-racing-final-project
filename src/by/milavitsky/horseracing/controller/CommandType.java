package by.milavitsky.horseracing.controller;

public enum  CommandType {
    RACES,
    LOGIN,
    LOGOUT,
    REGISTRATION,
    FORWARD_REGISTRATION,
    RACE,
    PROFILE,
    COOKIE,
    SHOW_USER_BETS,
    SHOW_USERS,
    UPDATE_CASH,
    ADD_RACE,
    DELETE_USER,
    ENTER_RESULT,
    DELETE_RACE,
    SET_RATIO,
    BET;

    public static boolean isContains(String name) {
        for (CommandType value : CommandType.values()) {
            if (value.name().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public static CommandType getCommand(String command) {
        return CommandType.valueOf(command.toUpperCase());
    }
}
