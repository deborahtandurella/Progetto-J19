package net.request_handler;

import application.RestaurantCatalogue;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomeCriticoRequest extends AbstractRequestStrategy {

    private static HomeCriticoRequest instance = null;

    private HomeCriticoRequest(){
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
        Map<Integer, String> restaurant = RestaurantCatalogue.getInstance().getRestaurantInfo();
        Map<String, Object> param = new HashMap<>();
        param.put("restaurant",restaurant);
        param.put("sw",tmp);
        write(resp, Rythm.render("list.html", param));
    }
}
