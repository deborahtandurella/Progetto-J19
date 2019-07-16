package net.request_handler;

import application.CritiqueSections;
import application.controller.Home;
import application.MenuEntry;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class (concreteStrategy)
 */
public class CritiqueRequest extends AbstractRequestStrategy {
    private static CritiqueRequest instance = null;

    private CritiqueRequest(){
    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(CritiqueRequest)
     */
    public static CritiqueRequest getInstance(){
        if(instance == null)
            instance = new CritiqueRequest();
        return instance;
    }
    @Override
    public void doGet(HttpServletResponse resp) {
    }

    /**
     * Create the critique written by the critic and addresses the critic in his home.
     *
     * @param req, the HttpServletRequest to get parameter
     * @param resp, the HttpServletResponse to answer to the requests of the templates
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            writeCritique(req);
            Map<String, Object> param = new HashMap<>();
            param.put("username", req.getParameter("username"));
            write(resp, Rythm.render("homeCritico.html", param));
        }catch (SQLException e){
            e.printStackTrace();
            SQLExcwptionHandler(resp);
        }
    }

    /**
     * Create the critique taking the information written by the critic
     *
     * @param req, the HttpServletRequest to get parameter
     * @throws SQLException
     */
    private void writeCritique(HttpServletRequest req)throws SQLException {

        String restCode = req.getParameter("restCode");

        Home.getInstance().writeCritique(restCode, getVoti(req), this.voteDishes(req,restCode),
                req.getParameter("comment"), req.getParameter("username"));
    }

    /**
     * Method which takes the vote of the dishes assigned by the critic and writes them in the critique
     *
     * @param restCode
     * @return
     * @throws SQLException
     */
    private HashMap<MenuEntry,Double> voteDishes(HttpServletRequest req,String restCode)throws SQLException{
        ArrayList<String> menu = Home.getInstance().getMenuCode(restCode);
        HashMap<MenuEntry, Double> dishVote = new HashMap<>();
        for (String i :menu) {
            dishVote.put(Home.getInstance().getDish(restCode, i),
                    Double.parseDouble(req.getParameter(i)));
        }
        return dishVote;
    }

    /**
     * Method which takes the vote of the critique sections by the critic
     *
     * @return
     */
    private double [] getVoti(HttpServletRequest req){
        double [] voti = new double[4];
        CritiqueSections [] nomeVoti = CritiqueSections.values();
        for(int i = 0; i < voti.length; i++){
            voti[i] = Double.parseDouble(req.getParameter(nomeVoti[i].toString()));
        }
        return voti;
    }

}
