package application;

import java.util.HashMap;
import java.util.Map;

public class HomeCritic {

    private  boolean leggedin;

    public HomeCritic() {
        this.leggedin = false;
    }

    public void writeCritique(int codResturant, int votoServizio, int votoConto, int votoLocation,int votoMenu
                                ,HashMap<MenuEntry,Double> piattiEVoti){
        // devo fare il parse --> giusto che un critico inserisca solo interi ma per fare medie abbiamo bisogno di double

        int [] tmp1 = {votoMenu, votoLocation, votoServizio, votoConto};
        double [] tmp2 = parseVoto(tmp1);          //brutale ma funzionale, passsare tutto il vettore per indici poco carino

        Critique c = new Critique( tmp2[0], tmp2[1], tmp2[2], tmp2[3]);
        for (Map.Entry<MenuEntry,Double> t : piattiEVoti.entrySet()) {
                c.voteDishes(t.getKey(),t.getValue());
        }
        RestaurantCatalogue.getInstance().addCritique(codResturant, c);
    }

    public String findRestaurant(int restaurantCode) {
        String tmp = null;
        try {
            tmp = RestaurantCatalogue.getInstance().findRestaurant(restaurantCode);
        } catch (RestaurantNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return tmp;
    }

    private double[] parseVoto(int[] voti){
        double [] votiD = new double[voti.length];
        for (int i=0; i<voti.length; i++){
            votiD[i] = (double)voti[i];
        }
        return votiD;
    }

    public boolean logIn(String username, String psw){
        return Database.getInstance().logIn(username,psw);
    }

}
