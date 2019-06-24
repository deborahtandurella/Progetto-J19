package application;

import application.restaurant_exception.EmptyMenuException;
import application.restaurant_exception.NoCritiquesException;

import java.util.*;


public class Restaurant {
    private final String name;
    private final String address;
    private final int code;
    private HashMap<DishType,ArrayList<MenuEntry>> menu ;
    private ArrayList<Critique> critiques;
    private RestaurantOverview overview;
    private String owner ;

    public Restaurant(String name, String address, int code, String owner){
        this.name = name;
        this.address = address;
        this.code = code;
        this.menu = null;
        this.critiques = new ArrayList<>();
        this.overview = new RestaurantOverview();
        this.owner = owner;
    }


    public void addMenu(HashMap<DishType,ArrayList<MenuEntry>> a){
        menu = a;
    }

    @Override
    public String toString() {
        return this.name + "&" + this.address + "&" + this.overview.toString();
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
        this.overview.computeMean(this.critiques);
    }

    public String getRestaurantInfo(){
        return this.name + "," + this.address;
    }


    public LinkedHashMap<Integer, String> getMenuInfo () {
        if(this.menu == null)
            throw new EmptyMenuException();
        LinkedHashMap<Integer,String> temp = new LinkedHashMap<>();
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

    public HashMap<String, String> getOverview(){
        if(this.critiques.isEmpty())
            throw new NoCritiquesException();
        HashMap<String, String> temp = new HashMap<>();
        temp.put("name", this.name);
        temp.put("address", this.address);
        String all = "";
        String separetor = "&";
        for (Critique c : this.critiques){
            all = all + c.toString() + "\n" + separetor;        // TODO use StringBuilder
        }
        temp.put("overview", all);
        return temp;
    }

    public HashMap<String, String> getMeanCritique(){
        HashMap<String, String> temp = new HashMap<>();
        for (CritiqueSections i : CritiqueSections.values()) {
            temp.put((String.valueOf(i)), String.format("%.2f", this.overview.getSections().get(i)).replace(",","."));
        }
        return temp;
    }

    public void addMenuEntry(String dishType,String dish, double price){
        if(this.menu == null){
            this.menu = new HashMap<>();
            this.menu = MenuHandler.getInstance().initializeMenu(this.menu);
            addDishToMenu(dishType,dish,price,1);
        }
        else{
            int code = MenuHandler.getInstance().getLastCode(this.menu) + 1;
            MenuHandler.getInstance().checkExistance(dish,this.menu);
            addDishToMenu(dishType,dish,price,code);
        }
        printMenu();


    }
    private void addDishToMenu(String dishType,String dish, double price, int code){
        this.menu.get(MenuHandler.getInstance().stringConverter(dishType))
                .add(new MenuEntry(dish,price,code));
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getOwner() {
        return owner;
    }
}
