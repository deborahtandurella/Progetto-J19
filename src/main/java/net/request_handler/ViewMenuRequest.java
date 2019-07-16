package net.request_handler;

import application.controller.Home;
import application.restaurant_exception.EmptyMenuException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
     * @return instance(ViewMenuRequest)
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
        try{
            String restaurantCode = req.getParameter("restaurant");
            LinkedHashMap<String, List<String>> menuToString = Home.getInstance()
                    .restaurantMenuToString(restaurantCode);
            HashMap<String,Object> conf = new HashMap<>();
            conf.put("piatti",menuToString);
            conf.put("username", req.getParameter("username"));
            conf.put("restCode",req.getParameter("restaurant"));
            conf.put("name",Home.getInstance().getRestaurantName(restaurantCode));
            write(resp, Rythm.render("viewMenu.html",conf));
        } catch (SQLException e) {
            e.printStackTrace();
        }catch (EmptyMenuException e){
            write(resp,Rythm.render("warn.html","Il ristorante non ha ancora aggiunto un menù"));
        }

    }
}
