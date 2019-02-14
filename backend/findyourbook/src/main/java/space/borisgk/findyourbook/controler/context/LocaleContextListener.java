package space.borisgk.findyourbook.controler.context;

import space.borisgk.findyourbook.localization.Localization;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class LocaleContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("locale", Localization.getInstance());
    }
}
