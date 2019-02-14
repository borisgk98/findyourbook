package space.borisgk.findyourbook.tool;

import javax.servlet.http.HttpServletRequest;

public interface UserValTool {
    String getUserVal(HttpServletRequest req, String name);
}
