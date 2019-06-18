package application;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantOverview {

    public final static int  MINVOTO = 1;
    public final static int  MAXVOTO = 10;
    public final static CritiqueSections [] CRITIQUE_SECTIONS = CritiqueSections.values();
    protected HashMap<CritiqueSections, Double> sections;

    public RestaurantOverview (){
        this.sections = new HashMap<>();
        for(CritiqueSections type :CRITIQUE_SECTIONS){
            this.sections.put(type,0.0);
        }

    }


    public void computeMean(ArrayList<Critique> list){

        for (CritiqueSections type : CRITIQUE_SECTIONS){
            for (Critique c:list ) {
                double tmp =this.sections.get(type);
                this.sections.put(type, c.getSections().get(type)+tmp);
            }
        }

        int nVoti = list.size();
        for(CritiqueSections type : CRITIQUE_SECTIONS){
            Double tmp = this.sections.get(type) / nVoti;
            this.sections.put(type, tmp);
        }

    }

    public HashMap<CritiqueSections, Double> getSections() {
        return sections;
    }
}
