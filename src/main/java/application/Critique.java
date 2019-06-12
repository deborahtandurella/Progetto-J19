package application;

import java.util.ArrayList;
import java.util.HashMap;

public class Critique {

    public final static int  MINVOTO = 1;
    public final static int  MAXVOTO = 10;
    public final static CritiqueSections [] CRITIQUE_SECTIONS = CritiqueSections.values();
    private HashMap<CritiqueSections, Double> sections;
    private HashMap<MenuEntry, Double> dishes;
    private String comment;

    public Critique(double votoMenu, double votoLocation , double votoServizio, double votoConto) {
        this.sections = new HashMap<>();
        this.dishes = new HashMap<>();
        writeCritique( votoMenu, votoServizio, votoConto, votoLocation );
        this.sections.put(CritiqueSections.CUCINA, 0.);
        this.comment= null;
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
        return this.sectionsToString() + "\n" + this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String sectionsToString(){
        StringBuilder stb =  new StringBuilder();
        String enter =  "\n";
        String tab =  "\t";
        stb.append(enter);
        for( int i = 0 ;i < CRITIQUE_SECTIONS.length; i++){
            CritiqueSections  en = CRITIQUE_SECTIONS[i];
            double voto = this.sections.get(en);
            stb.append(en.toString()+tab+voto+enter);
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

    public HashMap<CritiqueSections, Double> getSections() {
        return sections;
    }

    public static Critique computeMean(ArrayList<Critique> list){
        int t = CRITIQUE_SECTIONS.length;
        double votiOv [] =  new double[t];
        int numberOfVotiOv [] =  new int[t];
        for( int i =0 ; i < t ; i++){
            votiOv[i]=0.0;
            numberOfVotiOv[i]=0;
        }

        for (int i =0 ; i< t ; i++){
            for (Critique c:list ) {
                votiOv[i] += c.getSections().get(CRITIQUE_SECTIONS[i]);
                numberOfVotiOv[i]+=1;
            }
        }
        double medieVoti [] = new double[t];
        for(int i=0; i< t ; i++){
            if(numberOfVotiOv[i] != 0){
                medieVoti[i] = (votiOv[i]/numberOfVotiOv[i]);
            }
            else {
                medieVoti[i] = 0;
            }
        }
        Critique temp = new Critique(medieVoti[0],medieVoti[1],medieVoti[2],medieVoti[3]);
        temp.getSections().replace(CritiqueSections.CUCINA, medieVoti[4]);
        return temp;

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
