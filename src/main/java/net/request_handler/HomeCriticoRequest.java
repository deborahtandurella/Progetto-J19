package net.request_handler;

import application.RestaurantCatalogue;
import application.controller.HomeCritic;
import application.restaurant_exception.NoCritiquesException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeCriticoRequest extends AbstractRequestStrategy {

    private static HomeCriticoRequest instance = null;

    protected HomeCriticoRequest(){
    }

    public static HomeCriticoRequest getInstance(){
        if(instance == null)
            instance = new HomeCriticoRequest();
        return instance;
    }
    @Override
    public void doGet(HttpServletResponse resp) throws IOException {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tmp = req.getParameter("switch");
        if(!tmp.equals("viewCrit"))
            postList(req,resp,tmp);
        else
            postCritiquesView(req, resp);

    }

    private void postList(HttpServletRequest req, HttpServletResponse resp, String tmp) throws IOException{
        String username = req.getParameter("username");
        Map<Integer, String> restaurant = RestaurantCatalogue.getInstance().getRestaurantInfo();
        Map<String, Object> param = new HashMap<>();
        param.put("restaurant", restaurant);
        param.put("sw", tmp);
        param.put("username", username);
        write(resp, Rythm.render("list.html", param));
    }

    private void postCritiquesView(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        try {
            String tmp = req.getParameter("username");
            Map<String, Object> conf =new HashMap<>();
            conf.put("critique", HomeCritic.getInstance().myCritique(tmp));
            conf.put("username", tmp);
            write(resp,Rythm.render("myCritiques.html", conf));
        }
        catch (NoCritiquesException e){
            write(resp, Rythm.render("warn.html", e.getMessage()));
        }
    }
}
