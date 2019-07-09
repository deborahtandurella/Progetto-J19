package application.restaurant_exception;

public class EmptyMenuException extends RuntimeException {
    public EmptyMenuException() {
    }

    public EmptyMenuException(String message) {
        super(message);
    }
}
