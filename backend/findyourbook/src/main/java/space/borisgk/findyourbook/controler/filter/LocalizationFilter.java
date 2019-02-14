package space.borisgk.findyourbook.controler.filter;

import space.borisgk.findyourbook.localization.Localization;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebFilter("/*")
public class LocalizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String lang = request.getParameter("lang");

        if (lang != null) {
            Cookie cookie = new Cookie("locale", lang);
            // TODO что то сделать с константами, касающиемися кукис
            cookie.setMaxAge(60 * 60 * 24);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        else if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("locale")) {
                    lang = cookie.getValue();
                }
            }
            if (lang == null) {
                lang = "ru";
            }
        }

        Map<String, String> locale = Localization.getInstance().getLocale(lang);
        request.setAttribute("locale", locale);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
