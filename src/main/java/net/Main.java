package net;


import application.*;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        Database.getInstance().addMenu(1,"M_Korte_Dei_Sapori.txt");
        Database.getInstance().addMenu(4,"M_torre_degli_aquila.txt");
        Database.getInstance().addMenu(2,"M_locanda_del_carmine.txt");
        init();
//        Database.getInstance().addMenu(7,"Bardelli_&.txt");
//        RestaurantCatalogue.getInstance().printMenu(2);
//        RestaurantCatalogue.getInstance().printMenu(5);
//        RestaurantCatalogue.getInstance().printMenu(6);
        new CliqueServer(8282, new RythmCliqueServlet())
                .withRythm()
                .start();

    }
    /**
     * serve per inizializzare i ristoranti con una critica di default
     **/

    private static void init(){
        double [] voti = {6,6,6,6};
        HashMap<MenuEntry, Double> piatti = new HashMap<>();
        MenuEntry cibo = new MenuEntry("cibo",0.0,0);
        piatti.put(cibo,4.0);
        String comment = "prova commento della critica del ristorante";
        HomeCritic.getInstance().writeCritique(1,voti,piatti,comment);
        HomeCritic.getInstance().writeCritique(1,voti,piatti,comment);
        HomeCritic.getInstance().writeCritique(1,voti,piatti,comment);
        HomeCritic.getInstance().writeCritique(1,voti,piatti,comment);
        HomeCritic.getInstance().writeCritique(1,voti,piatti,comment);
        HomeCritic.getInstance().writeCritique(2,voti,piatti,comment);
        HomeCritic.getInstance().writeCritique(4,voti,piatti,comment);
    }
}
