package net.request_handler;

import application.controller.Home;
import application.restaurant_exception.NoCritiquesException;
import org.rythmengine.Rythm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HomeCriticoRequest extends HomeUserRequest {

    private static HomeCriticoRequest instance = null;

    protected HomeCriticoRequest(){
    }

    public static HomeCriticoRequest getInstance(){
        if(instance == null)
            instance = new HomeCriticoRequest();
        return instance;
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tmp = req.getParameter("switch");
        if(!tmp.equals("viewCrit"))
            super.postList(req,resp,tmp);
        else
            postCritiquesView(req, resp);
    }


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
        }
    }
}
