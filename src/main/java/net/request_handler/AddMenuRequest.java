package net.request_handler;

import application.controller.Home;
import application.restaurant_exception.DishAlreadyInMenuException;
import net.net_exception.InvalidParameterException;
import net.net_exception.MissingFormParameterException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class (concreteStrategy)
 */
public class AddMenuRequest extends AbstractRequestStrategy {
    private static AddMenuRequest instance = null;

    private AddMenuRequest() {
    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(AddMenuRequest)
     */
    public static AddMenuRequest getInstance(){
        if(instance == null)
            instance = new AddMenuRequest();
        return instance;
    }

    @Override
    public void doGet(HttpServletResponse resp) throws IOException {

    }

    /**
     *
     * @param req, the HttpServletRequest to get parameter
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        try {
            addMenuEntryToRestaurant(req);
            answerRequest(req,resp);
        }catch (MissingFormParameterException | DishAlreadyInMenuException e){
            write(resp, Rythm.render("warn.html",e.getMessage()));
        }

    }

    /**
     * Check if the parameter is valid
     *
     * @param param, the type of the new MenuEntry(dish)
     */
    private void checkParam(String param){
        if(param == null)
            throw new MissingFormParameterException("Scegliere un tipo per il piatto che si vuole inserire");
    }

    /**
     * Check if the parameter price is valid
     *
     * @param price, the price of the new MenuEntry
     */
    private void checkPrice(double price){
        if(price < 0)
            throw new InvalidParameterException("Inserisci un prezzo positivo");
    }

    /**
     * This method get the parameter from the template and adds a new MenuEntry to a restaurant's menu
     *
     * @param req, the HttpServletRequest to get parameter
     * @throws MissingFormParameterException
     * @throws InvalidParameterException
     */
    private void addMenuEntryToRestaurant(HttpServletRequest req)throws MissingFormParameterException,
            InvalidParameterException{
        String dishType = req.getParameter("dishType");
        checkParam(dishType);
        String restaurantCode = req.getParameter("restaurantCode");
        String dishPrice = req.getParameter("dishPrice");
        checkPrice(Double.parseDouble(dishPrice));
        String dishName = req.getParameter("dishName");
        Home.getInstance()
                .addMenuEntry(restaurantCode,dishType,dishName,Double.parseDouble(dishPrice));
    }

    /**
     * This method answer to the request of the user after he has add a new MenuEntry.
     * It can directs the user to his home page or to the page to add another MenuEntry, using the parameter 'action'.
     *
     * @param req, the HttpServletRequest to get parameter
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    private void answerRequest(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        if(req.getParameter("action").equals("altro"))
            write(resp,Rythm.render("addMenu.html",req.getParameter("rCode")));
        else {
            Map<String, Object> conf = new HashMap<>();
            conf.put("myRest", Home.getInstance().getOwnedRestaurant(req.getParameter("username")));
            conf.put("username", req.getParameter("username"));
            conf.put("exception", "false");
            write(resp, Rythm.render("homeRistoratore.html", conf));

        }
    }
}
