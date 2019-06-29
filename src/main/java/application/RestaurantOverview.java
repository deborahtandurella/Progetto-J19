package application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A overview of the restaurant which is given by the mean of its critiques.
 */
public class RestaurantOverview {

    public final static int  MINVOTO = 1;
    public final static int  MAXVOTO = 10;
    public final static CritiqueSections [] CRITIQUE_SECTIONS = CritiqueSections.values();
    private double mean;
    protected HashMap<CritiqueSections, Double> sections;

    /**
     * Create a new overview.
     */
    public RestaurantOverview (){
        this.sections = new HashMap<>();
        for(CritiqueSections type :CRITIQUE_SECTIONS){
            this.sections.put(type,0.0);
        }
        this.mean = 0;
    }

    /**
     * It computes the mean of each section
     * @param list of the critiques of a restaurant
     */
    public void computeMean(ArrayList<Critique> list){

        for (CritiqueSections type : CRITIQUE_SECTIONS){
            for (Critique c:list ) {
                double tmp =this.sections.get(type);
                this.sections.put(type, c.getSections().get(type)+tmp);
            }
        }

        this.mean = 0;
        int nVoti = list.size();
        for(CritiqueSections type : CRITIQUE_SECTIONS){
            Double tmp = this.sections.get(type) / nVoti;
            this.sections.put(type, tmp);
            this.mean += tmp;
        }
        this.mean = this.mean/CritiqueSections.values().length;
    }

    /**
     *
     * @return a map whose keys are the sections and the values are their votes
     */
    public HashMap<CritiqueSections, Double> getSections() {
        return sections;
    }

    /**
     *
     * @return the mean of the votes of the sections
     */
    public double getMean() {
        return mean;
    }
}
