package application;

import application.restaurant_exception.NoCritiquesException;
import application.restaurant_exception.RestaurantAlreadyExistingException;
import application.restaurant_exception.RestaurantNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


// TODO methods for single restaurant's info
public  class RestaurantCatalogue {
    private static RestaurantCatalogue instance = null;
    private  Map<Integer, Restaurant> restaurants;
    private  int counter;
    private  int counterCrit;

    private RestaurantCatalogue(){
        this.restaurants = new HashMap<>();
        this.counter = 0;
        this.counterCrit = 0;
    }
    public static synchronized RestaurantCatalogue getInstance(){
        if(instance == null)
            instance = new RestaurantCatalogue();
        return instance;
    }
    
    public  int addRestaurant(String name, String address, String owner) throws RestaurantAlreadyExistingException{

        checkExisting(name, address);
        Restaurant r = new Restaurant(name, address, ++counter, owner);
        restaurants.put(counter, r);
        return counter;

    }
    
    public  void addMenu(HashMap<DishType,ArrayList<MenuEntry>> a, int key){
        restaurants.get(key).addMenu(a);
    }
    
    public  String findRestaurant(int k){
        if(!restaurants.containsKey(k)){
            throw new RestaurantNotFoundException();
        }
        return restaurants.get(k).getRestaurantInfo();
    }

    public  void printMenu(int key){
       restaurants.get(key).printMenu();
    }

    public  void addCritique(int codResturant, Critique crit){
        crit.setCode(++counterCrit);
        restaurants.get(codResturant).addCritique(crit);
    }

    public Map<Integer,String> getRestaurantInfo(){
        Map<Integer, String> rest = new HashMap<>();
        for(Map.Entry<Integer, Restaurant> e : restaurants.entrySet()){
            rest.put(e.getKey(),e.getValue().getRestaurantInfo());
        }
        return rest;
    }

    public LinkedHashMap<Integer, String>  getMenuInfo(int restaurantCode){
        return this.restaurants.get(restaurantCode).getMenuInfo();
    }

    public ArrayList<Integer> getMenuCode(int restCode){
        return this.restaurants.get(restCode).getMenuCode();
    }

    public MenuEntry getDish(int restCod, int dishCod){
        return this.restaurants.get(restCod).getDish(dishCod);
    }

    public HashMap<String,String> getRestaurantOverview(int restCod){
        return this.restaurants.get(restCod).getOverview();
    }

    public HashMap<String,String> getRestaurantMeanCritique(int restCod){
        return this.restaurants.get(restCod).getMeanCritique();
    }

    private void checkExisting(String name, String address){
        if(this.restaurants.isEmpty())
            return;
        for (int i:this.restaurants.keySet()) {
            if (checkInfo(i,name,address))
                throw new RestaurantAlreadyExistingException("Il ristorante è già presente nel sistema !");
        }
    }
    private boolean checkInfo(int code,String name, String address){
        return this.restaurants.get(code).getName().equals(name) &&
                this.restaurants.get(code).getAddress().equals(address);
    }

    public Map<Integer, String> myRestaurant(String owner){
        HashMap<Integer,String> myRest = new HashMap<>();
        for(Map.Entry<Integer, Restaurant> restaurant: this.restaurants.entrySet()) {
            if (restaurant.getValue().getOwner().equals(owner))
                myRest.put(restaurant.getKey(), restaurant.getValue().getName());
        }
        if(myRest.isEmpty())
            throw new RestaurantNotFoundException("Nessun ristorante in tuo possesso");
        return myRest;
    }

    public ArrayList<String> myCritique(String critic){
        ArrayList<String> critique = new ArrayList<>();
        for(Map.Entry<Integer, Restaurant> restaurant: this.restaurants.entrySet()) {
            for(Critique crit : restaurant.getValue().getCritiques()) {
                if (crit.getCritico().equals(critic))
                    critique.add(crit.myCritique(restaurant.getValue().getName()));
            }
        }
        if(critique.isEmpty())
            throw new NoCritiquesException("Nessuna critica ancora compilata!");
        return critique;
    }

    public void addMenuEntry(int restaurantCode,String dishType,String dish, double price) {
        this.restaurants.get(restaurantCode).addMenuEntry(dishType,dish,price);
    }

}
    
