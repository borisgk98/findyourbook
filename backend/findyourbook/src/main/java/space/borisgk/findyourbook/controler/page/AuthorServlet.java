package space.borisgk.findyourbook.controler.page;

import space.borisgk.findyourbook.controler.tool.PathToParamsParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/author/*")
public class AuthorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] params = PathToParamsParser.parse(req.getPathInfo());
        if (params.length == 0) {
            if (req.getParameter("page") == null) {
                req.setAttribute("page", 1);
            }
            else {
                try {
                    req.setAttribute("page", Integer.parseInt(req.getParameter("page")));
                }
                catch (NumberFormatException e) {
                    req.setAttribute("page", 1);
                }
            }
            req.getRequestDispatcher("/WEB-INF/jsp/author-all.jsp").forward(req, resp);
        }
        req.setAttribute("authorId", params[0]);
        req.getRequestDispatcher("/WEB-INF/jsp/author.jsp").forward(req, resp);
    }
}
