package by.milavitsky.horseracing.controller;

import by.milavitsky.horseracing.controller.command.*;

import java.util.EnumMap;
import java.util.Map;

import static by.milavitsky.horseracing.controller.CommandType.*;

/**
 * The type Command map.
 * <p>
 * Map that stores available commands with their implementation.
 */
public class CommandMap {

    private static final Map<CommandType, Command> commands = new EnumMap<>(CommandType.class);


    static {
        commands.put(RACES, new RacesPageCommand());
        commands.put(LOGIN, new LogInCommand());
        commands.put(LOGOUT, new LogOutCommand());
        commands.put(REGISTRATION, new RegistrationCommand());
        commands.put(FORWARD_REGISTRATION, new ForwardRegistrationCommand());
        commands.put(RACE, new RacePageCommand());
        commands.put(PROFILE, new ProfileCommand());
        commands.put(COOKIE, new CookieCommand());
        commands.put(SHOW_USER_BETS, new ShowUserBetsCommand());
        commands.put(SHOW_USERS, new ShowUsersCommand());
        commands.put(UPDATE_CASH, new UpdateCashCommand());
        commands.put(ADD_RACE, new AddRaceCommand());
        commands.put(DELETE_USER, new BanUserCommand());
        commands.put(ENTER_RESULT, new EnterResultCommand());
        commands.put(DELETE_RACE, new DeleteRaceCommand());
        commands.put(SET_RATIO, new SetRatioCommand());
        commands.put(BET, new BetCommand());
    }

    /**
     * Gets command.
     *
     * @param commandType the command type
     * @return the command
     */
    public Command getCommand(CommandType commandType) {
        return commands.get(commandType);
    }

    private static class CommandMapHolder {
        private static final CommandMap HOLDER_INSTANCE = new CommandMap();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static CommandMap getInstance() {
        return CommandMapHolder.HOLDER_INSTANCE;
    }
}

