package net.request_handler;

import application.controller.HomeCritic;
import application.controller.HomeRestaurantOwner;
import application.controller.HomeUser;
import application.database_exception.InvalidUsernameException;
import application.user.UserType;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.SQLException;

public class SignUpRequest extends AbstractRequestStrategy {
    private static SignUpRequest instance = null;

    private SignUpRequest() {
    }

    public static SignUpRequest getInstance(){
        if(instance == null)
            instance = new SignUpRequest();
        return instance;
    }

    @Override
    public void doGet(HttpServletResponse resp) throws IOException {
        write(resp,Rythm.render("signUp.html"));
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String [] credential = {username, password, name, surname};
        try{
            signUp(type, credential, resp);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void signUp(String type, String [] credential, HttpServletResponse resp)throws IOException, SQLException {
        if(!(HomeUser.getInstance().signUp(credential, UserType.valueOf(type)))){
            write(resp,Rythm.render("warn.html", "Username non valido, già in uso!"));
        }
        else {
            /* TODO crete abstract class which is extended by 'SignUpRequest' and 'HomeRequest'.
               TODO the abstract class has the method to write the template 'HomeCritic' and
               TODO 'HomeRestaurantOwner'*/
            write(resp, Rythm.render("home.html"));
        }
    }

}
