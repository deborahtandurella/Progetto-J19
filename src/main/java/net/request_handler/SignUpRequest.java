package net.request_handler;

import application.controller.HomeCritic;
import application.controller.HomeRestaurantOwner;
import application.database_exception.InvalidUsernameException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            signUp(type, credential);
        }catch (InvalidUsernameException e){
            write(resp, Rythm.render("warn.html",e.getMessage()));
        }

    }

    private void signUp(String type, String [] credential)throws InvalidUsernameException{
        // TODO check if params are != null
        if(type.equals("critic"))
            criticSignUp(credential);
        else if(type.equals("restaurantOwner"))
            ristoratoreSignUp(credential);

    }

    private void criticSignUp(String [] credential)throws InvalidUsernameException{
        HomeCritic.getInstance().signUp(credential);
    }

    private void ristoratoreSignUp(String [] credential)throws InvalidUsernameException {
        HomeRestaurantOwner.getInstance().signUp(credential);
    }
}
