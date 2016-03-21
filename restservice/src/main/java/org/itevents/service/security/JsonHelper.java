package org.itevents.service.security;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ramax on 3/20/16.
 */
public class JsonHelper {
    void sendJson(HttpServletResponse response, Integer statusCode, String message) throws IOException {
        String jsonObject = "{" + "\"error\": \"" + message + "\"" + "}";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(statusCode);
        PrintWriter out = response.getWriter();
        out.print(jsonObject);
        out.flush();
        out.close();
    }
}
