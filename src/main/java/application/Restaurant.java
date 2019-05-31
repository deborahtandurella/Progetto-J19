package application;

import java.util.ArrayList;
import java.util.HashMap;


public class Restaurant {
    private final String name;
    private final String address;
    private final int code;
    private HashMap<DishType,ArrayList<MenuEntry>> menu ;
    private ArrayList<Critique> critiques;
    private Critique overviewCritique;

    public Restaurant(String name, String address, int code){
        this.name = name;
        this.address = address;
        this.code = code;
        this.menu = null;
        this.critiques = new ArrayList<>();
        this.overviewCritique = null;
    }


    public void addEntry(HashMap<DishType,ArrayList<MenuEntry>> a){
        menu = a;
    }

    @Override
    public String toString() {
        return this.name + "; " + this.address + this.overviewCritique.toString();
    }
    
    public void printMenu(){
        System.out.println("++++" + this.name + "++++");
        DishType  [] type = {DishType.ANTIPASTI,DishType.PRIMI, DishType.SECONDI, DishType.DOLCI} ;

        for (int i=0 ; i < type.length ; i++) {
            System.out.println("\n"+type[i].toString());
            for (MenuEntry tmp2: menu.get(type[i])) {
                System.out.println(tmp2.toString());
            }
        }
    }

    public void addCritique(Critique crit){

        this.critiques.add(crit);
        this.overviewCritique = Critique.computeMean(this.critiques);
    }


}
