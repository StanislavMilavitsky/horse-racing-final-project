package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static by.milavitsky.horse_racing.controller.CommandParameter.PAGE_PROFILE;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.*;

public class ProfileCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response){
        return new Router(PAGE_PROFILE);
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(USER_BASIC) || permissions.contains(ADMIN_BASIC) ||
                permissions.contains(CUSTOMER_BASIC);
    }
}
