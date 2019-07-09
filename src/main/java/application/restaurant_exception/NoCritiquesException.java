package application.restaurant_exception;

public class NoCritiquesException extends RuntimeException {
    public NoCritiquesException() {
    }

    public NoCritiquesException(String message) {
        super(message);
    }
}
