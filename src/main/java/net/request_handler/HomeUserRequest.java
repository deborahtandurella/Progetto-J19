package net.request_handler;

import application.RestaurantCatalogue;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class
 * It contains methods used by the class 'HomeCriticoRequest' and 'HomeRistoratoreRequest',
 * which both extend the class.
 */
public abstract class HomeUserRequest extends AbstractRequestStrategy {

    /**
     * It posts the page with the list of restaurant
     *
     * @param req, the HttpServletRequest to get parameter
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tmp = req.getParameter("switch");
            postList(req,resp,tmp);

    }

    /**
     * Method which gets te information to print the list page of restaurants
     *
     * @param req, the HttpServletRequest to get parameter
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @param tmp, this is a parameter used to decide with action the user wants to do after have selected
     *             a restaurant
     * @throws IOException
     */
    protected void postList(HttpServletRequest req, HttpServletResponse resp, String tmp) throws IOException{
        String username = req.getParameter("username");
        Map<Integer, String> restaurant = RestaurantCatalogue.getInstance().getAllRestaurantName();
        Map<String, Object> param = new HashMap<>();
        param.put("restaurant", restaurant);
        param.put("sw", tmp);
        param.put("username", username);
        write(resp, Rythm.render("list.html", param));
    }

    @Override
    public void doGet(HttpServletResponse resp) throws IOException {

    }

}
