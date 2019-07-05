package application.controller;

public interface Home {
    void signUp(String [] credential);
    boolean  logIn(String username, String password);
}
