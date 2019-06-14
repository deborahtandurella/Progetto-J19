package net.request_handler;

import application.HomeCritic;
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
        double [] voti = new double[4];
        String [] nomeVoti = {"votoMenu", "votoLocation", "votoServizio", "votoConto" };
        // TODO
        // change the parameters' name in the template in order to use here CritiqueSections.values()
        // or set the string of nomeVoti as attributes

        String comment = req.getParameter("comment");
        for(int i = 0; i < voti.length; i++){
            voti[i] = Double.parseDouble(req.getParameter(nomeVoti[i]));
        }
        int restCode = Integer.parseInt(req.getParameter("restCode"));

        ArrayList<Integer> menu = RestaurantCatalogue.getInstance().getMenuCode(restCode);
        HashMap<MenuEntry, Double> dishVote = new HashMap<>();
        for (Integer i :menu) {
            dishVote.put(RestaurantCatalogue.getInstance().getDish(restCode, i), Double.parseDouble(req.getParameter(Integer.toString(i))));
        }
        HomeCritic.getInstance().writeCritique(restCode, voti, dishVote, comment);

    }
}
