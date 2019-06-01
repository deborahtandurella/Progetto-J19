package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class RestaurantCatalogue {
    private static RestaurantCatalogue instance = null;
    private  Map<Integer, Restaurant> restaurants;
    private  int counter;
    
    private RestaurantCatalogue(){
        this.restaurants = new HashMap<>();
        this.counter = 0;
    }
    public static synchronized RestaurantCatalogue getInstance(){
        if(instance == null)
            instance = new RestaurantCatalogue();
        return instance;
    }
    
    public  int addRestaurant(String name, String address){
        
        Restaurant r = new Restaurant(name, address, ++counter);
        restaurants.put(counter, r);
        return counter;
    }
    
    public  void addMenu(HashMap<DishType,ArrayList<MenuEntry>> a, int key){
        restaurants.get(key).addEntry(a);
    }
    
    public  String findRestaurant(int k){
        if(!restaurants.containsKey(k)){
            throw new RestaurantNotFoundException();
        }
        return restaurants.get(k).toString();
    }
    
    public  void printList(){
        for(Map.Entry<Integer, Restaurant> e : restaurants.entrySet()){
            System.out.println(e.getKey() + ". " + e.getValue().toString());
        }
    }

    public ArrayList<Restaurant> returnList(){
        ArrayList<Restaurant> rest = new ArrayList<>();
        for(Map.Entry<Integer, Restaurant> e : restaurants.entrySet()){
            rest.add(e.getValue());
        }
        return rest;
    }

    public  void printMenu(int key){
       restaurants.get(key).printMenu();
    }

    public  void addCritique(int codResturant, Critique crit){
        restaurants.get(codResturant).addCritique(crit);
    }

}
    
