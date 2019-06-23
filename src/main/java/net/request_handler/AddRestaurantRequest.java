package net.request_handler;

import application.RestaurantCatalogue;
import application.restaurant_exception.RestaurantAlreadyExistingException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddRestaurantRequest extends AbstractRequestStrategy {
    private static  AddRestaurantRequest instance = null;

    private AddRestaurantRequest() {
    }

    public static AddRestaurantRequest getInstance(){
        if(instance == null)
            instance = new AddRestaurantRequest();
        return instance;
    }

    @Override
    public void doGet(HttpServletResponse resp) throws IOException {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int restaurantCode = addRestaurant(req);
        }catch (RestaurantAlreadyExistingException e){
            write(resp,Rythm.render("warn.html",e.getMessage()));
        }
    }

    private int addRestaurant(HttpServletRequest req) throws RestaurantAlreadyExistingException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String ownerUsername = req.getParameter("owner");
        return RestaurantCatalogue.getInstance().addRestaurant(name,address,ownerUsername);
    }
}
