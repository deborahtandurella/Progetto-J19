package net.request_handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RequestStrategy {
     void doGet(HttpServletResponse resp);
     void doPost(HttpServletRequest req, HttpServletResponse resp);

}
