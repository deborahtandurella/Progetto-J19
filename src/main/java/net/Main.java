package net;

public class Main {
    public static void main(String[] args) throws Exception {
        new CliqueServer(8282, new RythmCliqueServlet())
                .withRythm()
                .start();
    }
}
