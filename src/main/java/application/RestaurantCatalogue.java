package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO methods for single restaurant's info
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

    public ArrayList<String> getRestaurantOverview(){
        ArrayList<String> rest = new ArrayList<>();
        for(Map.Entry<Integer, Restaurant> e : restaurants.entrySet()){
            rest.add(e.getValue().toString());
        }
        return rest;
    }

    public  void printMenu(int key){
       restaurants.get(key).printMenu();
    }

    public  void addCritique(int codResturant, Critique crit){
        restaurants.get(codResturant).addCritique(crit);
    }

    public Map<Integer,String> getRestaurantInfo(){
        Map<Integer, String> rest = new HashMap<>();
        for(Map.Entry<Integer, Restaurant> e : restaurants.entrySet()){
            rest.put(e.getKey(),e.getValue().getRestaurantInfo());
        }
        return rest;
    }

    public  Map<Integer, String>  getMenu(int restaurantCode){
        return this.restaurants.get(restaurantCode).getMenu();
    }

}
    
