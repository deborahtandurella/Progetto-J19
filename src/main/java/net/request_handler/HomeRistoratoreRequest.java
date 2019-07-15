package net.request_handler;

import net.net_exception.MissingFormParameterException;
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
        try{
            String restCode = req.getParameter("restaurant");
            checkParam(restCode);
            String username = req.getParameter("username");
            write(resp, Rythm.render("myRestaurantAction.html", restCode, username));
        }catch (MissingFormParameterException e){
            write(resp,Rythm.render("warn.html",e.getMessage()));
        }
    }

    private void checkParam(String restCode){
        if(restCode == null)
            throw new MissingFormParameterException("Selezionare un ristorante per poter continuare");
    }
}
