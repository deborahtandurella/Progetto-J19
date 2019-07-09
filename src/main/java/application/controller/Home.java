package application.controller;


import application.RestaurantCatalogue;
import application.restaurant_exception.RestaurantNotFoundException;

import java.util.Map;

public class Home {

    private static Home instance = null;

    public Home() {

    }

    public static synchronized Home getInstance(){
        if(instance == null){
            instance = new Home();
        }
        return instance;
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
