package net.request_handler;

import application.HomeCritic;
import application.HomeRestaurantOwner;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HomeRequest extends  AbstractRequestStrategy {

    private static HomeRequest instance = null;

    private HomeRequest(){
    }

    public static HomeRequest getInstance(){
        if(instance == null)
            instance = new HomeRequest();
        return instance;
    }
    @Override
    public void doGet(HttpServletResponse resp) throws IOException {
        write(resp, Rythm.render(("home.html")));
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String type = req.getParameter("type");
        if(type.equals("critico"))
          logCritico(resp, username, password);
        else if (type.equals("ristoratore"))
            logRistoratore(resp, username, password);
        else{
            //eccezione
        }
    }


    public void logCritico(HttpServletResponse resp, String username, String password) throws IOException{
        if (HomeCritic.getInstance().logIn(username, password)) {
            write(resp, Rythm.render("homeCritico.html"));
        } else
            write(resp, Rythm.render("warn.html"));
    }


    public void logRistoratore(HttpServletResponse resp, String username, String password) throws IOException{
        if(HomeRestaurantOwner.getInstance().logIn(username,password))
            write(resp, Rythm.render("home_ristoratore.html"));
    }
}
