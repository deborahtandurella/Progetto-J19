package net.request_handler;

import application.CritiqueCatalogue;
import application.Restaurant;
import application.RestaurantCatalogue;
import application.restaurant_exception.EmptyMenuException;
import application.restaurant_exception.NoCritiquesException;
import net.net_exception.MissingFormParameterException;
import org.rythmengine.Rythm;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class ListRequest extends AbstractRequestStrategy {
    private static ListRequest instance = null;

    protected ListRequest() {

    }

    public static ListRequest getInstance(){
        if(instance == null)
            instance = new ListRequest();
        return instance;
    }

    @Override
    public void doGet(HttpServletResponse resp) {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try{
            String restaurantCode = req.getParameter("restaurant");
            checkNumber(restaurantCode);
            String action = req.getParameter("switch");
            String username = req.getParameter("username");
            if(action.equals("write"))
                sendCritiqueModule(Integer.parseInt(restaurantCode),resp,username);
            else
                sendRestaurantOverview(Integer.parseInt(restaurantCode),resp, username);
        }catch (MissingFormParameterException e){
            write(resp,Rythm.render("warn.html",e.getMessage()));
        }
    }

    private void sendCritiqueModule(int restaurantCode, HttpServletResponse resp, String username) throws IOException {
        Map<String, Object> conf = new HashMap<>();
        try{
            LinkedHashMap<Integer, String> piatti = RestaurantCatalogue.getInstance().getMenuInfo(restaurantCode);
            conf.put("piatti", piatti);
            conf.put("restCode", restaurantCode);
            conf.put("username", username);
            write(resp, Rythm.render("critique.html", conf));
        }catch(EmptyMenuException e){
            write(resp,Rythm.render("warn.html"));
        }
    }

    protected void sendRestaurantOverview(int restaurantCode,HttpServletResponse resp, String username)throws IOException{
        Map<String, Object> conf = new HashMap<>();
        try{

            Map<String, String> restaurantOverview = RestaurantCatalogue.getInstance()
                    .getRestaurantOverview(restaurantCode);

            conf.put("name", RestaurantCatalogue.getInstance().getRestaurantName(restaurantCode));
            conf.put("address", RestaurantCatalogue.getInstance().getRestaurantAddress(restaurantCode));
            conf.put("overview", restaurantOverview);
            conf.put("critiques", CritiqueCatalogue.getInstance().getRestaurantCritiqueToString(restaurantCode));
            conf.put("username",username);
            conf.put("votoMedio",Double.toString(RestaurantCatalogue.getInstance().getRestaurantMeanVote(restaurantCode)));
            write(resp, Rythm.render("restaurant_view.html", conf));
        }catch (NoCritiquesException e){
            NoCritiquesExceptionhandler(restaurantCode,conf,resp);
        }
    }


    private void NoCritiquesExceptionhandler(int restaurantCode,Map<String, Object> conf,HttpServletResponse resp)
            throws IOException {
        conf.put("name",RestaurantCatalogue.getInstance().getRestaurantName(restaurantCode));
        conf.put("address",RestaurantCatalogue.getInstance().getRestaurantAddress(restaurantCode));
        write(resp,Rythm.render("restaurantViewException.html",conf));
    }

    private void checkNumber(String restaurantCode){
        if(restaurantCode == null)
            throw new MissingFormParameterException("Scegliere un ristorante per continuare");
    }
}
