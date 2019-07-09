package net.request_handler;

import application.controller.Home;
import application.restaurant_exception.EmptyMenuException;
import net.net_exception.MissingFormParameterException;
import org.rythmengine.Rythm;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ListRequest extends OverviewRequest {
    private static ListRequest instance = null;

    protected ListRequest() {

    }

    public static ListRequest getInstance(){
        if(instance == null)
            instance = new ListRequest();
        return instance;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try{
            String restaurantCode = req.getParameter("restaurant");
            checkNumber(restaurantCode);
            String action = req.getParameter("switch");
            String username = req.getParameter("username");
            if(action.equals("write"))
                sendCritiqueModule(restaurantCode,resp,username);
            else
                sendRestaurantOverview(restaurantCode,resp, username);
        }catch (MissingFormParameterException e){
            write(resp,Rythm.render("warn.html",e.getMessage()));
        }
    }

    private void sendCritiqueModule(String restaurantCode, HttpServletResponse resp, String username) throws IOException {
        Map<String, Object> conf = new HashMap<>();
        try{
            LinkedHashMap<String, String> piatti = Home.getInstance().getMenuInfo(restaurantCode);
            conf.put("piatti", piatti);
            conf.put("restCode", restaurantCode);
            conf.put("username", username);
            write(resp, Rythm.render("critique.html", conf));
        }catch(EmptyMenuException e){
            write(resp,Rythm.render("warn.html", e.getMessage()));
        }
    }

    private void checkNumber(String restaurantCode){
        if(restaurantCode == null)
            throw new MissingFormParameterException("Scegliere un ristorante per continuare");
    }
}
