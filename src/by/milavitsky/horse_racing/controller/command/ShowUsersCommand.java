package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.User;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_entity.UserService;
import by.milavitsky.horse_racing.service.service_interface.UserServiceInterface;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Set;

import static by.milavitsky.horse_racing.controller.CommandParameter.*;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.ADMIN_BASIC;

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
