package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A critique about a restaurant.
 * It can be written only by a critique.
 */
public class Critique extends RestaurantOverview{

    private HashMap<MenuEntry, Double> dishes;
    private String comment;
    private String critico;
    private int critiqueCode;
    private int restaurantCode;

    /**
     * Create a new critique
     * @param critico username of the critic who writes the critique
     * @param restaurantCode code of the restaurant
     * @param critiqueCode code by which the system identifies the critique
     *
     */
    public Critique(String critico,int restaurantCode, int critiqueCode) {
        this.sections = new HashMap<>();
        this.dishes = new HashMap<>();
        this.restaurantCode = restaurantCode;
        this.critico = critico;
        this.comment=null;
        this.critiqueCode= critiqueCode ;
    }

    /**
     * It is called after a critique is created to set the critique code
     * @param code of the critique
     */
    public void setCode(int code) {
        this.critiqueCode = code;
    }

    /**
     * It is called after a critique is created to set the grades of all
     * the critique sections except from CUCINA
     *
     * @param grade of the sections
     */
    public void writeVotes(double [] grade){
        try{
            checkNumber(grade);
            insertVote(grade);

        }catch (InvalidNumberException e){
            System.err.println(e.getMessage());
            //propagare eccezione
        }
    }

    /**
     * Method called when a critique is created.
     * It adds the dishes the critique is about and their grades to the critiques.
     *
     * @param dv Map of MenuEntry and its grade
     */
    public void voteDishes(HashMap<MenuEntry, Double> dv){
        try{
            this.dishes = dv;
            this.sections.put(CritiqueSections.CUCINA,meanDishes());
        }
        catch (InvalidNumberException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * Method called when the critic has to be display in its restaurant web page.
     * @return
     */
    @Override
    public String toString() {
        return this.sectionsToString() + this.dishesToString()+"COMMENTO :£" + this.comment;
    }

    /**
     * Method called after a critique is created to set the comment written by the critic
     * @param comment written by the critic
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Method called by toString or critiqueToString methods
     *
     * @return a String which contains all the sections and their grades divided by a separator(£) in order to be parsed
     */
    private String sectionsToString(){
        StringBuilder stb =  new StringBuilder();
        String separator =  "£";
        for( int i = 0 ;i < CRITIQUE_SECTIONS.length; i++){
            CritiqueSections  en = CRITIQUE_SECTIONS[i];
            double voto = this.sections.get(en);
            stb.append(en.toString()+": "+String.format("%.2f", voto).replace(",",".")+separator);
        }
        return stb.toString();
    }

    /**
     * Method called by toString or critiqueToString methods
     *
     * @return a String which contains all the name and the grades of dishes of the critiques divided by a separator(£)
     * in order to be parsed
     */
    private String dishesToString(){
        StringBuilder stb = new StringBuilder();
        String separator = "£";
        stb.append("Piatti assaggiati: "+separator);
        for(Map.Entry<MenuEntry, Double> dish : this.dishes.entrySet()){
            stb.append(dish.getKey().getDish() + ": " + String.format("%.2f", dish.getValue()).replace(",",".")+separator);
        }
        return stb.toString();
    }

    /**
     * Called by writeVotes
     * It checks if the votes are valid
     * @param vote of the sections
     */
    private void checkNumber(double [] vote){
        for( int  i=0 ; i<vote.length ; i++){
            if(vote[i] < MINVOTO || vote[i] > MAXVOTO) 
                throw new InvalidNumberException("Voto non valido! Inserire un numero compreso tra 1 e 10");
        }
    }

    /**
     * Called by writeVotes
     * It inserts the votes into the corresponding section
     * @param vote of the sections
     */
    private void insertVote(double[] vote){

        for(int i=0; i<CRITIQUE_SECTIONS.length-1; i++){
            this.sections.put(CRITIQUE_SECTIONS[i], vote[i]);
        }
    }

    /**
     * Method called by voteDishes.
     *
     * @return the mean of the grades of the dishes , which is the grade of the section CUCINA
     */
    private double meanDishes(){
        double tmp = 0.;
        int length = 0;
        for (double d: this.dishes.values()) {
                tmp+= d ;
                length++;
        }
        return tmp/length;
    }



    public String getCritico() {
        return critico;
    }



    public int getRestaurantCode() {
        return restaurantCode;
    }

    public String getComment() {
        return comment;
    }

    public int getCritiqueCode() {
        return critiqueCode;
    }

    public HashMap<MenuEntry, Double> getDishes() {
        return dishes;
    }
}
