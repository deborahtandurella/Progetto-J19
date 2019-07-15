package net.request_handler;

import application.controller.Home;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Abstract class
 * It contains method to modify a menu, used by the class 'AddMenuRequest' and 'AddRestaurantRequest',
 * which both extend the class.
 */
public abstract class AbstractEditMenu extends AbstractRequestStrategy{

    /**
     * Sends the view to modify the menu of the restaurant
     *
     * @param username of the user
     * @param restaurantCode code oof the restaurant
     * @param resp
     * @throws SQLException
     * @throws IOException
     */
    protected void sendMenuAddTmpl(String username, String restaurantCode , HttpServletResponse resp)
            throws SQLException, IOException {
        HashMap<String,Object> conf = new HashMap<>();
        conf.put("username", username);
        conf.put("rCode",restaurantCode);
        conf.put("rName", Home.getInstance().getRestaurantName(restaurantCode));
        write(resp,Rythm.render("addMenu.html",conf));
    }
}
