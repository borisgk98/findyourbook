package space.borisgk.findyourbook.controler.page;

import space.borisgk.findyourbook.controler.tool.PathToParamsParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/search/book")
public class BookSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("bookName", req.getParameter("bookName"));
        req.getRequestDispatcher("/WEB-INF/jsp/search.jsp").forward(req, resp);
    }
}
