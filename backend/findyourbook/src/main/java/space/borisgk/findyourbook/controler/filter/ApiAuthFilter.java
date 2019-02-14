package space.borisgk.findyourbook.controler.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import java.io.IOException;

/** TODO
фильтр для регистрации приложений в api.
нужно для защиты от атак через api, например заполнить всю базу put-запросами
 */

//@WebFilter("/api/*")
//public class ApiAuthFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//}
