package net;


import application.Database;

public class Main {
    public static void main(String[] args) throws Exception {
        Database.getInstance();
        new CliqueServer(8282, new RythmCliqueServlet())
                .withRythm()
                .start();

    }
}
