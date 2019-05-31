package application;

public class MenuEntry {

    private final String dish;
    private final double price;
    
    public MenuEntry( String dish, double price){
        this.dish = dish;

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


    
    
}
