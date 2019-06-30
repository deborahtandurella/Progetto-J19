package net.request_handler;

import application.controller.HomeCritic;
import application.controller.HomeRestaurantOwner;
import application.RestaurantCatalogue;
import application.restaurant_exception.RestaurantNotFoundException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
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
        String type = req.getParameter("type");
        try{
            logIn(type,resp, username, password);
        }catch(InvalidParameterException e){
            write(resp,Rythm.render("warn.html", e.getMessage()));
        }

    }

    private void logIn(String type, HttpServletResponse resp, String username, String password) throws IOException{
        if(type == null)
            throw new InvalidParameterException();
        else if(type.equals("critico"))
            logCritic(resp, username, password);
        else if (type.equals("ristoratore"))
            logRestaurantOwner(resp, username, password);

    }
    private void logCritic(HttpServletResponse resp, String username, String password) throws IOException{
        if (HomeCritic.getInstance().logIn(username, password)) {
            write(resp, Rythm.render("homeCritico.html",username));
        } else
            write(resp, Rythm.render("warn.html"));
    }


    private void logRestaurantOwner(HttpServletResponse resp, String username, String password) throws IOException{
        if(HomeRestaurantOwner.getInstance().logIn(username,password)) {
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
}
