package application.controller;

import application.*;
import application.database_exception.InvalidUsernameException;
import application.restaurant_exception.NoCritiquesException;
import application.restaurant_exception.RestaurantNotFoundException;
import application.user.UserCatalogue;

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

        Critique c = new Critique(critico,codResturant,0);
        c.writeVotes(voti);
        votiPiatti = this.removeNullDishes(votiPiatti);
        c.voteDishes(votiPiatti);
        c.setComment(comment);
        CritiqueCatalogue.getInstance().addNewCritique(c);
    }


    public boolean logIn(String username, String psw){
        return UserCatalogue.getInstance().logInCritic(username,psw);
    }

    public void signUp(String username, String password, String name, String surname)throws InvalidUsernameException {
        UserCatalogue.getInstance().criticSignUp(username,password, name, surname);
    }

    public ArrayList<Integer> getMenuCode(int restCode){
        return RestaurantCatalogue.getInstance().getMenuCode(restCode);
    }
    public MenuEntry getDish(int restCod, int dishCod){
        return RestaurantCatalogue.getInstance().getDish(restCod, dishCod);
    }

    public ArrayList<String> myCritique(String critic) throws NoCritiquesException {
        return CritiqueCatalogue.getInstance().getCritiquesByUser(critic);
    }

    private HashMap<MenuEntry, Double> removeNullDishes(HashMap<MenuEntry, Double> dv){
        ArrayList<MenuEntry> temp = new ArrayList<>();
        for (Map.Entry<MenuEntry, Double> dish : dv.entrySet()){
            if(dish.getValue() == 0){
                temp.add(dish.getKey());
            }
        }
        for (MenuEntry dish : temp){
            dv.remove(dish);
        }
        return dv;
    }
}
