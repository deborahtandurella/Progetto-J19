package net.request_handler;

import net.net_exception.InvalidURIException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Class context in pattern strategy
 */
public class RythmCliqueServlet extends HttpServlet {
    /**
     * A list of the name of the right URI which can be accepted
     */
    private String [] rightRequest = {"/home", "/homeCritico", "/list", "/critique","/homeRistoratore", "/signUp"
            ,"/addRestaurant","/myRestaurantAction","/addMenu", "/restaurantView","/editMenu", "/homeButton"};


    /**
     * Method used to create the instance of the correct class to respond to the template request
     *
     * @param URI the template name
     * @return the instance of the class
     * @throws InvalidURIException
     */
    private RequestStrategy setRequestStrategy(String URI) throws InvalidURIException {
        RequestStrategy rs = null;

        try {
            isURIValid(URI);
            String tmp = parseURI(URI);
            rs = (RequestStrategy)Class.forName(tmp).getMethod("getInstance").invoke(null);
        } catch (ClassNotFoundException |NoSuchMethodException |InvocationTargetException
                | IllegalAccessException e) {
            e.printStackTrace();
        }

        return rs;
    }

    /**
     * Method which calls the method 'doGet()' of the right class for the right template thanks to 'setRequestStrategy()'
     * @param request, the HttpServletRequest to get parameter
     * @param response, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        String tmp = request.getRequestURI();
        try {
            setRequestStrategy(tmp).doGet(response);
        }catch (InvalidURIException e){
            if(!(e.getMessage().equals("/favicon.ico")))
                e.printStackTrace();
        }
    }

    /**
     * Method which calls the method 'doPost()' of the right class for the right template thanks to 'setRequestStrategy()'
     * @param request, the HttpServletRequest to get parameter
     * @param response, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        try {
            setRequestStrategy(request.getRequestURI()).doPost(request, response);
        }catch (InvalidURIException e){
            if(!(e.getMessage().equals("/favicon.ico")))
                e.printStackTrace();

        }
    }

    /**
     * Checks if the URI is valid thanks to the attributes rightRequest og the class.
     * @param URI
     */
    private void isURIValid(String URI){
        ArrayList<String> requestList =  ArrayToList(this.rightRequest);
        if(!(requestList.contains(URI)))
            throw new InvalidURIException(URI);
    }


    private ArrayList<String> ArrayToList(String [] s){
        ArrayList<String> list = new ArrayList<>();
        for(String i: s)
            list.add(i);
        return list;
    }

    /**
     * Modifies the URI received, so that it can be used in 'setRequestStrategy()' to use the correct request handler
     * with that name
     * @param uri
     * @return
     */
    private String parseURI(String uri){
        String path = "net.request_handler.";
        String uri1 = String.valueOf(uri.toCharArray(), 1,1).toUpperCase();
        String uri2 = String.valueOf(uri.toCharArray(), 2,uri.length()-2);
        return path+uri1+uri2+"Request";
    }
}
