package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.enums.PermissionEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Set;

import static by.milavitsky.horseracing.controller.CommandParameter.PAGE_INDEX;
import static by.milavitsky.horseracing.entity.enums.PermissionEnum.*;

public class LogOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return new Router(PAGE_INDEX);
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return true;
    }
}
