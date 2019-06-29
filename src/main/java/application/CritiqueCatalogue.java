package application;

import application.restaurant_exception.NoCritiquesException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class CritiqueCatalogue {
    private static CritiqueCatalogue instance = null;
    private HashSet<Critique> critiques;
    private int critiqueCode;

    private CritiqueCatalogue() {
        this.critiques = new HashSet<>();
        this.critiqueCode = 0;
    }

    public static CritiqueCatalogue getInstance(){
        if (instance == null)
            instance = new CritiqueCatalogue();
        return instance;
    }

    public int addNewCritique(Critique critique){
        this.critiqueCode++;
        critique.setCode(this.critiqueCode);
        if(!this.critiques.add(critique)) {
            this.critiqueCode--;
            // eccezzione critica non aggiunta
        }
        updateRestaurantOverview(critique.getRestaurantCode());
        return this.critiqueCode;
    }

    public ArrayList<String> getCritiquesByUser(String critic){
        ArrayList<String> critique = new ArrayList<>();
        for (Critique c: this.critiques) {
            if(c.getCritico().equals(critic)){
                String restaurantName = RestaurantCatalogue.getInstance().getRestaurantName(c.getRestaurantCode());
                critique.add(restaurantName+"£"+c.toString());
            }

        }
        if(critique.isEmpty())
            throw new NoCritiquesException("Nessuna critica ancora compilata!");
        return critique;
    }

    public ArrayList<Critique> getRestaurantCritics(int restaurantCode){
        ArrayList<Critique> restaurantCritics = new ArrayList<>();
        for (Critique c: this.critiques) {
            if(c.getRestaurantCode() == restaurantCode)
                restaurantCritics.add(c);
        }
        if (restaurantCritics.isEmpty())
            throw new NoCritiquesException("Ancora nessuna critica per il ristorante selezionato");
        return restaurantCritics;
    }
    private void updateRestaurantOverview(int restaurantCode){
        RestaurantOverview ro = new RestaurantOverview();
        ro.computeMean(getRestaurantCritics(restaurantCode));
        RestaurantCatalogue.getInstance().setRestaurantOverview(restaurantCode,ro);
    }

    public LinkedList<String> getRestaurantCritiqueToString(int restCode){
        ArrayList<Critique> restCrit = this.getRestaurantCritics(restCode);
        LinkedList<String> critiques = new LinkedList<>();
        for(Critique c : restCrit){
            critiques.add(c.toString());
        }
        return critiques;
    }


}
