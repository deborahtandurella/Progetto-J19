package net;

public class Main {
    public static void main(String[] args) throws Exception {
        new CliqueServer(8283, new RythmCliqueServlet())
                .withRythm()
                .start();
    }
}
