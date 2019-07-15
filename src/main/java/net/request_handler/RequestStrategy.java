package net.request_handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Interface pattern Strategy
 */
public interface RequestStrategy {
     /**
      * Sends the correct response to the user
      *
      * @param resp, the HttpServletResponse to answer to the requests of the template
      * @throws IOException
      */
     void doGet(HttpServletResponse resp) throws IOException;

     /**
      * Get the parameter in the form of the template and sends the correct answer to the request of the template
      *
      * @param req, the HttpServletRequest to get parameter
      * @param resp, the HttpServletResponse to answer to the requests of the templates
      * @throws IOException
      */
     void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException;

}
