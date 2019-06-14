package net.request_handler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractRequestStrategy implements RequestStrategy {

     private void write(HttpServletResponse resp, String message) throws IOException {
        resp.getWriter().write(message);
    }
}
