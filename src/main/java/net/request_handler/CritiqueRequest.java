package net.request_handler;

import application.CritiqueSections;
import application.controller.HomeCritic;
import application.MenuEntry;
import application.RestaurantCatalogue;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CritiqueRequest extends AbstractRequestStrategy {

    private static CritiqueRequest instance = null;

    private CritiqueRequest(){

    }

    public static CritiqueRequest getInstance(){
        if(instance == null)
            instance = new CritiqueRequest();
        return instance;
    }
    @Override
    public void doGet(HttpServletResponse resp) {
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        writeCritique(req);
        write(resp, Rythm.render("homeCritico.html"));
    }


    private void writeCritique(HttpServletRequest req){

        int restCode = Integer.parseInt(req.getParameter("restCode"));

        HomeCritic.getInstance().writeCritique(restCode, getVoti(req), this.voteDishes(req,restCode),
                req.getParameter("comment"), req.getParameter("username"));

    }
    private HashMap<MenuEntry,Double> voteDishes(HttpServletRequest req,int restCode){
        ArrayList<Integer> menu = HomeCritic.getInstance().getMenuCode(restCode);
        HashMap<MenuEntry, Double> dishVote = new HashMap<>();
        for (Integer i :menu) {
            dishVote.put(HomeCritic.getInstance().getDish(restCode, i),
                    Double.parseDouble(req.getParameter(Integer.toString(i))));
        }
        return dishVote;
    }

    private double [] getVoti(HttpServletRequest req){
        double [] voti = new double[4];
        CritiqueSections [] nomeVoti = CritiqueSections.values();
        for(int i = 0; i < voti.length; i++){
            voti[i] = Double.parseDouble(req.getParameter(nomeVoti[i].toString()));
        }
        return voti;
    }

}
