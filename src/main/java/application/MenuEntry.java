package application;

/**
 * A dish, or what is offered by the restaurant's menu
 */

public class MenuEntry {

    private final String dish;
    private final double price;
    private int code;
    private int restaurantCode;
    private String type;

    /**
     * Create a new menuEntry .
     * @param dish the name of the the menuEntry which is written in the menu
     * @param price of the menuEntry
     * @param code the code by which  the menuEntry is identified in the system
     */
    public MenuEntry( String dish, double price, int code, int restaurantCode, String type){
        this.dish = dish;
        this.code = code;
        this.price = price;
        this.restaurantCode = restaurantCode;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.dish + " " + this.price;
    }

    public String getDish() {
        return dish;
    }

    public double getPrice() {
        return price;
    }

    public int getCod() {
        // TODO CHANGE ITS NAME IN GODDAMN ENGLISH!!!
        return code;
    }

    public int getRestaurantCode() {
        return restaurantCode;
    }

    public String getType() {
        return type;
    }
}
