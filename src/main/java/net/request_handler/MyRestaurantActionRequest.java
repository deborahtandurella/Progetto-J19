package net.request_handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        if (req.getParameter("switch").equals("discover"))
            sendRestaurantOverview(req.getParameter("restaurant"), resp, req.getParameter("username"));

        else if(req.getParameter("switch").equals("modifyMenu"))
            //todo costruire metodo che modifica il menu
            System.out.println("costruire metedo modifica menu");
    }
}
