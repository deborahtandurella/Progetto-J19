package application.controller;

import persistence.InvalidUsernameException;
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

    public boolean signUp(String [] credential, UserType type)throws InvalidUsernameException, SQLException {
        return UserCatalogue.getInstance().userSignUp(credential,type);
    }

}
