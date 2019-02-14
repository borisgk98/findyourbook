package space.borisgk.findyourbook.controler.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import space.borisgk.findyourbook.controler.tool.PathToParamsParser;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class RestApiServlet extends HttpServlet {
    protected String[] pathParams;

    protected void parsePathParams(String path) {
        pathParams = PathToParamsParser.parse(path);
    }

    protected void answer(HttpServletResponse resp, Integer statusCode, RestAnswer restAnswer) {
        resp.setStatus(statusCode);
        resp.setHeader("Content-Language", "en");
        resp.setHeader("Content-Type", "text/json;charset=utf-8");
        PrintWriter pw;
        try {
            pw = resp.getWriter();
        }
        catch (IOException e) {
            resp.setStatus(500);
            return;
        }
        try {
            pw.println((new ObjectMapper()).writeValueAsString(restAnswer));
        }
        catch (JsonProcessingException e) {
            resp.setStatus(500);
            pw.println(String.format("{ errorType: \"%s\", exception: \"%s\", exceptionStackTrace: \"%s\" }",
                    "Server error",
                    e.toString(),
                    e.getStackTrace().toString()));
        }
    }
}
