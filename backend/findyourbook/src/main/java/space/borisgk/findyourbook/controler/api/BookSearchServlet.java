package space.borisgk.findyourbook.controler.api;

import space.borisgk.findyourbook.model.Book;
import space.borisgk.findyourbook.repository.JDBCRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/search/book")
public class BookSearchServlet extends RestApiServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String bookName = req.getParameter("bookName");
        JDBCRepository<Book> bookJDBCRepository = new JDBCRepository<>(Book.class);
        List<Book> books = bookJDBCRepository.search(bookName);
        answer(resp, 200, RestAnswer.builder().value(books).build());
    }
}
