package net.request_handler;

import application.controller.HomeUser;
import application.user.UserType;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Singleton class (concreteStrategy)
 */
public class SignUpRequest extends AddressHomeRequest {
    private static SignUpRequest instance = null;

    private SignUpRequest() {
    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(SignUpRequest)
     */
    public static SignUpRequest getInstance(){
        if(instance == null)
            instance = new SignUpRequest();
        return instance;
    }

    @Override
    public void doGet(HttpServletResponse resp) throws IOException {
        write(resp,Rythm.render("signUp.html"));
    }

    /**
     * method which takes the data of the new user during registration
     *
     * @param req, the HttpServletRequest to get parameter
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String [] credential = {username, password, name, surname};
        try{
            signUp(type, credential, resp);
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }

    }

    /**
     * Method called when a new user signs up
     *
     * @param type, of the new user (critic or restaurant owner)
     * @param credential, data of the new user
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     * @throws SQLException
     */
    private void signUp(String type, String [] credential, HttpServletResponse resp)throws IOException, SQLException {
        if(!(HomeUser.getInstance().signUp(credential, UserType.valueOf(type)))){
            write(resp,Rythm.render("warn.html", "Username non valido, già in uso!"));
        }
        else {
            if(type.equals("CRITIC"))
                super.homeCritic(resp, credential[0]);
            else
                super.homeRestaurantOwner(resp, credential[0]);
        }
    }

}
