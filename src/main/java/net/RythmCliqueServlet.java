package net;

import application.HomeCritic;
import application.Restaurant;
import application.RestaurantCatalogue;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


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
                write(response, Rythm.render(("critique.html")));
                break;
            case "/home_critico":
                write(response, Rythm.render(("home_critico.html")));
                break;
            case "/home_utente":
                write(response, Rythm.render(("home_utente.html")));
                break;
            case "/list":
                /******DEBUGGING******/
                //RestaurantCatalogue.getInstance().addRestaurant("a", "b");
//                Restaurant a = new Restaurant("a", "a", 1);
//                Restaurant b = new Restaurant("b", "b", 2);
//                ArrayList<Restaurant>rest = new ArrayList<>();
                ArrayList<String> rest = new ArrayList<>();
                rest.add("a");
                rest.add("b");
                /*********************/
                write(response, Rythm.render("list.html", /*RestaurantCatalogue.getInstance().returnList()*/rest));
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
                break;
        }
    }
}
