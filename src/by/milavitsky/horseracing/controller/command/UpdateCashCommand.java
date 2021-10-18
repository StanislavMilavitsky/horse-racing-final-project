package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.entity.enums.PermissionEnum;
import by.milavitsky.horseracing.exception.CommandException;
import by.milavitsky.horseracing.exception.ServiceException;
import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.serviceinterface.UserServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Set;

import static by.milavitsky.horseracing.controller.CommandParameter.*;
import static by.milavitsky.horseracing.entity.enums.PermissionEnum.USER_BASIC;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class UpdateCashCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String addCash = request.getParameter(PARAM_ADD_CASH);
            if (isNotEmpty(addCash)) {
                User user = (User) request.getSession().getAttribute(ATTR_USER_AUTH);
                BigDecimal newCash = new BigDecimal(addCash).add(user.getCash());
                user.setCash(newCash);
                UserServiceInterface userService = (UserServiceInterface) ServiceFactory.getInstance().getClass(UserServiceInterface.class);
                boolean result = userService.updateCash(newCash, user.getId());
                if (result) {
                    request.getSession().setAttribute(ATTR_USER_AUTH, user);
                }
            }
            return new Router(PAGE_PROFILE);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(USER_BASIC);
    }
}
