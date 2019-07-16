package net;


import application.*;
import application.controller.*;
import net.request_handler.RythmCliqueServlet;
import persistence.PersistenceFacade;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        int portNumber = 8282;
        if(args.length !=0)
            portNumber = Integer.parseInt(args[0]);
        new CliqueServer(portNumber, new RythmCliqueServlet())
                .withRythm()
                .start();
        PersistenceFacade.getInstance();

    }

}
