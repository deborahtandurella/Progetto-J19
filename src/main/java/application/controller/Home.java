package application.controller;

public interface Home {
    void signUp(String username, String password);
    boolean  logIn(String username, String password);
}
