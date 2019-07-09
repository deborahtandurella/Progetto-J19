package net.request_handler;

import application.controller.HomeUser;
import persistence.InvalidUsernameException;
import application.user.UserType;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class HomeRequest extends AddressHomeRequest{

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
        try{
            logIn(resp, username, password);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void logIn(HttpServletResponse resp, String username, String password) throws IOException, SQLException {
        try {
            UserType type = HomeUser.getInstance().logIn(username, password);
            if (type == UserType.CRITIC)
                super.homeCritic(resp,username);
            else
                super.homeRestaurantOwner(resp, username);
        }
        catch (InvalidUsernameException e) {
            write(resp, Rythm.render("warn.html", e.getMessage()));
        }

    }


}
