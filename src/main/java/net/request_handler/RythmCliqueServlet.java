package net.request_handler;

import net.net_exception.InvalidURIException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class RythmCliqueServlet extends HttpServlet {
    private String [] rightRequest = {"/home", "/homeCritico", "/list", "/critique","/homeRistoratore"};



    private RequestStrategy setRequestStrategy(String URI) throws InvalidURIException {
        RequestStrategy rs = null;

        try {
            isURIValid(URI);
            String tmp = parseURI(URI);
            rs = (RequestStrategy)Class.forName(tmp).getMethod("getInstance").invoke(null);
        } catch (ClassNotFoundException |NoSuchMethodException |InvocationTargetException
                | IllegalAccessException e) {
            e.printStackTrace();
        }

        return rs;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response ) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        String tmp = request.getRequestURI();

        try {
            setRequestStrategy(tmp).doGet(response);
        }catch (InvalidURIException e){

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);

        try {
            setRequestStrategy(request.getRequestURI()).doPost(request, response);
        }catch (InvalidURIException e){

        }
    }


    private void isURIValid(String URI){
        ArrayList<String> requestList =  ArrayToList(this.rightRequest);
        if(!(requestList.contains(URI)))
            throw new InvalidURIException();
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
