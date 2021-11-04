package by.milavitsky.horseracing.controller.filter;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.CommandMap;
import by.milavitsky.horseracing.controller.CommandType;
import by.milavitsky.horseracing.entity.User;
import by.milavitsky.horseracing.entity.enumentity.PermissionEnum;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import static by.milavitsky.horseracing.controller.CommandParameter.*;

public class PermissionAccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(ATTR_USER_AUTH);
        Set<PermissionEnum> permissions;
        if (user != null && user.getRole() != null && user.getRole().getPermissions() != null) {
            permissions = user.getRole().getPermissions();
        } else {
            permissions = EnumSet.of(PermissionEnum.GUEST_BASIC);
        }
        String commandName = request.getParameter(COMMAND_PARAMETER);
        if (isNotEmpty(commandName) && CommandType.isContains(commandName)) {
            CommandType commandType = CommandType.getCommand(commandName);
            Command command = CommandMap.getInstance().getCommand(commandType);
            boolean isAllowed = command.isAllowed(permissions);
            if (!isAllowed) {
                request.getRequestDispatcher(PAGE_403).forward(request, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
