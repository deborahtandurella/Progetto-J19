package net.request_handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Interface pattern Strategy
 */
public interface RequestStrategy {
     void doGet(HttpServletResponse resp) throws IOException;
     void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException;

}
