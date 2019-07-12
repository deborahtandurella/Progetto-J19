package application;

import application.restaurant_exception.NoCritiquesException;
import persistence.OverviewMapper;
import persistence.PersistenceFacade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Singleton class
 * A catalogue of the critiques which have been compiled
 */
public class CritiqueCatalogue {
    private static CritiqueCatalogue instance = null;
    private CritiqueFilter critFilter = null;

    private CritiqueCatalogue() {

        this.critFilter = new CritiqueFilter();
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
    public void addNewCritique(Critique critique)throws SQLException{
        PersistenceFacade.getInstance().addNewCritique(critique);
        updateRestaurantOverview(critique.getRestaurantCode());
    }

    /**
     * Method which is called from 'HomeCritic' to show only the critiques compiled by the current logged critic
     *
     * @param critic, the name of the critic ('username')
     * @return critique, the list of critiques of the critic selected
     */
    public ArrayList<String> getCritiquesByUser(String critic)throws SQLException {
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
    public HashSet<Critique> getRestaurantCritics(String restaurantCode){
        HashSet<Critique> restaurantCritics = new HashSet<>();
        for (Critique c: this.getCritiques()) {
            if(c.getRestaurantCode().equals(restaurantCode)){
                restaurantCritics.add(c);
            }
        }
        if (restaurantCritics.isEmpty()) {
            throw new NoCritiquesException("Ancora nessuna critica per il ristorante selezionato");
        }
        return restaurantCritics;
    }

    /**
     * Method called by 'addNewCritique' in this class
     * It updates the overview of a restaurant after that a critic has written a critique
     *
     * @param restaurantCode, the code of the restaurant which has been critiqued
     */
    private void updateRestaurantOverview(String restaurantCode)throws SQLException{
        RestaurantOverview ro = new RestaurantOverview();
        ro.computeMean(getRestaurantCritics(restaurantCode));
        RestaurantCatalogue.getInstance().setRestaurantOverview(restaurantCode,ro);
        PersistenceFacade.getInstance().updateTable(OverviewMapper.class,ro,restaurantCode);
    }

    /**
     * Method which is called to show to an user the overview of a restaurant with its critiques
     *
     * @param restCrit, the critiques to print
     * @return critiques, the list of the critiques of the restaurant in String format
     */
    public LinkedList<String> getRestaurantCritiqueToString(HashSet<Critique> restCrit){
        LinkedList<String> critiques = new LinkedList<>();
        for(Critique c : restCrit){
            critiques.add(c.toString());
        }
        return critiques;
    }

    /**
     * It gets all the critiques saved in the system.
     *
     * @return  critiques saved in the mapper CritiquesMapper
     */
    private HashSet<Critique> getCritiques(){
        return PersistenceFacade.getInstance().getCritiques();
    }

    /**
     *Method which select the critiques with a mean >= of the grade
     *
     *  @param grade, the vote used to select the critiques
     *  @return only the critiques which verify the condition
     */
    public LinkedList<String> getRestCritByVoteToString(int grade, String restCode){
        return this.getRestaurantCritiqueToString(this.critFilter.getRestCritByVote(this.getRestaurantCritics(restCode),
                grade));
    }


    /**
     * Method which select the critiques with a section with a grade>= of the vote
     *
     * @param grade,  the vote used to select the critiques
     * @param restCode code of the restaurant
     * @param section of the critiques
     * @return only the critiques which verify the condition
     */
    public LinkedList<String> getRestCritByVoteSectionToString(int grade, String restCode, CritiqueSections section){
        return this.getRestaurantCritiqueToString(this.critFilter.getRestCritByVoteSection(
                                                    this.getRestaurantCritics(restCode), grade, section));
    }

}
