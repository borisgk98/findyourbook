package space.borisgk.findyourbook.controler.page;

import space.borisgk.findyourbook.controler.tool.PathToParamsParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] params = PathToParamsParser.parse(req.getPathInfo());
        if (params.length == 0) {
            // TODO user-all.jsp
            req.getRequestDispatcher("/WEB-INF/jsp/user-all.jsp").forward(req, resp);
        }
        req.setAttribute("userId", params[0]);
        req.getRequestDispatcher("/WEB-INF/jsp/user.jsp").forward(req, resp);
    }
}
