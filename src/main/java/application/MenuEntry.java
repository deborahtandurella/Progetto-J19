package application;

/**
 * A dish, or what is offered by the restaurant's menu
 */

public class MenuEntry {

    private final String dish;
    private final double price;
    private String code;
    private String restaurantCode;
    private String type;

    /**
     * Create a new menuEntry .
     * @param dish the name of the the menuEntry which is written in the menu
     * @param price of the menuEntry
     * @param code the code by which  the menuEntry is identified in the system
     */
    public MenuEntry( String dish, double price, String code, String restaurantCode, String type){
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

    public String getCod() {
        // todo change name!!
        return code;
    }

    public String getRestaurantCode() {
        return restaurantCode;
    }

    public String getType() {
        return type;
    }
}
