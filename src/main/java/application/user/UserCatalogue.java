package application.user;

import application.database_exception.InvalidUsernameException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class
 * A catalogue of the users who are registered in the system
 */
public class UserCatalogue {

    private static UserCatalogue instance = null;
    private HashMap<String, User> restaurantOwner;
    private HashMap<String, User> critic;

    /**
     * Create a new UserCatalogue
     * initialize restaurantOwner, HashMap of the owners of restaurants registered ('key':= username, 'value':= User)
     * initialize critic, HashMap of the critics registered ('key':= username, 'value':= User)
     */
    private UserCatalogue(){
        this.restaurantOwner = new HashMap<>();
        this.critic = new HashMap<>();
        try {
            setUpCritic("critici.txt");
            setUprestaurantOwner("ristoratori.txt");
        }catch (IOException e){
            e.printStackTrace();
        }
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
     * Method used to upload the restaurant owners, who are registered in the system,
     * in the restaurant owners' HashMap
     *
     * @param fileName !!waiting for the future database connection to take the data!!
     * @throws IOException
     */
    private void setUprestaurantOwner(String fileName)throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = bf.readLine()) != null){
            String [] token = line.split("&");
            User owner = new User(token[0], token[1], token[2], token[3]);
            restaurantOwner.put(token[0], owner);
        }
        bf.close();
    }

    /**
     * Method used to upload the critics, who are registered in the system, in the critic's HashMap
     *
     * @param fileName !!waiting for the future database connection to take the data!!
     * @throws IOException
     */
    private void setUpCritic(String fileName) throws IOException{
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = bf.readLine()) != null){
            String [] token = line.split("&");
            User crit = new User(token[0], token[1], token[2], token[3]);
            critic.put(token[0], crit);
        }
        bf.close();
    }

    /**
     * Method used in class 'HomeCritic' to verify the correct access of a critic.
     * It controls if the username and password match
     *
     * @param username of the user
     * @param psw of the user
     * @return true if 'username' and 'password' match, false if not
     */
    public synchronized boolean logInCritic(String username,String psw){
        if(critic.containsKey(username) && critic.get(username).getPassword().equals(psw))
            return true;
        return false;
    }

    /**
     * Method used in class 'HomeRestaurantOwner'to verify the correct access og a restaurant owner.
     * It controls if the username and password match
     *
     * @param username of the user
     * @param psw of the user
     * @return true if 'username' and 'password' match, false if not
     */
    public synchronized boolean logInRestaurantOwner(String username, String psw){
        if(restaurantOwner.containsKey(username) && restaurantOwner.get(username).getPassword().equals(psw))
            return true;
        return false;
    }

    /**
     * Method used in this class by 'restaurantOwnerSignUp' and 'criticSignUp' to sign up
     * a new user of the application and register his data.
     *
     * @param infoUser, an array which contains  username, password, name and surname of the user, in this order.
     * @param catalogue the HashMap where the user's data will be saved
     */
    private void userSignUp(String [] infoUser, HashMap<String,User> catalogue){
        if(catalogue.containsKey(infoUser[0]))
            throw new InvalidUsernameException("Username already taken!");
        User crit = new User(infoUser[0], infoUser[1], infoUser[2], infoUser[3]);
        catalogue.put(infoUser[0], crit);
        updateDBUser(catalogue,"critici.txt");
    }

    /**
     * Method called in class 'HomeRestaurantOwner' to sign up a new restaurant owner in the system
     *
     * @param username of the new restaurant owner
     * @param password of the new restaurant owner
     * @param name of the new restaurant owner
     * @param surname of the new restaurant owner
     */
    public void restaurantOwnerSignUp(String username, String password, String name, String surname){
        String [] info = {username, password, name, surname};
        userSignUp(info, this.restaurantOwner);
    }

    /**
     * Method called in class 'HomeCritic' to sign up a new critic in the system
     *
     * @param username of the new critic
     * @param password of the new critic
     * @param name of the new critic
     * @param surname of the new critic
     */
    public void criticSignUp(String username, String password, String name, String surname){
        String [] info = {username, password, name, surname};
        userSignUp(info, this.critic);
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
