package net;


import application.Database;
import application.RestaurantCatalogue;

public class Main {
    public static void main(String[] args) throws Exception {
        Database.getInstance().addMenu(2,"menu_korte_dei_sapori.txt");
        //Database.getInstance().addMenu(6,"M_torre_degli_aquila.txt");
        //Database.getInstance().addMenu(5,"M_locanda_del_carmine.txt");
        RestaurantCatalogue.getInstance().printMenu(2);
        //RestaurantCatalogue.getInstance().printMenu(5);
        //RestaurantCatalogue.getInstance().printMenu(6);
        new CliqueServer(8282, new RythmCliqueServlet())
                .withRythm()
                .start();

    }
}
