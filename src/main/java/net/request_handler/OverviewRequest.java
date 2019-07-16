package net.request_handler;

import application.RestaurantCatalogue;
import application.controller.Home;
import application.restaurant_exception.NoCritiquesException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract class
 * It contains method to show restaurant'overview, used by the class 'MyRestaurantActionRequest' and 'ListRequest',
 * which both extend the class.
 */
public abstract class OverviewRequest extends AbstractRequestStrategy {

    /**
     * Method which get the information to show the page of a restaurant, with its overview and critiques
     *
     * @param restaurantCode, the code of the restaurant which the user want visualize
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @param username, the username of the user who is making the request
     * @param critList, list of critiques that belong to the restaurant
     * @throws IOException
     */
    protected void sendRestaurantOverview(String restaurantCode, HttpServletResponse resp, String username,
                                          List<String> critList)throws IOException {
        Map<String, Object> conf = new HashMap<>();
        try {

            Map<String, String> restaurantOverview = RestaurantCatalogue.getInstance()
                    .getRestaurantOverview(restaurantCode);
            conf.put("restaurant", restaurantCode);
            conf.put("name", Home.getInstance().getRestaurantName(restaurantCode));
            conf.put("address", Home.getInstance().getRestaurantAddress(restaurantCode));
            conf.put("overview", restaurantOverview);
            conf.put("critiques", critList);
            conf.put("username", username);
            conf.put("votoMedio", String.format("%.2f",
                    (Home.getInstance().getRestaurantMeanVote(restaurantCode))).replace(",","."));
            write(resp, Rythm.render("restaurantView.html", conf));
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("SWLException: "+e.getMessage());
        }catch (NoCritiquesException e){
            NoCritiquesExceptionhandler(restaurantCode,conf,resp,username);
        }
    }

    /**
     * Method which manages the 'NoCritiquesException' (if a restaurant has no critiques)
     *
     * @param restaurantCode, the code of the restaurant of the critiques
     * @param conf, the HashMap used to pass the parameter to 'Rythme.render'
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    protected void NoCritiquesExceptionhandler(String restaurantCode,Map<String, Object> conf,HttpServletResponse resp,
                                               String username) throws IOException {
        try {
            conf.put("username",username);
            conf.put("name", Home.getInstance().getRestaurantName(restaurantCode));
            conf.put("address", Home.getInstance().getRestaurantAddress(restaurantCode));
            write(resp, Rythm.render("restaurantViewException.html", conf));
        }catch (SQLException e){
            e.printStackTrace();
            SQLExcwptionHandler(resp);
        }
    }
    @Override
    public void doGet(HttpServletResponse resp) {

    }
}
