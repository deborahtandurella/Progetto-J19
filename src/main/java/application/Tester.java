package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class Tester {
    public static void main(String[] args) throws FileNotFoundException {
        String rest = "ristoranti.txt";
        String menu1 = "menu_korte_dei_sapori.txt";
//        String menu2 = "M.txt";
//        String menu3 = "M_torre_degli_aquila.txt";
        
        HomeRestaurantOwner hro = new HomeRestaurantOwner(rest);
        try {
            hro.addRestaurant();
            hro.addRestaurant(); 
            hro.addRestaurant();
            /*hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();
            hro.addRestaurant();*/
        } catch (IOException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
        
        try {
            hro.addMenu(2, menu1);
            //hro.addMenu(5, menu2);
            //hro.addMenu(6, menu3);
        } catch (IOException e) {
            System.err.println(Arrays.toString(e.getStackTrace()));
        }
        
        
//        System.out.println(hro.findRestaurant(1).toString());
//        System.out.println("-----");
//        RestaurantCatalogue.printList();
//        System.out.println("-----");
//        RestaurantCatalogue.printMenu(2);     // per stampare menu che funzia
//        System.out.println("-----");
//        RestaurantCatalogue.printMenu(5);
//        System.out.println("-----");
//        RestaurantCatalogue.printMenu(6);


        HomeCritic hc = new HomeCritic();
        HashMap<MenuEntry, Double> prova = new HashMap<>();
        MenuEntry p1 = new MenuEntry("Spaghettone con limone, menta e peperoncino",	11, 0);
        MenuEntry p2 = new MenuEntry("Cannellone di branzino su fonduta di spinaci",	13, 0);
        prova.put(p1,7.);
        prova.put(p2,6.);
        hc.writeCritique(2,6,8,7,9,prova);

        System.out.println(hc.findRestaurant(2));
    }
}
