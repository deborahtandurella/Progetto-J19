package net.request_handler;

import application.controller.Home;
import application.restaurant_exception.RestaurantAlreadyExistingException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AddRestaurantRequest extends AbstractEditMenu{
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
            String restaurantCode = addRestaurant(req);
            sendMenuAddTmpl(req.getParameter("owner"),restaurantCode,resp);

        }catch (RestaurantAlreadyExistingException e){
            write(resp,Rythm.render("warn.html",e.getMessage()));
        }catch (SQLException e){
            SQLExcwptionHandler(resp);
            e.printStackTrace();
        }
    }

    private String addRestaurant(HttpServletRequest req) throws RestaurantAlreadyExistingException, SQLException {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String ownerUsername = req.getParameter("owner");
        return Home.getInstance().addRestaurant(name,address,ownerUsername);
    }
}
