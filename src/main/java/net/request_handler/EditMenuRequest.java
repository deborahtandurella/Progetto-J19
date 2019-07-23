package net.request_handler;

import application.controller.Home;
import application.restaurant_exception.EmptyMenuException;
import net.net_exception.MissingFormParameterException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class (concreteStrategy)
 */
public class EditMenuRequest extends AbstractEditMenu {
    private static EditMenuRequest instance = null ;

    private EditMenuRequest() {
    }

    public static EditMenuRequest getInstance(){
        if(instance == null)
            instance = new EditMenuRequest();
        return instance;
    }

    @Override
    public void doGet(HttpServletResponse resp) throws IOException {

    }

    /**
     * Addresses the user to the page required.
     *
     * @param req, the HttpServletRequest to get parameter
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
       try{
           String restCode = req.getParameter("restaurant");
           String action = req.getParameter("action");
           String username = req.getParameter("username");
           if(action.equals("add"))
               sendMenuAddTmpl(username,restCode,resp);
           else if(action.equals("elimina")){
               removeDish(resp,req);
               sendEditMenuTmpl(restCode,username,resp);
           }
           else
               sendHome(username,resp);

       }catch (SQLException e){
            e.printStackTrace();
            write(resp,Rythm.render("warn.html","siamo offline,ci scusiamo per il disagio"));
       }
       catch (MissingFormParameterException e){
           write(resp,Rythm.render("warn.html",e.getMessage()));
       }catch (EmptyMenuException e){
           write(resp,Rythm.render("warn.html","aaa"));
       }

    }

    /**
     * Method called when the restaurant owner is modifying the menu and wants remove a dish.
     * @param resp
     * @param req
     * @throws SQLException
     */
    private void removeDish(HttpServletResponse resp, HttpServletRequest req) throws SQLException {
        String dishCode = req.getParameter("dishCode");
        if(dishCode == null){
            throw new MissingFormParameterException("Selezionare un piatto da eliminare");
        }else {
            Home.getInstance().removeDish(req.getParameter("dishCode"),
                    req.getParameter("restaurant"));
        }
    }

    /**
     * Method used to address the user to the page to modify the menu of his restaurant
     *
     * @param restaurantCode
     * @param username
     * @param resp
     * @throws SQLException
     * @throws IOException
     */
    private void sendEditMenuTmpl(String restaurantCode, String username, HttpServletResponse resp)
            throws SQLException, IOException {
        Map<String,Object> conf = new HashMap<>();
        conf.put("piatti",Home.getInstance().getMenuInfo(restaurantCode));
        conf.put("restCode",restaurantCode);
        conf.put("username",username);
        conf.put("name",Home.getInstance().getRestaurantName(restaurantCode));
        write(resp, Rythm.render("editMenu.html",conf));
    }

    /**
     * Method used to address the restaurant owner to his personal home when he concluded his actions
     * @param username
     * @param resp
     * @throws IOException
     */
    private void sendHome(String username,HttpServletResponse resp) throws IOException {
        Map<String, Object> conf = new HashMap<>();
        conf.put("myRest", Home.getInstance().getOwnedRestaurant(username));
        conf.put("exception","false");
        conf.put("username", username);
        write(resp, Rythm.render("homeRistoratore.html", conf));
    }


}
