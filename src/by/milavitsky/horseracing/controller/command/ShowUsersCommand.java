package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.entity.enums.PermissionEnum;
import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.service_entity.UserService;
import by.milavitsky.horseracing.service.service_interface.UserServiceInterface;
import by.milavitsky.horseracing.exception.CommandException;
import by.milavitsky.horseracing.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Set;

import static by.milavitsky.horseracing.controller.CommandParameter.*;
import static by.milavitsky.horseracing.entity.enums.PermissionEnum.ADMIN_BASIC;

public class ShowUsersCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            UserServiceInterface userService = (UserServiceInterface) ServiceFactory.getInstance().getClass(UserService.class);
            int pagesCount = userService.getUsersPagesCount();
            request.setAttribute(ATTR_PAGE_NUMBER, pagesCount);
            String page = request.getParameter(PARAM_PAGE);
            List<User> users = userService.findAll(page);
            request.setAttribute(ATTR_USERS_LIST, users);
            return new Router(PAGE_USERS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(ADMIN_BASIC);    }
}
