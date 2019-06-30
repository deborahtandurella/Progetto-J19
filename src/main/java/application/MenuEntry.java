package application;

/**
 * A dish, or what is offered by the restaurant's menu
 */

public class MenuEntry {

    private final String dish;
    private final double price;
    private int cod;

    /**
     * Create a new menuEntry .
     * @param dish the name of the the menuEntry which is written in the menu
     * @param price of the menuEntry
     * @param cod the code by which  the menuEntry is identified in the system
     */
    public MenuEntry( String dish, double price, int cod){
        this.dish = dish;
        this.cod = cod;
        this.price = price;
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
        return cod;
    }
}
