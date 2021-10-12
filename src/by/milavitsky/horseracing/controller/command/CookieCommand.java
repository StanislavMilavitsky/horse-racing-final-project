package by.milavitsky.horseracing.controller.command;

import by.milavitsky.horseracing.controller.Command;
import by.milavitsky.horseracing.controller.Router;
import by.milavitsky.horseracing.entity.enums.PermissionEnum;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Set;

import static by.milavitsky.horseracing.controller.CommandParameter.*;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isNoneEmpty;

public class CookieCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        String locale = request.getParameter(PARAM_COOKIE_LOCALE);
        String language;
        if (isNoneEmpty(locale) && equalsIgnoreCase(locale, LOCALE_LANGUAGE_RU)) {
            language = new Locale(LOCALE_LANGUAGE_RU, LOCALE_COUNTRY_RU).toString();
        } else {
            language = new Locale(LOCALE_LANGUAGE_EN, LOCALE_COUNTRY_US).toString();
        }
        response.addCookie(new Cookie(PARAM_COOKIE_LOCALE, language));
        Router router = new Router(request);
        router.redirect();
        return router;    }

    @Override
    public boolean isAllowed(Set<PermissionEnum> permissions) {
        return true;
    }
}
