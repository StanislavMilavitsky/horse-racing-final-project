package by.milavitsky.horse_racing.controller;

import by.milavitsky.horse_racing.dao.pool.ConnectionManager;
import by.milavitsky.horse_racing.dao.pool.ProxyConnection;
import by.milavitsky.horse_racing.exception.CommandException;
import by.milavitsky.horse_racing.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import  static org.apache.commons.lang3.StringUtils.isEmpty;

import static by.milavitsky.horse_racing.controller.CommandParameter.*;


/**
 * The type Dispatcher servlet.
 * <p>
 * Servlet that handles requests and issues responses.
 */
public class DispatcherServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);

    private static final CommandMap factory = CommandMap.getInstance();
    private ProxyConnection manager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter(COMMAND_PARAMETER);
        if (isEmpty(command) || !CommandType.isContains(command)) {
            req.getRequestDispatcher(PAGE_404).forward(req, resp);
            return;
        }
        Command commandAction = factory.getCommand(CommandType.getCommand(command));
        if (commandAction != null) {
            Router router;
            try {
                router = commandAction.execute(req, resp);
            } catch (CommandException e) {
                logger.error(e.getMessage(),e);
                req.getRequestDispatcher(PAGE_500).forward(req, resp);
                return;
            }
            if (router == null) {
                logger.error("Null servlet forward");
                req.getRequestDispatcher(PAGE_404).forward(req, resp);
                return;
            }
            if (router.isRedirect()) {
                logger.info("Redirect: " + router.getPage());
                resp.sendRedirect(router.getPage());
            } else {
                logger.info("Forward: " + router.getPage());
                getServletContext().getRequestDispatcher(router.getPage()).forward(req, resp);
            }
        }
    }

    @Override
    public void init() {
        try {
            manager = ConnectionManager.get();
        } catch (DaoException e) {
            logger.info("Connection Pool cant get connection!", e);
        }
        logger.info("Connection Pool started!");
    }

    @Override
    public void destroy() {
        if (manager != null) {
            ConnectionManager.closePool();
            logger.info("Connection Pool shutdown!");
        }
    }
}
