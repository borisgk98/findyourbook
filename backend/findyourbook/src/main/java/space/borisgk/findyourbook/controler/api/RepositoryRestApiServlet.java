package space.borisgk.findyourbook.controler.api;

import space.borisgk.findyourbook.auth.Authentication;
import space.borisgk.findyourbook.model.Model;
import space.borisgk.findyourbook.model.User;
import space.borisgk.findyourbook.repository.JDBCRepository;
import space.borisgk.findyourbook.tool.CookieTool;
import space.borisgk.findyourbook.tool.ParametrMapToModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class RepositoryRestApiServlet<T> extends RestApiServlet {
    protected JDBCRepository repository;
    protected Class<T> tClass;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        parsePathParams(req.getPathInfo());
        // возвращаем всех
        if (pathParams.length == 0) {
            Integer start = 0, stop = 25;
            if (req.getParameter("stop") != null) {
                try {
                    stop = Integer.parseInt(req.getParameter("stop"));
                }
                catch (NumberFormatException e) {
                    answer(resp, 400, RestAnswer.builder().messange("'stop' param should be number!").build());
                    return;
                }
            }
            if (req.getParameter("start") != null) {
                try {
                    start = Integer.parseInt(req.getParameter("start"));
                }
                catch (NumberFormatException e) {
                    answer(resp, 400, RestAnswer.builder().messange("'start' param should be number!").build());
                    return;
                }
            }
            List<T> elems = repository.findAll(start, stop);
            answer(resp, 200, RestAnswer
                    .builder()
                    .value(elems)
                    .build());
            return;
        }
        // возвращаем одного
        Integer id = null;
        try {
            id = Integer.parseInt(pathParams[0]);
        }
        catch (NumberFormatException e) {
            answer(resp, 400, RestAnswer
                    .builder()
                    .messange("Bad request")
                    .exception(e)
                    .build());
            return;
        }
        // TODO обработка ошибок
        T elem = (T) repository.get(id);
        answer(resp, 200, RestAnswer
                .builder()
                .value(elem)
                .build());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}
