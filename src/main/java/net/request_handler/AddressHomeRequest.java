package net.request_handler;

import application.controller.Home;
import application.restaurant_exception.RestaurantNotFoundException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract class
 * It contains method to show the home page of an user (critic or restaurant owner).
 *
 */
public abstract class AddressHomeRequest extends AbstractRequestStrategy {

    /**
     * Used tho show to a critic his home page
     *
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @param username of the user (a critic)
     * @throws IOException
     */
    protected void homeCritic(HttpServletResponse resp, String username) throws IOException {
        write(resp, Rythm.render("homeCritico.html",username));
    }

    /**
     * Used tho show to a restaurant owner his home page
     *
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @param username of the user (a restaurant owner)
     * @throws IOException
     */
    protected void homeRestaurantOwner(HttpServletResponse resp, String username) throws IOException{
        Map<String, Object> conf = new HashMap<>();
        try {
            conf.put("myRest", Home.getInstance().getOwnedRestaurant(username));
            conf.put("exception","false");
        }
        catch (RestaurantNotFoundException e){
            conf.put("exception", "true");
        }
        conf.put("username", username);
        write(resp, Rythm.render("homeRistoratore.html", conf));
    }

}