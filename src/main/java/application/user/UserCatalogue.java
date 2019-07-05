package application.user;

import application.database_exception.InvalidUsernameException;
import persistence.PersistenceFacade;

import java.io.*;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;

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
            User userLOg = PersistenceFacade.getInstance().getUser(username);
            if (!(userLOg.getPassword().equals(psw)))
                throw new InvalidUsernameException("Password errata");
            return userLOg.getType();
    }

    /**
     * Method used in this class by 'restaurantOwnerSignUp' and 'criticSignUp' to sign up
     * a new user of the application and register his data.
     *
     * @param infoUser, an array which contains  username, password, name and surname of the user, in this order.
     * @param catalogue the HashMap where the user's data will be saved
     */
    private void userSignUp(String [] infoUser, HashSet<User> catalogue){
        for(User u : catalogue) {
            if (u.getUsername().equals(infoUser[0])) {
                throw new InvalidUsernameException("Username already taken!");
            }
        }
        User user = new User(infoUser);
        catalogue.add(user);
    }

    /**
     * Method called in class 'HomeRestaurantOwner' to sign up a new restaurant owner in the system
     *
     * @param credential, which contains username, password, name and surname
     */
    public void restaurantOwnerSignUp(String [] credential){
        userSignUp(credential, this.restaurantOwner);
    }

    /**
     * Method called in class 'HomeCritic' to sign up a new critic in the system
     *
     * @param credential, which contains username, password, name and surname
     */
    public void criticSignUp(String [] credential){
        userSignUp(credential, this.critic);
    }

    /**
     * Method used to update database
     *  !!waiting for the real connection with DBMS!!
     * @param info
     * @param fileName
     */
    private void updateDBUser(Map<String,User> info, String fileName){
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            for (Map.Entry<String,User> a: info.entrySet()) {
                pw.println(a.getKey()+"&"+a.getValue().getPassword()+"&"+
                        a.getValue().getName()+"&"+a.getValue().getSurname());
            }

            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
