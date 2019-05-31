package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class HomeRestaurantOwner {
    
    private boolean loggedIn;
    private final RestaurantRead rr;
    private MenuRead mr;
    private HashSet<Integer> auth = new HashSet<>();
    
    public HomeRestaurantOwner(String filerestaurant) throws FileNotFoundException {
        this.loggedIn = false;
        this.rr = new RestaurantRead(filerestaurant);
        this.mr = null;
    }
    
    public void addRestaurant() throws IOException {
        String[] tmp;
        tmp = this.rr.fileRead();
        auth.add(RestaurantCatalogue.getInstance().addRestaurant(tmp[0], tmp[1]));
    }
    
    public void addMenu(int key, String path) throws IOException {
        this.mr = new MenuRead(path);
        HashMap<DishType,ArrayList<MenuEntry>> e = new HashMap<>();
        
        if (auth.contains(key)) {
            e = mr.fileRead();
        }
        RestaurantCatalogue.getInstance().addMenu(e, key);
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
    
    
}
