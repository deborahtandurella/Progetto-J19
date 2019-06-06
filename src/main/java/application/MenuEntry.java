package application;

public class MenuEntry {

    private final String dish;
    private final double price;
    private int cod;

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
