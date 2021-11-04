package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.enumentity.PermissionEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static by.milavitsky.horseracing.controller.CommandParameter.PAGE_REGISTRATION;
import static by.milavitsky.horseracing.entity.enumentity.PermissionEnum.*;

public class ForwardRegistrationCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        return new Router(PAGE_REGISTRATION);
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(GUEST_BASIC) || permissions.contains(USER_BASIC) ||
                permissions.contains(ADMIN_BASIC) || permissions.contains(CUSTOMER_BASIC);
    }
}
