package net;

import application.*;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


public class RythmCliqueServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);


        switch (request.getRequestURI()){
            case "/home":
                write(response, Rythm.render(("home.html")));
                break;
            case "/restaurant_view":
                write(response, Rythm.render(("restaurant_viewPROVA.html")));
                break;
            case "/critique":
                break;
            case "/sign_up":
                write(response, Rythm.render(("sign_up.html")));
                break;
            case "/sign_up_done":
                write(response, Rythm.render(("sign_up_done.html")));
                break;
            case "/home_critico":
                write(response, Rythm.render(("home_critico.html")));
                break;
            case "/home_ristoratore":
                write(response, Rythm.render(("home_ristoratore.html")));
                break;
            case "/home_utente":
                write(response, Rythm.render(("home_utente.html")));
                break;
            case "/add_restaurant":
                write(response, Rythm.render(("add_restaurant.html")));
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
                if (HomeCritic.getInstance().logIn(username, password)) {
                    write(resp, Rythm.render("home_critico.html"));
                } else
                    write(resp, Rythm.render("warn.html"));
                break;
            case "/home_critico":
                String tmp = req.getParameter("switch");
                System.out.println(tmp);
                Map<Integer, String> rest = RestaurantCatalogue.getInstance().getRestaurantInfo();
                Map<String, Object> param = new HashMap<>();
                param.put("rest",rest);
                param.put("sw",tmp);
                write(resp, Rythm.render("list.html", param));
                break;
            case "/critique":
                writeCritique(req);
                break;

            case "/list":
                int restCode = Integer.parseInt(req.getParameter("restaurant"));
                String action = req.getParameter("switch");
                Map<String, Object> conf = new HashMap<>();
                if (action.equals("write")) {
                    SortedMap<Integer, String> piatti = RestaurantCatalogue.getInstance().getMenuInfo(restCode);
                    conf.put("piatti", piatti);
                    conf.put("restCode", restCode);
                    write(resp, Rythm.render("critique.html", conf));

                }
                else {
                    write(resp, Rythm.render("restaurant_viewPROVA.html", conf));
                }
                break;
        }
    }

    private void writeCritique(HttpServletRequest req){
        double [] voti = new double[4];
        String [] nomeVoti = {"votoMenu", "votoLocation", "votoServizio", "votoConto" };
        for(int i = 0; i < voti.length; i++){
            voti[i] = Double.parseDouble(req.getParameter(nomeVoti[i]));
        }
        int restCode = Integer.parseInt(req.getParameter("restCode"));

        ArrayList<Integer> menu = RestaurantCatalogue.getInstance().getMenuCode(restCode);
        HashMap<MenuEntry, Double> dv = new HashMap<>();
        for (Integer i :menu) {
            dv.put(RestaurantCatalogue.getInstance().getDish(restCode, i), Double.parseDouble(req.getParameter(Integer.toString(i))));
        }
        HomeCritic.getInstance().writeCritique(restCode, voti, dv);
        RestaurantCatalogue.getInstance().printRestaurantOverview(restCode);
    }
}
