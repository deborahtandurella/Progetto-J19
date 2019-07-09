package application.user;

import persistence.InvalidUsernameException;
import persistence.PersistenceFacade;

import java.sql.SQLException;

/**
 * Singleton class
 * A catalogue of the users who are registered in the system
 */
public class UserCatalogue {

    private static UserCatalogue instance = null;

    private UserCatalogue(){
    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(UserCatalogue)
     */
    public static synchronized UserCatalogue getInstance(){
        if(instance == null)
            instance = new UserCatalogue();
        return instance;
    }


    /**
     * Method used in class 'HomeUser'to verify the correct access of an user.
     * It controls if the username and password match
     *
     * @param username of the user
     * @param psw of the user
     * @return true if 'username' and 'password' match, false if not
     */
    public UserType logInUser(String username, String psw) throws SQLException {
        User userLog = PersistenceFacade.getInstance().getUser(username);
        if (!(userLog.getPassword().equals(psw)))
            throw new InvalidUsernameException("Password errata");
        return userLog.getType();
    }

    /**
     * Method used to sign up a new user
     * a new user of the application and register his data.
     *
     * @param infoUser, an array which contains  username, password, name and surname of the user, in this order.
     * @param  type, the enum which represents the type of user('critic' or 'restaurantOwner')
     */
    public boolean userSignUp(String [] infoUser,UserType type) throws SQLException{
        try{
            User userLog = PersistenceFacade.getInstance().getUser(infoUser[0]);
            return false;
        }
        catch (InvalidUsernameException e){
            User user = new User(infoUser, type);
            PersistenceFacade.getInstance().signUpNewUser(user);
            return true;
        }
    }

}
