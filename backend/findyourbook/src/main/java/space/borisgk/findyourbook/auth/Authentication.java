package space.borisgk.findyourbook.auth;

import lombok.SneakyThrows;
import space.borisgk.findyourbook.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.function.Function;

public class Authentication {
    protected static final Integer cookieDuraction = 60 * 60 * 24 * 5;

    // TODO подумать над защитой от атак MAN IN THE MIDDLE

    @SneakyThrows
    public static String getPasswordHash(String id) {
        Function<String, String> f = new Function<String, String>() {
            @SneakyThrows
            @Override
            public String apply(String s) {
                s += "kek";
                return DatatypeConverter
                        .printHexBinary(MessageDigest.getInstance("MD5").digest(s.getBytes())).toUpperCase();
            }
        };
        return f.apply(f.apply(id));
    }

    @SneakyThrows
    public static String getUserSeesionId(String id) {
        Function<String, String> f = new Function<String, String>() {
            @SneakyThrows
            @Override
            public String apply(String s) {
                s += "lol";
                return DatatypeConverter
                        .printHexBinary(MessageDigest.getInstance("MD5").digest(s.getBytes())).toUpperCase();
            }
        };
        return f.apply(f.apply(id));
    }

    public static void identefyUser(User user, HttpServletResponse resp) {
        resp.addCookie(new Cookie("SUPERSECRETSESSIONID", getUserSeesionId(user.getId().toString())){{
            setPath("/");
            setMaxAge(cookieDuraction);
            setSecure(true);
        }});
        resp.addCookie(new Cookie("auth", "true"){{
            setPath("/");
            setMaxAge(cookieDuraction);
        }});
        resp.addCookie(new Cookie("login", user.getLogin()){{
            setPath("/");
            setMaxAge(cookieDuraction);
        }});
        resp.addCookie(new Cookie("id", user.getId().toString()){{
            setPath("/");
            setMaxAge(cookieDuraction);
        }});
    }
}
