package application;

import application.restaurant_exception.RestaurantNotFoundException;

import java.util.HashMap;

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

}
