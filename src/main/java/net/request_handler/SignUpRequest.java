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
        try{
            signUp(type,username,password, name, surname);
        }catch (InvalidUsernameException e){
            write(resp, Rythm.render("warn.html",e.getMessage()));
        }

    }

    private void signUp(String type, String username, String password, String name, String surname)throws InvalidUsernameException{
        // TODO check if params are != null
        if(type.equals("critic"))
            criticSignUp(username, password, name, surname);
        else if(type.equals("restaurantOwner"))
            ristoratoreSignUp(username, password, name, surname);

    }

    private void criticSignUp(String username, String password, String name, String surname)throws InvalidUsernameException{
        HomeCritic.getInstance().signUp(username, password, name, surname);
    }

    private void ristoratoreSignUp(String username,String password, String name, String surname)throws InvalidUsernameException {
        HomeRestaurantOwner.getInstance().signUp(username, password, name, surname);
    }
}
