package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        return this.name + "    " + this.address + this.overviewCritique.toString();
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

    public String getRestaurantInfo(){
        return this.name + "    " + this.address;
    }

    public int getCode() {
        return code;
    }

    public  Map<Integer, String> getMenuInfo(){
        Map<Integer,String> temp = new HashMap<>();
        for (Map.Entry<DishType,ArrayList<MenuEntry>> a: this.menu.entrySet()) {
            for (MenuEntry me:a.getValue()) {
                temp.put(me.getCod(),me.getDish());
            }
        }

        return temp;
    }

    public ArrayList<Integer> getMenuCode(){
        ArrayList<Integer> tmp = new ArrayList<>();
        for (DishType e : this.menu.keySet()) {
            for(MenuEntry me: this.menu.get(e)){
                tmp.add(me.getCod());
            }
        }
        return tmp;
    }

    public MenuEntry getDish(int cod){
        for (Map.Entry<DishType,ArrayList<MenuEntry>> a: this.menu.entrySet()) {
            for (MenuEntry me:a.getValue()) {
                if(me.getCod() == cod) {
                    return me;
                }
            }
        }
        return null;
    }
}
