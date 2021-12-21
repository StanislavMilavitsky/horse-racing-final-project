package by.milavitsky.horseracing.controller;

import javax.servlet.http.HttpServletRequest;

import static by.milavitsky.horseracing.controller.CommandParameter.*;

public class
Router {
    private final String page;
    private boolean isRedirect;

    /**
     * Instantiates a new Router by request.
     *
     * @param request the request
     */
    public Router(HttpServletRequest request) {
        String url = request.getHeader(URL_REFERER);
        this.page = url.replaceAll(PAGE_START_PREFIX, BLANK);
    }

    /**
     * Instantiates a new Router by string.
     *
     * @param page the page
     */
    public Router(String page) {
        this.page = page;
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Is redirect boolean.
     *
     * @return the boolean
     */
    public boolean isRedirect() {
        return isRedirect;
    }

    /**
     * Redirect.
     */
    public void redirect() {
        this.isRedirect = true;
    }
}
