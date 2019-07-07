package persistence;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException() {
        super("Oggetto richiesto non trovato!");
    }
}
