package application;

import application.restaurant_exception.RestaurantAlreadyExistingException;
import application.restaurant_exception.RestaurantNotFoundException;
import persistence.OIDCreator;
import persistence.PersistenceFacade;
import persistence.RestaurantsMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Singleton class
 * A catalogue of the restaurants which are registered in the system
 */
public  class RestaurantCatalogue {
    private static RestaurantCatalogue instance = null;
    private  int counter;

    /**
     * Create a new RestaurantCatalogue
     * initialize restaurants, HashMap of the restaurants('key':= code of restaurant, 'value':= the restaurant)
     * initialize counter, the counter used to generate the code of the restaurant in the system
     */
    private RestaurantCatalogue(){
        this.counter = 0;
    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(RestaurantCatalogue)
     */
    public static synchronized RestaurantCatalogue getInstance(){
        if(instance == null)
            instance = new RestaurantCatalogue();
        return instance;
    }

    /**
     * Add a restaurant in the catalogue system
     * @param name of the restaurant
     * @param address of the restaurant
     * @param owner of the restaurant(username)
     * @return the counter of the class used to create the code of the new restaurant
     * @throws RestaurantAlreadyExistingException
     */
    public  String addRestaurant(String name, String address, String owner) throws RestaurantAlreadyExistingException{
        checkExisting(name, address);
        Restaurant r = new Restaurant(name, address, owner,"city~~~");
        String restaurantCode = OIDCreator.getInstance().getNewRestaurantCode();
        PersistenceFacade.getInstance().addRestaurant(restaurantCode,r);
        return restaurantCode;
    }

    /**
     * Add a new menu to the restaurant selected by the code
     * @param menu the menu of the restaurant
     * @param key the code of the restaurant
     */
    public  void addMenu(HashMap<DishType,ArrayList<MenuEntry>> menu, String key){
        getRestaurant(key).addMenu(menu);
    }

    /**
     * Method for debugging
     * @param key
     */
    public  void printMenu(String key) {
        getRestaurant(key).printMenu();
    }

    /**
     * Method which is called when an user wants to see the list of the restaurants in the system
     *
     * @return a map whose keys are the code of the restaurants and the values are the name of the restaurants
     */
    public Map<Integer,String> getAllRestaurantName(){
        Map<Integer, String> rest = new HashMap<>();
        for(Map.Entry<String, Restaurant> e : getAllRestaurants().entrySet()){
            rest.put(Integer.parseInt(e.getKey()),e.getValue().getName());
        }
        return rest;
    }

    /**
     * Method which is called when a critic write a critique of a restaurant,
     * in order to show the menu of the restaurant
     *
     * @param restaurantCode the code oh the restaurant
     * @return a map whose keys are the code of the of the dishes of the restaurant and the values are the name of the dishes
     */
    public LinkedHashMap<String, String>  getMenuInfo(String restaurantCode){
        return getRestaurant(restaurantCode).getMenuInfo();
    }

    /**
     * Method which is called by 'addRestaurant' to check if a new restaurant which is in registration step,
     * is already in the system
     *
     * @param name of the restaurant to register
     * @param address of the restaurant to register
     */
    private void checkExisting(String name, String address){
        Map<String, Restaurant> restaurantsCopy = getAllRestaurants();
        if(restaurantsCopy.isEmpty())
            return;
        for (String code : restaurantsCopy.keySet()) {
            if (checkInfo(code, name, address))
                throw new RestaurantAlreadyExistingException("Il ristorante è già presente nel sistema !");
        }
    }

    /**
     * Called by 'checkExisting' to verify if the name and address input are the same of the ones of the restaurant
     * identified by the code
     *
     * @param code of the restaurant in the system
     * @param name of the restaurant in registration step
     * @param address of the restaurant in registration step
     * @return a boolean (true if name or address are the same of the restaurant in the system)
     */
    private boolean checkInfo(String code,String name, String address){
        Map<String, Restaurant> restaurantsCopy = getAllRestaurants();
        return restaurantsCopy.get(code).getName().equals(name) &&
                restaurantsCopy.get(code).getAddress().equals(address);
    }

    /**
     * Method which is called to show to restaurant's owner the list of his restaurants
     *
     * @param owner of the restaurants (username)
     * @return a map whose keys are the code of the of the restaurants and the values are the name of the restaurants
     */
    public Map<Integer, String> myRestaurant(String owner){
        HashMap<Integer,String> myRest = new HashMap<>();
        for(Map.Entry<String, Restaurant> restaurant:getAllRestaurants().entrySet()) {
            if (restaurant.getValue().getOwner().equals(owner))
                myRest.put(Integer.parseInt(restaurant.getKey()), restaurant.getValue().getName());
        }
        if(myRest.isEmpty())
            throw new RestaurantNotFoundException("Nessun ristorante in tuo possesso");
        return myRest;
    }

    /**
     * Method called when the restaurant's owner adds a dish to the menu of the restaurant.
     * It adds a new dish to a menu of a restaurant
     *
     * @param restaurantCode the code of the restaurant
     * @param dishType the type of the dish
     * @param dishName the name of the dish
     * @param price the price of the dish
     */
    public void addMenuEntry(String restaurantCode,String dishType,String dishName, double price){
       Restaurant r = getRestaurant(restaurantCode);
       r.checkMenuEntryExistence(dishType, dishName, price);
       MenuEntry me = r.addMenuEntryToMenu(dishType,dishName,price,OIDCreator.getInstance().getNewMenuEntryCode()
               ,restaurantCode);
       PersistenceFacade.getInstance().addMenuEntry(me);

    }

    public ArrayList<String> getMenuCode(String restCode){
        return getRestaurant(restCode).getMenuCode();
    }

    public MenuEntry getDish(String restCode, String dishCode){
        return getRestaurant(restCode).getDish(dishCode);
    }

    public HashMap<String,String> getRestaurantOverview(String restCode){
        return getRestaurant(restCode).getOverview();
    }
    public String getRestaurantName(String restaurantCode){
        return getRestaurant(restaurantCode).getName();
    }
    public String getRestaurantAddress(String restaurantCode){
        return getRestaurant(restaurantCode).getAddress();
    }

    public void setRestaurantOverview(String restaurantCode,RestaurantOverview overview){
        getRestaurant(restaurantCode).setOverview(overview);
    }

    public double getRestaurantMeanVote(String restaurantCode){
        return  getRestaurant(restaurantCode).getMeanVote();
    }

    private Restaurant getRestaurant(String restaurantCode){
        try {
            return (Restaurant) PersistenceFacade.getInstance().get(restaurantCode, RestaurantsMapper.class);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, Restaurant> getAllRestaurants(){
        return PersistenceFacade.getInstance().getAllRestaurants();
    }

}
    
