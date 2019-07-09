package application.restaurant_exception;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException() {
        super("Restaurant not found");
    }

    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
