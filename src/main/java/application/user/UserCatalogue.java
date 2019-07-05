package application.user;

import application.database_exception.InvalidUsernameException;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Singleton class
 * A catalogue of the users who are registered in the system
 */
public class UserCatalogue {

    private static UserCatalogue instance = null;
    private ArrayList<User> restaurantOwner;
    private ArrayList<User> critic;

    /**
     * Create a new UserCatalogue
     * initialize restaurantOwner, List of the owners of restaurants registered
     * initialize critic, List of the critics registered
     */
    private UserCatalogue(){
        this.restaurantOwner = new ArrayList<>();
        this.critic = new ArrayList<>();
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
     * in the restaurant owners' List
     *
     * @param fileName !!waiting for the future database connection to take the data!!
     * @throws IOException
     */
    private void setUprestaurantOwner(String fileName)throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = bf.readLine()) != null){
            String [] token = line.split("&");
            User owner = new User(token);
            restaurantOwner.add(owner);
        }
        bf.close();
    }

    /**
     * Method used to upload the critics, who are registered in the system, in the critic's List
     *
     * @param fileName !!waiting for the future database connection to take the data!!
     * @throws IOException
     */
    private void setUpCritic(String fileName) throws IOException{
        BufferedReader bf = new BufferedReader(new FileReader(fileName));
        String line = null;
        while ((line = bf.readLine()) != null){
            String [] token = line.split("&");
            User crit = new User(token);
            critic.add(crit);
        }
        bf.close();
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
        boolean cache = false;
        boolean DB = false;
        for(User u : this.restaurantOwner) {
            if (u.getUsername().equals(username)) {
                if (u.getPassword().equals(psw))
                    cache = true;
            }
        }

        // chiamo metodo pesristence facade che verifica lo user

        if(cache || DB)
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
    private void userSignUp(String [] infoUser, ArrayList<User> catalogue){
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
