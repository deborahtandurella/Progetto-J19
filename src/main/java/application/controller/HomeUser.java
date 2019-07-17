package application.controller;

import persistence.InvalidUsernameException;
import application.UserCatalogue;
import application.UserType;

import java.sql.SQLException;

/**
 * Singleton class
 * A controller for the application
 */
public class HomeUser {
    private static HomeUser instance = null;
    public HomeUser() {
    }

    /**
     * 'Pattern Singleton Implementation'
     */
    public static synchronized  HomeUser getInstance(){
        if(instance == null){
            instance = new HomeUser();
        }
        return instance;
    }

    /**
     * Verifies the credential of the user and return the userType(critic or restaurant owner)
     * in order to address him in correct home page
     *
     * @param username
     * @param psw, password of the username
     * @return
     * @throws SQLException
     */
    public UserType logIn(String username, String psw) throws SQLException {
        return UserCatalogue.getInstance().logInUser(username, psw);
    }

    /**
     * Method called for the registration of the user in the system.
     * @param credential (username, password, name and surname)
     * @param type critic or restaurant owner
     * @return
     * @throws InvalidUsernameException
     * @throws SQLException
     */
    public boolean signUp(String [] credential, UserType type)throws InvalidUsernameException, SQLException {
        return UserCatalogue.getInstance().userSignUp(credential,type);
    }

    /**
     *
     * @param username
     * @return the user type of the user selected
     * @throws SQLException
     */
    public UserType getUserType(String username) throws SQLException{
        return UserCatalogue.getInstance().getUserType(username);
    }
}
