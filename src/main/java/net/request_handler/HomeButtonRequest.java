package net.request_handler;

import application.UserType;
import application.controller.HomeUser;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Singleton class (concreteStrategy)
 */
public class HomeButtonRequest extends AddressHomeRequest{
    private static HomeButtonRequest instance = null;

    private HomeButtonRequest() {
    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(HomeButtonRequest)
     */
    public static HomeButtonRequest getInstance(){
        if(instance == null)
            instance = new HomeButtonRequest();
        return instance;
    }
    @Override
    public void doGet(HttpServletResponse resp) throws IOException {

    }

    /**
     * Addresses the user in his personal home
     *
     * @param req, the HttpServletRequest to get parameter
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        if(username == null){
            write(resp, Rythm.render(("home.html")));
        }
        try {
            if(UserType.CRITIC == HomeUser.getInstance().getUserType(username))
                super.homeCritic(resp, username);
            else {
                super.homeRestaurantOwner(resp, username);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("SQLException: " + e.getMessage());
        }
    }
}
