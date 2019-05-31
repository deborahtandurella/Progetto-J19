package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class RestaurantCatalogue {
    private static Map<Integer, Restaurant> restaurants = new HashMap<>();
    private static int counter = 0;
    
    private RestaurantCatalogue(){
        
    }
    
    public static int addRestaurant(String name, String address){
        
        Restaurant r = new Restaurant(name, address, ++counter);
        restaurants.put(counter, r);
        return counter;
    }
    
    public static void addMenu(HashMap<DishType,ArrayList<MenuEntry>> a, int key){
        restaurants.get(key).addEntry(a);
    }
    
    public static String findRestaurant(int k){
        if(!restaurants.containsKey(k)){
            throw new RestaurantNotFoundException();
        }
        return restaurants.get(k).toString();
    }
    
    public static void printList(){
        for(Map.Entry<Integer, Restaurant> e : restaurants.entrySet()){
            System.out.println(e.getKey() + ". " + e.getValue().toString());
        }
    }
    
    public static void printMenu(int key){
       restaurants.get(key).printMenu();
    }

    public static void addCritique(int codResturant, Critique crit){
        restaurants.get(codResturant).addCritique(crit);
    }

}
    
