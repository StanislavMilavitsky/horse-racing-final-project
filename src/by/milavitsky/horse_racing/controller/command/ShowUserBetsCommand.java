package by.milavitsky.horse_racing.controller.command;

import by.milavitsky.horse_racing.controller.Command;
import by.milavitsky.horse_racing.controller.Router;
import by.milavitsky.horse_racing.entity.Bet;
import by.milavitsky.horse_racing.entity.User;
import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.ServiceException;
import by.milavitsky.horse_racing.service.ServiceFactory;
import by.milavitsky.horse_racing.service.service_interface.BetServiceInterface;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.List;
import java.util.Set;

import static by.milavitsky.horse_racing.controller.CommandParameter.*;
import static by.milavitsky.horse_racing.entity.enums.PermissionEnum.USER_BASIC;

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
