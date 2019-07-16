package application.controller;

import application.*;
import application.restaurant_exception.NoCritiquesException;
import application.restaurant_exception.RestaurantNotFoundException;
import persistence.PersistenceFacade;

import java.sql.SQLException;
import java.util.*;

/**
 * Singleton class
 * A controller for the application
 */
public class Home {

    private static Home instance = null;

    public Home() {
    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(Home)
     */
    public static synchronized Home getInstance(){
        if(instance == null){
            instance = new Home();
        }
        return instance;
    }

    /**
     * @param username, the name of the owner of the restaurant
     * @return the Map of the restaurant owned by the selected user
     * @throws RestaurantNotFoundException
     */
    public Map<Integer, String> getOwnedRestaurant(String username)throws RestaurantNotFoundException {
        return RestaurantCatalogue.getInstance().myRestaurant(username);
    }

    /**
     * It enters a new restaurant in the system
     *
     * @param name of the new restaurant
     * @param address of the new restaurant
     * @param owner of the new restaurant
     * @return the code of the new restaurant
     */
    public String addRestaurant(String name, String address,String city, String owner)throws SQLException{
        return RestaurantCatalogue.getInstance().addRestaurant(name,address,city,owner);
    }

    /**
     * It enters a new menu of a restaurant
     *
     * @param restaurantCode
     * @param dishType the type of the new dish
     * @param dish, the new dish
     * @param price, the price of the dish
     */
    public void addMenuEntry(String restaurantCode,String dishType,String dish, double price)throws SQLException {
        RestaurantCatalogue.getInstance().addMenuEntry(restaurantCode,dishType,dish,price);
    }

    /**
     * @param codResturant
     * @param voti of the CritiqueSection
     * @param votiPiatti, grades of the dishes valuated
     * @param comment
     * @param critico, the author of the critique
     */
    public void writeCritique(String codResturant, double [] voti, HashMap<MenuEntry,Double> votiPiatti,
                              String comment,String critico)throws SQLException{

        Critique c = new Critique(critico,codResturant,0);
        c.writeVotes(voti);
        votiPiatti = this.removeNullDishes(votiPiatti);
        c.voteDishes(votiPiatti);
        c.setComment(comment);
        CritiqueCatalogue.getInstance().addNewCritique(c);
    }

    /**
     * @param restCode
     * @return the menu of a restaurant
     */
    public ArrayList<String> getMenuCode(String restCode)throws SQLException{
        return RestaurantCatalogue.getInstance().getMenuCode(restCode);
    }

    /**
     * @param restCod
     * @param dishCod
     * @return the dish of the restaurant
     */
    public MenuEntry getDish(String restCod, String dishCod)throws SQLException{
        return RestaurantCatalogue.getInstance().getDish(restCod, dishCod);
    }

    /**
     * Method used to select the critiques of a critic
     *
     * @param critic, tha author opg the critiques
     * @return  the Critiques in string format
     * @throws NoCritiquesException
     */
    public ArrayList<String> myCritique(String critic) throws NoCritiquesException,SQLException {
        return CritiqueCatalogue.getInstance().getCritiquesByUser(critic);
    }

    /**
     * Method which is called to show to an user the overview of a restaurant with its critiques
     *
     * @param restCode, the critiques to print
     * @return critiques, the list of the critiques of the restaurant in String format
     */
    public LinkedList<String> getRestaurantCritiqueToString(String restCode){
        return CritiqueCatalogue.getInstance().getRestaurantCritiqueToString(
                                                        CritiqueCatalogue.getInstance().getRestaurantCritics(restCode));
    }

    /**
     * Method which select the critiques with a section with a grade>= of the vote
     *
     * @param grade,  the vote used to select the critiques
     * @param restCode code of the restaurant
     * @param section of the critiques
     * @return only the critiques which verify the condition
     */
    public LinkedList<String> getRestCritByVoteSectionToString(int grade, String restCode, CritiqueSections section){
        return CritiqueCatalogue.getInstance().getRestCritByVoteSectionToString(grade, restCode, section);
    }

    /**
     *Method which select the critiques with a mean >= of the grade
     *
     *  @param grade, the vote used to select the critiques
     *  @return only the critiques which verify the condition
     */
    public LinkedList<String> getRestCritByVoteToString(int grade, String restCode){
        return CritiqueCatalogue.getInstance().getRestCritByVoteToString(grade, restCode);
    }

    /**
     * Method used to delate the dishes which have been note valuated
     * @param dv the Map of dishes
     * @return the Map of dishes cleaned
     */
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

    public String getRestaurantName(String restaurantCode)throws SQLException{
        return RestaurantCatalogue.getInstance().getRestaurantName(restaurantCode);
    }

    public String getRestaurantAddress(String restaurantCode)throws SQLException{
        return RestaurantCatalogue.getInstance().getRestaurantAddress(restaurantCode);
    }

    public double getRestaurantMeanVote(String restaurantCode)throws SQLException{
        return RestaurantCatalogue.getInstance().getRestaurantMeanVote(restaurantCode);
    }

    public Map<Integer,String> getAllRestaurantName(){
        return RestaurantCatalogue.getInstance().getAllRestaurantName();
    }

    /**
     * Method which is called when a critic write a critique of a restaurant,
     * in order to show the menu of the restaurant
     *
     * @param restaurantCode the code oh the restaurant
     * @return a map whose keys are the code of the of the dishes of the restaurant and the values are the name of the dishes
     */
    public LinkedHashMap<String, String>  getMenuInfo(String restaurantCode)throws SQLException{
        return RestaurantCatalogue.getInstance().getMenuInfo(restaurantCode);
    }

    public void removeDish(String dishCode,String restaurantCode) throws SQLException {
        PersistenceFacade.getInstance().removeDish(dishCode, restaurantCode);
    }

    public LinkedHashMap<String, List<String>> restaurantMenuToString(String restaurantCode) throws SQLException {
        return RestaurantCatalogue.getInstance().restaurantMenuToString(restaurantCode);
    }
}
