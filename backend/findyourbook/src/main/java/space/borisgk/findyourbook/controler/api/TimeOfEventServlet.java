package space.borisgk.findyourbook.controler.api;

import space.borisgk.findyourbook.model.TimeOfEvent;
import space.borisgk.findyourbook.repository.JDBCRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/timeOfEvent/*")
public class TimeOfEventServlet extends RepositoryRestApiServlet<TimeOfEvent> {
    @Override
    public void init() throws ServletException {
        super.init();
        repository = new JDBCRepository(TimeOfEvent.class);
        tClass = TimeOfEvent.class;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
