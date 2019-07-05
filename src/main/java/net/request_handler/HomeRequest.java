package net.request_handler;

import application.controller.HomeCritic;
import application.controller.HomeRestaurantOwner;
import application.RestaurantCatalogue;
import application.controller.HomeUser;
import application.restaurant_exception.RestaurantNotFoundException;
import application.user.UserType;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class HomeRequest extends  AbstractRequestStrategy {

    private static HomeRequest instance = null;

    private HomeRequest(){
    }

    public static HomeRequest getInstance(){
        if(instance == null)
            instance = new HomeRequest();
        return instance;
    }
    @Override
    public void doGet(HttpServletResponse resp) throws IOException {
        write(resp, Rythm.render(("home.html")));
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try{
            logIn(resp, username, password);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void logIn(HttpServletResponse resp, String username, String password) throws IOException, SQLException {
        try {
            UserType type = HomeUser.getInstance().logIn(username, password);
            if (type == UserType.CRITIC)
                logCritic(resp,username);
            else
                logRestaurantOwner(resp, username);
        }
        catch (InvalidParameterException e) {
            write(resp, Rythm.render("warn.html", e.getMessage()));
        }
    }
    private void logCritic(HttpServletResponse resp, String username) throws IOException{
            write(resp, Rythm.render("homeCritico.html",username));
    }


    private void logRestaurantOwner(HttpServletResponse resp, String username) throws IOException{
        Map<String, Object> conf = new HashMap<>();
        try {
            conf.put("myRest", HomeRestaurantOwner.getInstance().getOwnedRestaurant(username));
            conf.put("exception","false");
        }
        catch (RestaurantNotFoundException e){
            conf.put("exception", "true");
        }
        conf.put("username", username);
        write(resp, Rythm.render("homeRistoratore.html", conf));
    }
}
