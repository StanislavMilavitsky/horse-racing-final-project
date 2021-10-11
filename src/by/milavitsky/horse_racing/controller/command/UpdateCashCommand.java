package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.User;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.ServiceException;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_interface.UserServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Set;

import static by.milavitsky.horse_racing.controller.CommandParameter.*;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.USER_BASIC;
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
