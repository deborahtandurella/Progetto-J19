package net.request_handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Singleton class (concreteStrategy)
 */
public class ViewMenuRequest extends AbstractRequestStrategy {
    private static ViewMenuRequest instance = null;

    private ViewMenuRequest() {
    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(HomeButtonRequest)
     */
    public static ViewMenuRequest getInstance(){
        if(instance == null)
            instance = new ViewMenuRequest();
        return instance;
    }

    @Override
    public void doGet(HttpServletResponse resp) throws IOException {

    }

    /**
     * Show the menu of the restaurant
     *
     * @param req, the HttpServletRequest to get parameter
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        
    }
}
