package application;

import application.restaurant_exception.DishAlreadyInMenuException;

import java.util.*;


public class MenuHandler {

    public MenuHandler() {
    }

    /**
     * Starting rom the string which represents the type of the dish, return the correct dishType enum.
     * @param choice, the dish type
     * @return DishType
     */
    public static DishType stringConverter(String choice){
        switch (choice.toLowerCase()){
            case "antipasto" :
                return DishType.ANTIPASTI;
            case "primo": return DishType.PRIMI;
            case "secondo": return DishType.SECONDI;
            case "dolce": return DishType.DOLCI;
            default: return null;
        }
    }


    /**
     * Method called to initialize the ArrayList in the menu
     * @param menu
     * @return
     */
    public  static HashMap<DishType,ArrayList<MenuEntry>> initializeMenu(HashMap<DishType,ArrayList<MenuEntry>> menu){
        menu.put(DishType.ANTIPASTI, new ArrayList<>());
        menu.put(DishType.PRIMI, new ArrayList<>());
        menu.put(DishType.SECONDI, new ArrayList<>());
        menu.put(DishType.DOLCI, new ArrayList<>());
        return menu;
    }

    /**
     *
     * @param dishName
     * @param menu
     */
    public void checkExistance(String dishName,HashMap<DishType,ArrayList<MenuEntry>> menu){
        for(DishType dt : DishType.values()){
            for (MenuEntry me:menu.get(dt)) {
                if(dishName.equalsIgnoreCase(me.getDish()))
                    throw new DishAlreadyInMenuException("Il piatto risulta gia' inserito nel menu!");
            }
        }
    }

    /**
     * Remove a selected dish from a menu
     *
     * @param dishCode the code of the dish
     * @param menu
     */
    public void removeDishFromMenu(String dishCode,HashMap<DishType,ArrayList<MenuEntry>> menu){
        MenuEntry m = null;
        DishType d = null;
        for (DishType dt : DishType.values()) {
            for (MenuEntry me:menu.get(dt)) {
                    if(me.getCod().equals(dishCode)){
                        m=me;
                        d=dt;
                    }

            }
        }
        menu.get(d).remove(m);
    }

    /**
     * Prepares the menu in String format in order to print it in the template
     *
     * @param menu
     * @return
     */
    public LinkedHashMap<String, List<String>> menuToString(HashMap<DishType,ArrayList<MenuEntry>> menu){
        LinkedHashMap<String, List<String>> menuString = new LinkedHashMap<>();
        for (DishType dt:DishType.values()) {
            menuString.put(dt.toString(),new ArrayList<>());
            for (MenuEntry me :menu.get(dt)) {
                menuString.get(dt.toString()).add(me.toString());
            }
        }
        return menuString;
    }
}
