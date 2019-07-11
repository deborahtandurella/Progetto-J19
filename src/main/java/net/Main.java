package net;


import application.*;
import application.controller.*;
import net.request_handler.RythmCliqueServlet;
import persistence.PersistenceFacade;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        new CliqueServer(8282, new RythmCliqueServlet())
                .withRythm()
                .start();
        PersistenceFacade.getInstance();

    }

}
