package net.request_handler;

import application.RestaurantCatalogue;
import application.restaurant_exception.DishAlreadyInMenuException;
import net.net_exception.InvalidParameterException;
import net.net_exception.MissingFormParameterException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AddMenuRequest extends AbstractRequestStrategy {
    private static AddMenuRequest instance = null;

    private AddMenuRequest() {
    }
    public static AddMenuRequest getInstance(){
        if(instance == null)
            instance = new AddMenuRequest();
        return instance;
    }

    @Override
    public void doGet(HttpServletResponse resp) throws IOException {

    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            addMenuEntryToRestaurant(req);
            answerRequest(req,resp);
        }catch (MissingFormParameterException | DishAlreadyInMenuException e){
            write(resp, Rythm.render("warn.html",e.getMessage()));
        }

    }
    private void checkParam(String param){
        if(param == null)
            throw new MissingFormParameterException("Scegliere un tipo per il piatto che si vuole inserire");
    }
    private void checkPrice(double price){
        if(price < 0)
            throw new InvalidParameterException("Inserisci un prezzo positivo");
    }

    private void addMenuEntryToRestaurant(HttpServletRequest req)throws MissingFormParameterException,
            InvalidParameterException{
        String dishType = req.getParameter("dishType");
        checkParam(dishType);
        String restaurantCode = req.getParameter("restaurantCode");
        String dishPrice = req.getParameter("dishPrice");
        checkPrice(Double.parseDouble(dishPrice));
        String dishName = req.getParameter("dishName");
        RestaurantCatalogue.getInstance()
                .addMenuEntry(Integer.parseInt(restaurantCode),dishType,dishName,Double.parseDouble(dishPrice));
    }

    private void answerRequest(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        if(req.getParameter("action").equals("Inserisci un altro piatto"))
            write(resp,Rythm.render("addMenu.html",req.getParameter("rCode")));
        else {
            Map<String, Object> conf = new HashMap<>();
            conf.put("myRest", RestaurantCatalogue.getInstance().myRestaurant(req.getParameter("username")));
            conf.put("username", req.getParameter("username"));
            write(resp, Rythm.render("homeRistoratore.html", conf));

        }
    }
}
