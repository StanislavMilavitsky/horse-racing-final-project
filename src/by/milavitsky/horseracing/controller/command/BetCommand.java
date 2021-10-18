package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.entity.enums.PermissionEnum;
import by.milavitsky.horseracing.exception.CommandException;
import by.milavitsky.horseracing.exception.ServiceException;
import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.serviceinterface.BetServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.util.Set;

import static by.milavitsky.horseracing.controller.CommandParameter.*;
import static by.milavitsky.horseracing.entity.enums.PermissionEnum.*;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;


public class BetCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
            try {
                String info = request.getParameter(PARAM_BET_INFO);
                String cash = request.getParameter(PARAM_BET_CASH);
                if (isNotEmpty(info) && isNotEmpty(cash)) {
                    HttpSession session = request.getSession();
                    User user = (User) session.getAttribute(ATTR_USER_AUTH);
                    BetServiceInterface betService = (BetServiceInterface) ServiceFactory.getInstance().getClass(BetServiceInterface.class);
                    boolean result = betService.add(info, cash, user.getCash(), user.getId());
                    if (result) {
                        BigDecimal userCash = user.getCash();
                        BigDecimal newUserCash = userCash.subtract(new BigDecimal(cash));
                        user.setCash(newUserCash);
                        session.setAttribute(ATTR_USER_AUTH, user);
                        Router router = new Router(request);
                        router.redirect();
                        return router;
                    }
                }
                return new Router(request);
            } catch (ServiceException e) {
                throw new CommandException(e.getMessage(), e);
            }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return (permissions.contains(USER_BASIC) || permissions.contains(ADMIN_BASIC) ||
                permissions.contains(CUSTOMER_BASIC)) && permissions.contains(PLACE_BET);
    }
}
