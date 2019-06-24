package application.controller;

import application.Critique;
import application.Database;
import application.MenuEntry;
import application.RestaurantCatalogue;
import application.restaurant_exception.NoCritiquesException;
import application.restaurant_exception.RestaurantNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeCritic implements Home {
    private static HomeCritic instance = null;

    private HomeCritic() {

    }
    public static synchronized  HomeCritic getInstance(){
        if(instance == null){
            instance = new HomeCritic();
        }
        return instance;
    }

    public void writeCritique(int codResturant, double [] voti, HashMap<MenuEntry,Double> votiPiatti,
                              String comment,String critico){

        Critique c = new Critique( voti[0], voti[1],  voti[2], voti[3],critico);
        c.voteDishes(votiPiatti);
        c.setComment(comment);
        RestaurantCatalogue.getInstance().addCritique(codResturant, c);
    }

    public String findRestaurant(int restaurantCode) {
        String tmp = null;
        try {
            tmp = RestaurantCatalogue.getInstance().findRestaurant(restaurantCode);
        } catch (RestaurantNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return tmp;
    }

    public boolean logIn(String username, String psw){
        return Database.getInstance().logInCritico(username,psw);
    }

    public void signUp(String username, String password){
        Database.getInstance().criticSignUp(username, password);
    }

    public ArrayList<Integer> getMenuCode(int restCode){
        return RestaurantCatalogue.getInstance().getMenuCode(restCode);
    }
    public MenuEntry getDish(int restCod, int dishCod){
        return RestaurantCatalogue.getInstance().getDish(restCod, dishCod);
    }

    public ArrayList<String> myCritique(String critic) throws NoCritiquesException {
        return RestaurantCatalogue.getInstance().myCritique(critic);
    }
}
