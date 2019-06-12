package application;

import application.RestaurantException.RestaurantNotFoundException;

import java.util.HashMap;

public class HomeCritic {
    private static HomeCritic instance = null;

    private HomeCritic() {

    }
    public static synchronized  HomeCritic getInstance(){
        if(instance == null){
            instance = new HomeCritic();
        }
        return instance;
    }

    public void writeCritique(int codResturant, double [] voti, HashMap<MenuEntry,Double> votiPiatti, String comment){

        Critique c = new Critique( voti[0], voti[1],  voti[2], voti[3]);
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
        return Database.getInstance().logIn(username,psw);
    }

}
