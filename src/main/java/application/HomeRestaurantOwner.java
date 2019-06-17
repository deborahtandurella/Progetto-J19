package application;



public class HomeRestaurantOwner {

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
}
