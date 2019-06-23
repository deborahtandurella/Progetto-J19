package net.net_exception;

public class MissingFormParameterException extends RuntimeException {

    public MissingFormParameterException(String message) {
        super(message);
    }
}
