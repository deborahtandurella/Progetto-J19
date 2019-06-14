package net.request_handler;

import application.RestaurantCatalogue;
import application.RestaurantException.EmptyMenuException;
import application.RestaurantException.NoCritiquesException;
import org.rythmengine.Rythm;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ListRequest extends AbstractRequestStrategy {
    private static ListRequest instance = null;

    private ListRequest() {

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
        int restauantCode = Integer.parseInt(req.getParameter("restaurant"));
        String action = req.getParameter("switch");
        if(action.equals("write"))
            sendCritiqueModule(restauantCode,resp);
        else
            sendRestaurantOverview(restauantCode,resp);
    }

    private void sendCritiqueModule(int restaurantCode, HttpServletResponse resp) throws IOException {
        Map<String, Object> conf = new HashMap<>();
        try{
            LinkedHashMap<Integer, String> piatti = RestaurantCatalogue.getInstance().getMenuInfo(restaurantCode);
            conf.put("piatti", piatti);
            conf.put("restCode", restaurantCode);
            write(resp, Rythm.render("critique.html", conf));
        }catch(EmptyMenuException e){
            write(resp,Rythm.render("warn.html"));
        }
    }
    private void sendRestaurantOverview(int restaurantCode,HttpServletResponse resp)throws IOException{
        Map<String, Object> conf = new HashMap<>();
        try{
            Map<String, String> info = RestaurantCatalogue.getInstance().getRestaurantOverview(restaurantCode);
            Map<String, Double> meanCritique = RestaurantCatalogue.getInstance()
                    .getRestaurantMeanCritique(restaurantCode);

            conf.put("name", info.get("name"));
            conf.put("address", info.get("address"));
            conf.put("meanCritique", meanCritique);
            conf.put("crit",this.ArrayToList(info.get("overview").split("&")));
            write(resp, Rythm.render("restaurant_viewPROVA.html", conf));
        }catch (NoCritiquesException e){
            NoCritiquesExceptionhandler(restaurantCode,conf,resp);
        }
    }

    private ArrayList<String> ArrayToList(String [] s){
        ArrayList<String> list = new ArrayList<>();
        for(String i: s)
            list.add(i);
        return list;
    }
    private void NoCritiquesExceptionhandler(int restaurantCode,Map<String, Object> conf,HttpServletResponse resp)
            throws IOException {
        String [] restInfo = RestaurantCatalogue.getInstance().findRestaurant(restaurantCode)
                .split(",");
        conf.put("name",restInfo[0]);
        conf.put("address",restInfo[1]);
        write(resp,Rythm.render("restaurantViewException.html",conf));
    }

}
