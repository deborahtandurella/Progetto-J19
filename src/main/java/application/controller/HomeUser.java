package application.controller;

import application.database_exception.InvalidUsernameException;
import application.user.UserCatalogue;

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

    public boolean logIn(String username, String psw){
        return UserCatalogue.
    }

    public void signUp(String [] credential)throws InvalidUsernameException {
        UserCatalogue.getInstance().;
    }

}
