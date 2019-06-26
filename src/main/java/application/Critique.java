package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Critique extends RestaurantOverview{

    private HashMap<MenuEntry, Double> dishes;
    private String comment;
    private String critico;
    private int code;

    public Critique(double votoMenu, double votoLocation , double votoServizio, double votoConto, String critico) {
        this.sections = new HashMap<>();
        this.dishes = new HashMap<>();
        writeCritique( votoMenu, votoServizio, votoConto, votoLocation );
        this.sections.put(CritiqueSections.CUCINA, 0.);
        this.critico = critico;
        this.comment=null;
        this.code = 0;
    }

    public void setCode(int code) {
        this.code = code;
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
            dv = this.removeNullDishes(dv);
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

    private String critToString(){
        StringBuilder stb =  new StringBuilder();
        String separator =  "£";
        for( int i = 0 ;i < CRITIQUE_SECTIONS.length; i++){
            CritiqueSections  en = CRITIQUE_SECTIONS[i];
            double voto = this.sections.get(en);
            stb.append(en.toString()+": "+String.format("%.2f", voto).replace(",",".")+separator);
        }
        stb.append("Piatti assaggiati: "+separator);
        for(Map.Entry<MenuEntry, Double> dish : this.dishes.entrySet()){
            stb.append(dish.getKey().getDish() + ": " + String.format("%.2f", dish.getValue()).replace(",",".")+separator);
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

    private double meanDishes(){
        double tmp = 0.;
        int length = 0;
        for (double d: this.dishes.values()) {
                tmp+= d ;
                length++;
        }
        return tmp/length;
    }

    private HashMap<MenuEntry, Double> removeNullDishes(HashMap<MenuEntry, Double> dv){
       ArrayList<MenuEntry> temp = new ArrayList<>();
        for (Map.Entry<MenuEntry, Double> dish : dv.entrySet()){
            if(dish.getValue() == 0){
                temp.add(dish.getKey());
            }
        }
        for (MenuEntry dish : temp){
            dv.remove(dish);
        }
        return dv;
    }

    public String getCritico() {
        return critico;
    }

    public String myCritique(String restaurant){
        String temp ="Ristorante: " + restaurant + "£";
        temp = temp + this.critToString();
        return temp;
    }
}
