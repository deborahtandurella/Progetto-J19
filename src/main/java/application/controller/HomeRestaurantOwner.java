package application.controller;


import application.Database;
import application.RestaurantCatalogue;
import application.database_exception.InvalidUsernameException;
import application.restaurant_exception.RestaurantNotFoundException;

import java.util.Map;

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


    public Map<Integer, String> getOwnedRestaurant(String username)throws RestaurantNotFoundException {

        return RestaurantCatalogue.getInstance().myRestaurant(username);
    }

    public int addRestaurant(String name, String address, String owner){
        return RestaurantCatalogue.getInstance().addRestaurant(name,address,owner);
    }

    public void addMenuEntry(int restaurantCode,String dishType,String dish, double price){
        RestaurantCatalogue.getInstance().addMenuEntry(restaurantCode,dishType,dish,price);
    }


}
