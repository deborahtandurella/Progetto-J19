package application;

import application.restaurant_exception.EmptyMenuException;
import application.restaurant_exception.NoCritiquesException;

import java.util.*;

/**
 * A restaurant , it can be created by a restaurant owner
 */
public class Restaurant {
    private final String name;
    private final String address;
    private HashMap<DishType,ArrayList<MenuEntry>> menu ;
    private RestaurantOverview overview;
    private String owner ;
    private String city;
    private MenuHandler menuHandler;

    /**
     * Create a new restaurant .
     * @param name the name of the restaurant
     * @param address the address of the restaurant
     * @param owner the username of the restaurant's owner
     */
    public Restaurant(String name, String address, String owner, String city){
        this.name = name;
        this.address = address;
        this.menu = null;
        this.overview = new RestaurantOverview();
        this.owner = owner;
        this.city = city;
        this.menuHandler = new MenuHandler();
    }

    /**
     * Add a new menu to the restaurant.
     * @param menu the menu of the restaurant
     */
    public synchronized void addMenu(HashMap<DishType,ArrayList<MenuEntry>> menu){
        this.menu = menu;
    }

    @Override
    public String toString() {
        return this.name + "&" + this.address + "&" + this.overview.toString();
    }
    

    /**
     * Method which is called when a new critique about the restaurant is written.
     *
     * It updates the restaurant overview.
     * @param overview the updated overview of the restaurant
     */
    public void setOverview(RestaurantOverview overview) {
        this.overview = overview;
    }

    /**
     * Method called when  the names of the dishes in the restaurant's menu are required.
     *
     * @return a map whose keys are the codes of the dishes and the values are the name of the dishes
     */
    public LinkedHashMap<String, String> getMenuInfo () {
        if(this.menu == null)
            throw new EmptyMenuException("menu non presente");
        LinkedHashMap<String,String> temp = new LinkedHashMap<>();
        for (DishType dt : DishType.values()) {
            for (MenuEntry me:this.menu.get(dt)) {
                temp.put(me.getCod(),me.getDish());
            }
        }
        return temp;
    }

    /**
     * Method called when a critic writes a critique about the restaurant, in order to match each grade with its dish
     *
     * @return a list of the codes of the dishes
     */
    public ArrayList<String> getMenuCode(){
        ArrayList<String> tmp = new ArrayList<>();
        for (DishType e : this.menu.keySet()) {
            for(MenuEntry me: this.menu.get(e)){
                tmp.add(me.getCod());
            }
        }
        return tmp;
    }

    /**
     * Method called before the writing of a critique.
     * @param cod is a code of one of the dishes
     *
     * @return the dish (matching the code) which is voted by a critic
     */
    public MenuEntry getDish(String cod){
        for (Map.Entry<DishType,ArrayList<MenuEntry>> a: this.menu.entrySet()) {
            for (MenuEntry me:a.getValue()) {
                if(me.getCod().equals(cod)) {
                    return me;
                }
            }
        }
        return null;
    }

    /**
     * Method called when the overview of the restaurant has to be sent to restaurant_view.html in order to be
     * display in the web page.
     *
     * @return a map whose keys are the names of the sections of the overview and values are their grades
     */
    public HashMap<String, String> getOverview(){
        HashMap<String, String> temp = new HashMap<>();
        for (CritiqueSections i : CritiqueSections.values()) {
            temp.put((String.valueOf(i)),
                    String.format("%.2f", this.overview.getSections().get(i)).replace(",","."));
        }
        return temp;
    }

    /**
     * Method called when the restaurant's owner adds a dish to the menu of the restaurant.
     *
     * It adds a new dish to the menu of the restaurant.
     *
     * @param dish the name of the dish
     */
    public void checkMenuEntryExistence(String dish){
        if(this.menu == null){
            this.menu = new HashMap<>();
            this.menu = this.menuHandler.initializeMenu(this.menu);
        }
        else
            this.menuHandler.checkExistance(dish,this.menu);
    }

    /**
     * Called by addMenuEntry.
     * Adds a new dish to the menu of the restaurant.
     *
     * @param dishType the type of the dish(antipasto,primo...)
     * @param dish the name of the dish
     * @param price the price of the dish
     * @param dishCode the code by which the system identifies the dish
     * @param restaurantCode the code of the restaurant to whom the MenuEntry has to be added
     */
    public synchronized MenuEntry addMenuEntryToMenu(String dishType,String dish, double price, String dishCode, String restaurantCode){
        MenuEntry me = new MenuEntry(dish,price,dishCode,restaurantCode,dishType);
        this.menu.get(this.menuHandler.stringConverter(dishType)).add(me);
        return me;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCityAddress(){
        return city+", "+address;
    }

    public String getOwner() {
        return owner;
    }

    /**
     * @return the mean  of the votes of the overview's sections
     */
    public double getMeanVote(){
        return this.overview.getMean();
    }

    public String getCity() {
        return city;
    }

    public void removeDishFromMenu(String dishCode){
        this.menuHandler.removeDishFromMenu(dishCode,menu);
    }

    public LinkedHashMap<String, List<String>> menuToString(){
        if (this.menu == null)
            throw new EmptyMenuException();
        return this.menuHandler.menuToString(menu);
    }
}
