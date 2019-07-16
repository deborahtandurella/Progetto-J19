package net.request_handler;

import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Abstract class which implements Interface "RequestStrategy" (Pattern strategy).
 * It is extended by all the the concreteStrategy
 */
public abstract class AbstractRequestStrategy implements RequestStrategy {

    /**
     * Method to response with templates
     *
     * @param resp, the HttpServletResponse uses to answer to the requests of the templates
     * @param message, the name of the template used in the server response
     * @throws IOException
     */
     protected void write(HttpServletResponse resp, String message) throws IOException {
        resp.getWriter().write(message);
    }

    protected void SQLExcwptionHandler(HttpServletResponse resp) throws IOException{
            write(resp, Rythm.render("warn.html","Server offline.Ci scusiamo per il disagio"));
    }
}
