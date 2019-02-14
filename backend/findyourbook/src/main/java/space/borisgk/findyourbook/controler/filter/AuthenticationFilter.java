package space.borisgk.findyourbook.controler.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import space.borisgk.findyourbook.auth.Authentication;
import space.borisgk.findyourbook.tool.CookieTool;
import space.borisgk.findyourbook.tool.UserValTool;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/api/*")
public class AuthenticationFilter implements Filter {
    @Autowired
    protected UserValTool userValTool = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, filterConfig.getServletContext());
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("context.xml");
//        userValTool = (UserValTool)context.getBean("userValTool");
    }

    /*
        берет id пользователя, берет от него хеш, id и хеш записываються в cookies пользователя
         */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String id = userValTool.getUserVal(req, "id"), sessId = userValTool.getUserVal(req, "SUPERSECRETSESSIONID");
        if (id == null || sessId == null || !sessId.equals(Authentication.getUserSeesionId(id))) {
            Cookie cookie = new Cookie("auth", "false");
            cookie.setPath("/");
            resp.addCookie(cookie);
        }
        else {
            Cookie cookie = new Cookie("auth", "true");
            cookie.setPath("/");
            resp.addCookie(cookie);
        }
        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
