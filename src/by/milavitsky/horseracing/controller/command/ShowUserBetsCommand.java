package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.Bet;
import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.entity.enums.PermissionEnum;
import by.milavitsky.horseracing.exception.CommandException;
import by.milavitsky.horseracing.exception.ServiceException;
import by.milavitsky.horseracing.service.ServiceFactory;
import by.milavitsky.horseracing.service.serviceinterface.BetServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Set;

import static by.milavitsky.horseracing.controller.CommandParameter.*;
import static by.milavitsky.horseracing.entity.enums.PermissionEnum.USER_BASIC;

public class ShowUserBetsCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(ATTR_USER_AUTH);
            ServiceFactory factory = ServiceFactory.getInstance();
            BetServiceInterface betService = (BetServiceInterface) factory.getClass(BetServiceInterface.class);
            List<Bet> bets = betService.showByUser(user.getId());
            request.setAttribute(ATTR_USER_BETS, bets);
            return new Router(PAGE_PROFILE_BETS);
        } catch (ServiceException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return permissions.contains(USER_BASIC);
    }
}
