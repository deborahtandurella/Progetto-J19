package application.RestaurantException;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException() {
        super("Restaurant not found");
    }
    
}
