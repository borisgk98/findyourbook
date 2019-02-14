package space.borisgk.findyourbook.controler.api;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import space.borisgk.findyourbook.auth.Authentication;
import space.borisgk.findyourbook.model.User;
import space.borisgk.findyourbook.repository.JDBCRepository;
import space.borisgk.findyourbook.tool.CookieTool;
import space.borisgk.findyourbook.tool.ParametrMapToModel;
import space.borisgk.findyourbook.tool.UserValTool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/user/*")
public class UserServlet extends RepositoryRestApiServlet<User>{

    private UserValTool userValTool;

    @Override
    public void init() throws ServletException {
        super.init();
        repository = new JDBCRepository<User>(User.class);
        tClass = User.class;

        ApplicationContext context =
                new ClassPathXmlApplicationContext("context.xml");
        userValTool = (UserValTool)context.getBean("userValTool");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO подумать над выносом авторизации в отдельный модуль
        // TODO подумать над аутфекацией приложение для доступа к API (Предотвращение создания пользователей ботами, например по curl)
        Boolean auth = Boolean.parseBoolean(userValTool.getUserVal(req, "auth"));
        parsePathParams(req.getPathInfo());

//        if (pathParams.length != 0) {
//            answer(resp, 400, RestAnswer.builder().messange("Bad request").build());
//            return;
//        }

        if (pathParams.length > 1 && pathParams[1].equals("favoriteBook")) {
            // TODO обработка ошибок
            Integer userId = Integer.parseInt(pathParams[0]);
            User user = (User) repository.get(userId);
            List<Integer> arr = new ArrayList<>(Arrays.asList(user.getFavoriteBooks()));
            Integer id = Integer.parseInt(pathParams[2]);
            if (!arr.contains(id)) {
                arr.add(id);
                user.setFavoriteBooks(arr.toArray(new Integer[arr.size()]));
                repository.update(userId, user);
            }
            answer(resp, 201, RestAnswer.builder().messange("Added").build());
            return;
        }

        if (pathParams.length > 1 && pathParams[1].equals("favoriteAuthor")) {
            // TODO обработка ошибок
            Integer userId = Integer.parseInt(pathParams[0]);
            User user = (User) repository.get(userId);
            List<Integer> arr = new ArrayList<>(Arrays.asList(user.getFavoriteAuthors()));
            Integer id = Integer.parseInt(pathParams[2]);
            if (!arr.contains(id)) {
                arr.add(id);
                user.setFavoriteAuthors(arr.toArray(new Integer[arr.size()]));
                repository.update(userId, user);
            }
            answer(resp, 201, RestAnswer.builder().messange("Added").build());
            return;
        }

        // TODO обработка ошибок: пользователь уже существует (надо обрабатывать в репозитории), неверный емаил, короткий пароль
        User user = null;
        try {
            user = (new ParametrMapToModel<User>()).apply(req.getParameterMap(), User.class);
        } catch (IllegalArgumentException e) {
            answer(resp, 400, RestAnswer.builder().messange("Not enough arguments").exception(e).errorType("Bad arguments").build());
            return;
        }

        if (user.getLogin().length() < 6) {
            answer(resp, 400, RestAnswer.builder().messange("Login length should be longer than 6").build());
            return;
        }
        if (user.getEmail().contains("@") == false) {
            answer(resp, 400, RestAnswer.builder().messange("Wrong email address").build());
            return;
        }
        user.setHashpass(Authentication.getPasswordHash(req.getParameter("password")));
        repository.put(user);
        Authentication.identefyUser(user, resp);
        answer(resp, 201, RestAnswer.builder().messange("Created").value(user).build());
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        parsePathParams(req.getPathInfo());
        if (pathParams.length == 0) {
            // todo удалить всех
            answer(resp, 500, RestAnswer.builder().build());
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
        User user = (User) repository.get(id);
        repository.delete(id);
        answer(resp, 200, RestAnswer.builder().messange("Deleted").value(user).build());
    }
}