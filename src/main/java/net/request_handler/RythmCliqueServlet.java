package net.request_handler;

import application.RestaurantException.EmptyMenuException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class RythmCliqueServlet extends HttpServlet {
    private String [] rightRequest = {"/home", "/homeCritico", "/list", "/critique"};



    private RequestStrategy setRequestStrategy(String URI) {
        RequestStrategy rs = null;
        if(!isURIValid(URI)){
            throw new EmptyMenuException();
        }
        String tmp = parseURI(URI);
        try {
            rs = (RequestStrategy)Class.forName(tmp).getMethod("getInstance").invoke(null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return rs;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        String tmp = request.getRequestURI();
        System.out.println(tmp);
        setRequestStrategy(tmp).doGet(response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        setRequestStrategy(request.getRequestURI()).doPost(request, response);
    }


    private boolean isURIValid(String URI){
        ArrayList<String> requestList =  ArrayToList(this.rightRequest);
        return requestList.contains(URI);
    }


    private ArrayList<String> ArrayToList(String [] s){
        ArrayList<String> list = new ArrayList<>();
        for(String i: s)
            list.add(i);
        return list;
    }


    private String parseURI(String uri){
        String path = "net.request_handler.";
        String uri1 = String.valueOf(uri.toCharArray(), 1,1).toUpperCase();
        String uri2 = String.valueOf(uri.toCharArray(), 2,uri.length()-2);
        return path+uri1+uri2+"Request";
    }
}
