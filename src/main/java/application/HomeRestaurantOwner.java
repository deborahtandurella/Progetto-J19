package application;


import application.database_exception.InvalidUsernameException;

public class HomeRestaurantOwner implements Home {

    private static HomeRestaurantOwner instance = null;

    public HomeRestaurantOwner() {

    }

    public static synchronized  HomeRestaurantOwner getInstance(){
        if(instance == null){
            instance = new HomeRestaurantOwner();
        }
        return instance;
    }

    public boolean logIn(String username, String psw){
        return Database.getInstance().logInRistoratore(username,psw);
    }

    public void signUp(String username, String password)throws InvalidUsernameException {
        Database.getInstance().ristoratoriSignUp(username,password);
    }


}
