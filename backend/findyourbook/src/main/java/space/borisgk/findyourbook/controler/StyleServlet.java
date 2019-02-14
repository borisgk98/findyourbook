package space.borisgk.findyourbook.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import space.borisgk.findyourbook.tool.CookieTool;
import space.borisgk.findyourbook.tool.UserValTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/get-style")
public class StyleServlet extends HttpServlet {
    @Autowired
    private UserValTool userValTool = null;

    @Override
    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("context.xml");
//        userValTool = (UserValTool)context.getBean("userValTool");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "text/css;charset=utf-8");
        if (userValTool.getUserVal(req, "login") != null
                && userValTool.getUserVal(req, "user-style") != null
                && userValTool.getUserVal(req, "user-style").equals("true")) {
            Path path = Paths.get(
                    "/findyourbook/userstyles").resolve(userValTool.getUserVal(req, "id")).resolve("style.css");
            if (path.toFile().exists()) {
                List<String> in = Files.readAllLines(Paths.get(
                        "/findyourbook/userstyles").resolve(userValTool.getUserVal(req, "id")).resolve("style.css"));
                PrintWriter pw = resp.getWriter();
                for (String s : in) {
                    pw.println(s);
                }
                return;
            }
        }
        List<String> in = Files.readAllLines(Paths.get(
                "/findyourbook/style.css"));
        PrintWriter pw = resp.getWriter();
        for (String s : in) {
            pw.println(s);
        }
    }
}
