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
                /******DEBUGGING******/
                ArrayList <String> piatti = new ArrayList<>();
                piatti.add("a");
                piatti.add("b");
                piatti.add("c");
                /*********************/
                write(response, Rythm.render("critique.html", piatti));
                break;
            case "/home_critico":
                write(response, Rythm.render(("home_critico.html")));
                break;
            case "/home_utente":
                write(response, Rythm.render(("home_utente.html")));
                break;
            case "/list":
                Map<Integer,String> tmp = RestaurantCatalogue.getInstance().getRestaurantInfo();
                List<String> tmp1 = new ArrayList<>();
                for (Map.Entry<Integer,String> a: RestaurantCatalogue.getInstance().getRestaurantInfo().entrySet()) {
                    tmp1.add(a.getValue());
                }
                write(response, Rythm.render("list.html",tmp1));
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
                int votoA = Integer.parseInt(req.getParameter("a"));
                int votoB = Integer.parseInt(req.getParameter("b"));
                int votoC = Integer.parseInt(req.getParameter("c"));
                System.out.println(votoServizio);
                System.out.println(votoConto);
                System.out.println(votoLocation);
                System.out.println(votoA);
                System.out.println(votoB);
                System.out.println(votoC);
                break;

            case "/list":
                String rest = req.getParameter("restaurant");
                System.out.println(rest);
                break;
        }
    }
}
