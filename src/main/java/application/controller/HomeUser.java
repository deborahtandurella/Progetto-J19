package application.controller;

import application.database_exception.InvalidUsernameException;
import application.user.UserCatalogue;
import application.user.UserType;

import java.sql.SQLException;

public class HomeUser {
    private static HomeUser instance = null;
    public HomeUser() {
    }

    public static synchronized  HomeUser getInstance(){
        if(instance == null){
            instance = new HomeUser();
        }
        return instance;
    }

    public UserType logIn(String username, String psw) throws SQLException {
        return UserCatalogue.getInstance().logInUser(username, psw);
    }

    public void signUp(String [] credential)throws InvalidUsernameException {
        UserCatalogue.getInstance().;
    }

}
