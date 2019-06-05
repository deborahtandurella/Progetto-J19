package net;


import application.HomeRestaurantOwner;
import application.RestaurantCatalogue;

public class Main {
    public static void main(String[] args) throws Exception {
        HomeRestaurantOwner ro = new HomeRestaurantOwner("ristoranti.txt");
        ro.addRestaurant();ro.addRestaurant();
        //System.out.println(RestaurantCatalogue.getInstance());
        new CliqueServer(8282, new RythmCliqueServlet())
                .withRythm()
                .start();

    }
}
