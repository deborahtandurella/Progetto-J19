package net.request_handler;

import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeRistoratoreRequest extends HomeUserRequest {

    private static HomeRistoratoreRequest instance = null;

    private HomeRistoratoreRequest() {
        super();
    }


    public static HomeRistoratoreRequest getInstance(){

        if(instance == null)
            instance = new HomeRistoratoreRequest();
        return instance;
    }


    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if (req.getParameter("switch").equals("discover"))
            super.doPost(req, resp);
        else if(req.getParameter("switch").equals("add"))
            write(resp, Rythm.render("addRestaurant.html",req.getParameter("username")));
        else if(req.getParameter("switch").equals("viewMyRest"))
            myRest(req,resp);
    }

    private void myRest(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        int restCode = Integer.parseInt(req.getParameter("restaurant"));
        String username = req.getParameter("username");
        write(resp, Rythm.render("myRestaurantAction.html", restCode, username));
    }
}
