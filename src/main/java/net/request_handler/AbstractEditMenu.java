package net.request_handler;

import application.controller.Home;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

public abstract class AbstractEditMenu extends AbstractRequestStrategy{

    protected void sendMenuAddTmpl(String username, String restaurantCode , HttpServletResponse resp)
            throws SQLException, IOException {
        HashMap<String,Object> conf = new HashMap<>();
        conf.put("username", username);
        conf.put("rCode",restaurantCode);
        conf.put("rName", Home.getInstance().getRestaurantName(restaurantCode));
        write(resp,Rythm.render("addMenu.html",conf));
    }
}
