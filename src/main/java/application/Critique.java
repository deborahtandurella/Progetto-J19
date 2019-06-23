package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Critique extends RestaurantOverview{

    private HashMap<MenuEntry, Double> dishes;
    private String comment;
    private String critico;

    public Critique(double votoMenu, double votoLocation , double votoServizio, double votoConto, String critico) {
        this.sections = new HashMap<>();
        this.dishes = new HashMap<>();
        writeCritique( votoMenu, votoServizio, votoConto, votoLocation );
        this.sections.put(CritiqueSections.CUCINA, 0.);
        this.comment = critico;
    }

    private void writeCritique(double votoMenu, double votoServizio, double votoConto, double votoLocation){
        try{
            double [] tmp ={ votoMenu, votoLocation, votoServizio, votoConto};
            checkNumber(tmp);
            insertVoti(tmp);

        }catch (InvalidNumberException e){
            System.err.println(e.getMessage());
        }
    }

    public void voteDishes(HashMap<MenuEntry, Double> dv){
        try{
            this.dishes = dv;
            this.sections.replace(CritiqueSections.CUCINA,meanDishes());
        }
        catch (InvalidNumberException e){
            System.err.println(e.getMessage());
        }
    }
    @Override
    public String toString() {
        return this.sectionsToString() + this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String sectionsToString(){
        StringBuilder stb =  new StringBuilder();
        String separator =  "£";
        for( int i = 0 ;i < CRITIQUE_SECTIONS.length; i++){
            CritiqueSections  en = CRITIQUE_SECTIONS[i];
            double voto = this.sections.get(en);
            stb.append(en.toString()+separator+String.format("%.2f", voto).replace(",",".")+separator);
        }
        return stb.toString();
    }

    private void checkNumber(double [] voto){
        for( int  i=0 ; i<voto.length ; i++){
            if(voto[i] < MINVOTO || voto[i] > MAXVOTO)
                throw new InvalidNumberException("Voto non valido! Inserire un numero compreso tra 1 e 10");
        }
    }


    private void insertVoti(double[] votiD){

        for(int i=0; i<CRITIQUE_SECTIONS.length-1; i++){
            this.sections.put(CRITIQUE_SECTIONS[i], votiD[i]);
        }
    }

    private  double meanDishes(){
        double tmp = 0.;
        int length = 0;
        for (double d: this.dishes.values()) {
            if(d!=0){
                tmp+= d ;
                length++;
            }
        }
        return tmp/length;
    }
}
