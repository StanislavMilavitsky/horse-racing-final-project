package by.milavitsky.horse_racing.controller;

import by.milavitsky.horse_racing.entity.enums.PermissionEnum;
import by.milavitsky.horse_racing.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * The interface Command.
 * <p>
 * This is the interface for each command that the servlet handles implements.
 */
public interface Command {

    /**
     * We get an object of the router type, which is further processed by the servlet.
     *
     * @param request  HTTP servlet request
     * @param response HTTP servlet response
     * @return Router
     * @throws CommandException the command by.milavitsky.horse_racing.exception
     */
    Router execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

    boolean isAllowed(Set<PermissionEnum> permissions);
}
