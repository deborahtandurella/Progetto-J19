package net.request_handler;

import application.RestaurantCatalogue;
import application.restaurant_exception.EmptyMenuException;
import application.restaurant_exception.NoCritiquesException;
import org.rythmengine.Rythm;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

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
        String username = req.getParameter("username");
        if(action.equals("write"))
            sendCritiqueModule(restauantCode,resp,username);
        else
            sendRestaurantOverview(restauantCode,resp, username);
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
    private void sendRestaurantOverview(int restaurantCode,HttpServletResponse resp, String username)throws IOException{
        Map<String, Object> conf = new HashMap<>();
        try{
            Map<String, String> info = RestaurantCatalogue.getInstance().getRestaurantOverview(restaurantCode);
            Map<String, String> meanCritique = RestaurantCatalogue.getInstance()
                    .getRestaurantMeanCritique(restaurantCode);

            conf.put("name", info.get("name"));
            conf.put("address", info.get("address"));
            conf.put("meanCritique", meanCritique);
            conf.put("crit",this.parseCritique(info.get("overview").split("&")));
            conf.put("username",username);
            conf.put("votoMedio",votoMedio(meanCritique));
            write(resp, Rythm.render("restaurant_view.html", conf));
        }catch (NoCritiquesException e){
            NoCritiquesExceptionhandler(restaurantCode,conf,resp);
        }
    }


    private void NoCritiquesExceptionhandler(int restaurantCode,Map<String, Object> conf,HttpServletResponse resp)
            throws IOException {
        String [] restInfo = RestaurantCatalogue.getInstance().findRestaurant(restaurantCode)
                .split(",");
        conf.put("name",restInfo[0]);
        conf.put("address",restInfo[1]);
        write(resp,Rythm.render("restaurantViewException.html",conf));
    }

    private LinkedList<String> parseCritique(String[] critique){
        LinkedList<String> token = new LinkedList<>();
        for(String cr : critique){
            String tmp [] = cr.split("£");
            for(int i=0;i < tmp.length; i++){
                token.add(tmp[i]);
            }
        }
        return token;
    }

    private String votoMedio(Map<String, String> meanCritique) {
        double votoMedio = 0;
        for (Map.Entry<String, String> a : meanCritique.entrySet()) {
            votoMedio = Double.parseDouble(a.getValue()) + votoMedio;
        }
        return String.format("%.2f", votoMedio/meanCritique.size()).replace(",",".");
    }

}
