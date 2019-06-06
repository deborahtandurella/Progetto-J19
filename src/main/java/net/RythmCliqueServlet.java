package net;

import application.DishType;
import application.HomeCritic;
import application.Restaurant;
import application.RestaurantCatalogue;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RythmCliqueServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);


        switch (request.getRequestURI()){
            case "/home":
                write(response, Rythm.render(("home.html")));
                break;
            case "/critique":
                break;
            case "/home_critico":
                write(response, Rythm.render(("home_critico.html")));
                break;
            case "/home_utente":
                write(response, Rythm.render(("home_utente.html")));
                break;
            case "/list":
                Map<Integer, String> rest = RestaurantCatalogue.getInstance().getRestaurantInfo();
                Map<String, Object> conf = new HashMap<>();
                conf.put("rest",rest);
                write(response, Rythm.render("list.html", conf));
                break;
            default:
                write(response,Rythm.render("warn.html"));
        }



    }
    private void write(HttpServletResponse resp, String message) throws IOException {
        resp.getWriter().write(message);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        switch (req.getRequestURI()) {
            case "/home":
                String username = req.getParameter("username");
                String password = req.getParameter("password");
                HomeCritic hc = new HomeCritic();
                if (hc.logIn(username, password)) {
                    write(resp, Rythm.render("home_critico.html"));
                } else
                    write(resp, Rythm.render("warn.html"));
                break;
            case "/critique":
                int votoServizio = Integer.parseInt(req.getParameter("votoServizio"));
                int votoConto = Integer.parseInt(req.getParameter("votoConto"));
                int votoLocation = Integer.parseInt(req.getParameter("votoLocation"));
                int votoMenu = Integer.parseInt(req.getParameter("votoMenu"));
                int restCode = Integer.parseInt(req.getParameter("restCode"));
                System.out.println(votoServizio);
                System.out.println(votoConto);
                System.out.println(votoMenu);
                System.out.println(votoLocation);
                System.out.println(restCode);
                break;

            case "/list":
                int rest = Integer.parseInt(req.getParameter("restaurant"));
                //System.out.println(rest);
                /*HashMap<DishType, HashMap<Integer, String>> piatti = RestaurantCatalogue.getInstance().getMenu(rest);
                String [] name = {"antipasti","primi","secondi", "dolci"};
                for(DishType e : DishType.values()){
                    for (Map.Entry<Integer, String> ma : piatti.get(e).entrySet()){

                    }
                }*/
                Map<Integer,String> piatti = RestaurantCatalogue.getInstance().getMenu(rest);
                Map<String, Object> conf = new HashMap<>();
                conf.put("piatti", piatti);
                conf.put("restCode",rest);
                write(resp,Rythm.render("critique.html", conf));
                break;
        }
    }
}
