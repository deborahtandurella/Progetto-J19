package net.request_handler;

import application.HomeCritic;
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
        if (HomeCritic.getInstance().logIn(username, password)) {
            write(resp, Rythm.render("homeCritico.html"));
        } else
            write(resp, Rythm.render("warn.html"));
    }
}
