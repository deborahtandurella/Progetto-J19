package application.controller;

public interface Home {
    void signUp(String username, String password, String name, String surname);
    boolean  logIn(String username, String password);
}
