package net.request_handler;

import application.CritiqueSections;
import application.controller.Home;
import application.restaurant_exception.NoCritiquesException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

/**
 *Singleton class (concreteStrategy)
 *
 * Used to respond to the different options of print the restaurant view
 */
public class RestaurantViewRequest extends OverviewRequest {

    private static RestaurantViewRequest instance = null;
    private final String FILTER;
    private final String RESET;
    private final String SECTION;
    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(RestaurantViewRequest)
     */
    protected RestaurantViewRequest() {
        this.FILTER = "filter";
        this.RESET = "reset";
        this.SECTION = "mean";
    }

    public static RestaurantViewRequest getInstance(){
        if(instance == null)
            instance = new RestaurantViewRequest();
        return instance;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        super.sendRestaurantOverview(req.getParameter("restaurant"), resp, req.getParameter("username"),
                switchCritiques(req));
    }

    /**
     * Using the parameter in the form return the correct critiques required by the user
     *
     * @param req, HttpServletRequest request
     * @return  the critiques(in String format) which the user wants
     */
    private LinkedList<String> switchCritiques(HttpServletRequest req){
        if(req.getParameter("action").equals(FILTER)){
            if (req.getParameter("section").equals(SECTION)){
                return critiquesByVote(req);
            }
            else
            return critiquesByVoteSection(req);
        }
        else
            return allCritiques(req);
    }

    /**
     * Method which returns the critiques(in string format) divided for vote of a section
     *
     * @return only the critiques which verify the condition
     */
    private LinkedList<String> critiquesByVoteSection(HttpServletRequest req){
        try {
            return Home.getInstance().getRestCritByVoteSectionToString(Integer.parseInt(req.getParameter("grade")),
                    req.getParameter("restaurant"), CritiqueSections.valueOf(req.getParameter("section")));
        }
        catch (NoCritiquesException e){
            LinkedList<String> exc = new LinkedList<>();
            exc.add(e.getMessage());
            return exc;
        }
    }

    /**
     * Method which return the critiques(in string format) divided for mean
     *
     * @return only the critiques which verify the condition
     */
    private LinkedList<String> critiquesByVote(HttpServletRequest req) {
        try {
            return Home.getInstance().getRestCritByVoteToString(Integer.parseInt(req.getParameter("grade")),
                    req.getParameter("restaurant"));
        }
        catch (NoCritiquesException e){
            LinkedList<String> exc = new LinkedList<>();
            exc.add(e.getMessage());
            return exc;
        }
    }

    /**
     * Method which returns all the critiques(in string format) of the restaurant
     * 
     * @param req
     * @return
     */
    private LinkedList<String> allCritiques(HttpServletRequest req){
        try {
            return Home.getInstance().getRestaurantCritiqueToString(req.getParameter("restaurant"));
        }
        catch (NoCritiquesException e){
            LinkedList<String> exc = new LinkedList<>();
            exc.add(e.getMessage());
            return exc;
        }
    }
}
