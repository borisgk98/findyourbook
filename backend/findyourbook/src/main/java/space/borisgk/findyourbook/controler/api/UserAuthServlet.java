package space.borisgk.findyourbook.controler.api;

import space.borisgk.findyourbook.auth.Authentication;
import space.borisgk.findyourbook.model.User;
import space.borisgk.findyourbook.repository.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/auth/*")
public class UserAuthServlet extends RestApiServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login"), password = req.getParameter("password");
        UserRepository userRepository = new UserRepository();
        User user = userRepository.get(login);
        if (user.getHashpass().equals(Authentication.getPasswordHash(password))) {
            Authentication.identefyUser(user, resp);
            answer(resp, 200, RestAnswer.builder().messange("Authorization allow").build());
            return;
        }
        else {
            answer(resp, 403, RestAnswer.builder().messange("Authorization forbidden").build());
            return;
        }
    }
}
