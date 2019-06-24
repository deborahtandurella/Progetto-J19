package application.restaurant_exception;

public class DishAlreadyInMenuException extends RuntimeException {
    public DishAlreadyInMenuException(String message) {
        super(message);
    }
}
