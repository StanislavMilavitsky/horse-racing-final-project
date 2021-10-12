package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.enums.PermissionEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static by.milavitsky.horseracing.controller.CommandParameter.PAGE_PROFILE;
import static by.milavitsky.horseracing.entity.enums.PermissionEnum.*;

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
