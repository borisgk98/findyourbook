package space.borisgk.findyourbook.controler;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import space.borisgk.findyourbook.tool.CookieTool;
import space.borisgk.findyourbook.tool.UserValTool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO фильтр для компиляции файла stylus
@WebServlet("/change-style")
public class ChangeStyleServlet extends HttpServlet {
    @Autowired
    private UserValTool userValTool;

    @Override
    public void init() throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, getServletContext());
//        ApplicationContext context =
//                new ClassPathXmlApplicationContext("context.xml");
//        userValTool = (UserValTool)context.getBean("userValTool");

    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = userValTool.getUserVal(req, "id");
        if (userId == null) {
            resp.setStatus(403);
            return;
        }
        String colorFirst = req.getParameter("color");
//        String colorWrapper = req.getParameter("color-wrapper");
//        String colorNavA = req.getParameter("color-nav-a");
        Path style = Paths.get("/findyourbook/style.styl");
        Path outDir = Paths.get("/findyourbook/userstyles/").resolve(userId);
        if (outDir.toFile().exists() == false) {
            Files.createDirectory(outDir);
        }
        String colors = String.format("color-first = #%s\n" +
                "color-wrapper = #fcfcfc\n" +
                "color-nav-a = #ffffff", colorFirst);
        try {
            Files.copy(style, outDir.resolve("style.styl"));
        }
        catch (FileAlreadyExistsException e) {}
        Files.write(outDir.resolve("colors.styl"), colors.getBytes());
        Process process = new ProcessBuilder("stylus", outDir.resolve("style.styl").toString()).start();
        process.waitFor();
        resp.addCookie(new Cookie("user-style", "true"));
        resp.setStatus(200);
    }
}
