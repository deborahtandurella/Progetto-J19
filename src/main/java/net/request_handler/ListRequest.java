package net.request_handler;

import application.controller.Home;
import application.restaurant_exception.EmptyMenuException;
import application.restaurant_exception.NoCritiquesException;
import net.net_exception.MissingFormParameterException;
import org.rythmengine.Rythm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

/**
 * Singleton class (concreteStrategy)
 */
public class ListRequest extends OverviewRequest {
    private static ListRequest instance = null;

    protected ListRequest() {

    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(ListRequest)
     */
    public static ListRequest getInstance(){
        if(instance == null)
            instance = new ListRequest();
        return instance;
    }

    /**
     * Thanks to the parameter in the form of the template calls the correct method to answer
     * to the request of the user
     *
     * @param req, the HttpServletRequest to get parameter
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try{
            String restaurantCode = req.getParameter("restaurant");
            checkNumber(restaurantCode);
            String action = req.getParameter("switch");
            String username = req.getParameter("username");
            if(action.equals("write"))
                sendCritiqueModule(restaurantCode,resp,username);
            else
                sendRestaurantOverview(restaurantCode,resp, username,
                        Home.getInstance().getRestaurantCritiqueToString(restaurantCode));
        }catch (MissingFormParameterException e){
            write(resp,Rythm.render("warn.html",e.getMessage()));
        }catch (NoCritiquesException e){
            HashMap<String,Object> conf = new HashMap<>();
            NoCritiquesExceptionhandler(req.getParameter("restaurant"),conf,resp,
                    req.getParameter("username"));
        }
    }

    /**
     * Sends the view to compile a critique to the critic
     *
     * @param restaurantCode, code of the restaurant
     * @param resp
     * @param username of the user
     * @throws IOException
     */
    private void sendCritiqueModule(String restaurantCode, HttpServletResponse resp, String username) throws IOException {
        Map<String, Object> conf = new HashMap<>();
        try{
            LinkedHashMap<String, String> piatti = Home.getInstance().getMenuInfo(restaurantCode);
            conf.put("piatti", piatti);
            conf.put("restCode", restaurantCode);
            conf.put("username", username);
            write(resp, Rythm.render("critique.html", conf));
        }catch(EmptyMenuException e){
            write(resp,Rythm.render("warn.html", e.getMessage()));
        }catch (SQLException e){
            e.printStackTrace();
            SQLExcwptionHandler(resp);
        }
    }

    private void checkNumber(String restaurantCode){
        if(restaurantCode == null)
            throw new MissingFormParameterException("Scegliere un ristorante per continuare");
    }
}
