package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.User;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_interface.UserServiceInterface;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Set;

import static by.milavitsky.horse_racing.controller.CommandParameter.*;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.GUEST_BASIC;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class LogInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String email = request.getParameter(PARAM_EMAIL);
            String password = request.getParameter(PARAM_PASSWORD);
            if (isEmpty(email) || isEmpty(password)) {
                request.setAttribute(ATTR_EMPTY_PARAM, ATTR_EMPTY_MESSAGE);
                return new Router(PAGE_INDEX);
            }
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            UserServiceInterface userService = (UserServiceInterface) ServiceFactory.getInstance().getClass(UserServiceInterface.class);
            User userAuthorized = userService.authorization(user);
            if (userAuthorized != null) {
                HttpSession session = request.getSession();
                session.setAttribute(ATTR_USER_AUTH, userAuthorized);
            } else {
                request.setAttribute(ATTR_INCORRECT_DATA, ATTR_INCORRECT_MESSAGE);
                return new Router(PAGE_INDEX);
            }
            return new Router(PAGE_INDEX);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(GUEST_BASIC);
    }

}
