package application;

import application.restaurant_exception.NoCritiquesException;
import persistence.PersistenceFacade;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Singleton class
 * A catalogue of the critiques which have been compiled
 */
public class CritiqueCatalogue {
    private static CritiqueCatalogue instance = null;

    private CritiqueCatalogue() {
    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If class has not been already created it instantiates the class and returns the instance
     * @return instance(CritiqueCatalogue)
     */
    public static CritiqueCatalogue getInstance(){
        if (instance == null)
            instance = new CritiqueCatalogue();
        return instance;
    }

    /**
     * Method which is called from 'HomeCritic' when a critic write a critique,
     * in order to add it in the catalogue system.
     *
     * @param critique the critique
     */
    public void addNewCritique(Critique critique){
        PersistenceFacade.getInstance().addNewCritique(critique);
    }

    /**
     * Method which is called from 'HomeCritic' to show only the critiques compiled by the current logged critic
     *
     * @param critic, the name of the critic ('username')
     * @return critique, the list of critiques of the critic selected
     */
    public ArrayList<String> getCritiquesByUser(String critic){
        ArrayList<String> critique = new ArrayList<>();
        for (Critique c: this.getCritiques()) {
            if(c.getCritico().equals(critic)){
                String restaurantName = RestaurantCatalogue.getInstance().getRestaurantName(c.getRestaurantCode());
                critique.add(restaurantName+"£"+c.toString());
            }
        }
        if(critique.isEmpty())
            throw new NoCritiquesException("Nessuna critica ancora compilata!");
        return critique;
    }

    /**
     * Method called by  'updateRestaurantOverview' and 'getRestaurantCritiqueToString' in this class.
     * It selected only the critiques of a particular restaurant
     *
     * @param restaurantCode, the code of the restaurant selected
     * @return restaurantCritics, the list of the critiques of the restaurant
     */
    public HashSet getRestaurantCritics(int restaurantCode){
        HashSet<Critique> restaurantCritics = new HashSet<>();
        for (Critique c: this.getCritiques()) {
            if(c.getRestaurantCode() == restaurantCode)
                restaurantCritics.add(c);
        }
        if (restaurantCritics.isEmpty())
            throw new NoCritiquesException("Ancora nessuna critica per il ristorante selezionato");
        return restaurantCritics;
    }

    /**
     * Method called by 'addNewCritique' in this class
     * It updates the overview of a restaurant after that a critic has written a critique
     *
     * @param restaurantCode, the code of the restaurant which has been critiqued
     */
    private void updateRestaurantOverview(int restaurantCode){
        RestaurantOverview ro = new RestaurantOverview();
        ro.computeMean(getRestaurantCritics(restaurantCode));
        RestaurantCatalogue.getInstance().setRestaurantOverview(restaurantCode,ro);
    }

    /**
     * Method which is called to show to an user the overview of a restaurant with its critiques
     *
     * @param restCode, the code of the restaurant selected
     * @return critiques, the list of the critiques of the restaurant
     */
    public LinkedList<String> getRestaurantCritiqueToString(int restCode){
        HashSet<Critique> restCrit = this.getRestaurantCritics(restCode);
        LinkedList<String> critiques = new LinkedList<>();
        for(Critique c : restCrit){
            critiques.add(c.toString());
        }
        return critiques;
    }

    /**
     * It gets all the critiques saved in the system.
     *
     * @ critiques saved in the mapper CritiquesMapper
     */
    private HashSet<Critique> getCritiques(){
        return PersistenceFacade.getInstance().getCritiques();
    }

}
