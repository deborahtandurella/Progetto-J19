package net.request_handler;

import application.controller.Home;
import application.restaurant_exception.NoCritiquesException;
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
public class HomeCriticoRequest extends HomeUserRequest {

    private static HomeCriticoRequest instance = null;

    protected HomeCriticoRequest(){
    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(HomeCriticoRequest)
     */
    public static HomeCriticoRequest getInstance(){
        if(instance == null)
            instance = new HomeCriticoRequest();
        return instance;
    }

    /**
     * Addresses the critic in the correct page that he had required
     *
     * @param req, the HttpServletRequest to get parameter
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tmp = req.getParameter("switch");
        if(!tmp.equals("viewCrit"))
            super.postList(req,resp,tmp);
        else
            postCritiquesView(req, resp);
    }

    /**
     * Prepares the page to view the own critiques
     *
     * @throws IOException
     */
    private void postCritiquesView(HttpServletRequest req, HttpServletResponse resp)throws IOException{
        try {
            String tmp = req.getParameter("username");
            Map<String, Object> conf =new HashMap<>();
            conf.put("critique", Home.getInstance().myCritique(tmp));
            conf.put("username", tmp);
            write(resp,Rythm.render("myCritiques.html", conf));
        }
        catch (NoCritiquesException e){
            write(resp, Rythm.render("warn.html", e.getMessage()));
        }catch (SQLException e){
            e.printStackTrace();
            SQLExcwptionHandler(resp);
        }
    }
}
