package net.request_handler;

import application.controller.Home;
import application.restaurant_exception.NoCritiquesException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;

public class MyRestaurantActionRequest extends OverviewRequest {

    private static MyRestaurantActionRequest instance = null;

    private MyRestaurantActionRequest(){ }

    public static MyRestaurantActionRequest getInstance(){
        if(instance == null)
            instance = new MyRestaurantActionRequest();
        return instance;
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try{
            if (req.getParameter("switch").equals("discover"))
                sendRestaurantOverview(req.getParameter("restaurant"), resp, req.getParameter("username"),
                        Home.getInstance().getRestaurantCritiqueToString(req.getParameter("restaurant")));

            else if(req.getParameter("switch").equals("modifyMenu"))
                sendEditMenuTmpl(req,resp);
        }catch (NoCritiquesException e){
            HashMap<String,Object> conf = new HashMap<>();
            NoCritiquesExceptionhandler(req.getParameter("restaurant"),conf,resp);
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            write(resp,Rythm.render("warn.html"));
        }
    }

    private void sendEditMenuTmpl(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        HashMap<String,Object> conf = new HashMap<>();
        String restaurantCode = req.getParameter("restCode");
        conf.put("piatti",Home.getInstance().getMenuInfo(restaurantCode));
        conf.put("restCode",restaurantCode);
        conf.put("username",req.getParameter("username"));
        conf.put("name",Home.getInstance().getRestaurantName(restaurantCode));
        write(resp, Rythm.render("editMenu.html",conf));
    }
}
