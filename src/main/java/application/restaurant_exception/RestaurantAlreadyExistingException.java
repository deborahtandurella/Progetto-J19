package application.restaurant_exception;

public class RestaurantAlreadyExistingException extends RuntimeException {
    public RestaurantAlreadyExistingException(String message) {
        super(message);
    }
}
