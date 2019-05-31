package net;

import application.HomeCritic;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


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
