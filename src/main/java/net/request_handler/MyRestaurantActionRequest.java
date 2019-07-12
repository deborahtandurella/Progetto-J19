package net.request_handler;

import application.controller.Home;
import application.restaurant_exception.NoCritiquesException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
                //todo costruire metodo che modifica il menu
                System.out.println("costruire metedo modifica menu");
        }catch (NoCritiquesException e){
            HashMap<String,Object> conf = new HashMap<>();
            NoCritiquesExceptionhandler(req.getParameter("restaurant"),conf,resp);
        }
    }
}
