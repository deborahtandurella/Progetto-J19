package application;

import application.restaurant_exception.NoCritiquesException;

import java.util.HashSet;

public class CritiqueFilter {
    public CritiqueFilter() {
    }

    /**
     * Method which select the critiques with a mean >= of the grade
     *
     * @param restCrit the critiques of the restaurant
     * @param grade, the vote used to select the critiques
     * @return only the critiques which verify the condition
     */
    protected HashSet<Critique> getRestCritByVote(HashSet<Critique> restCrit, int grade){
        HashSet<Critique> crit = new HashSet<>();
        for (Critique c: restCrit) {
            if(c.getMean() >= grade)
                crit.add(c);
        }
        if(crit.isEmpty())
            throw new NoCritiquesException("Nessuna Critica con un voto medio desiderato.");
        else
            return crit;
    }


    /**
     * Method which select the critiques with a section with a grade>= of the vote
     *
     * @param restCrit the critiques of the restaurant
     * @param grade,  the vote used to select the critiques
     * @param section of the critiques
     * @return only the critiques which verify the condition
     */
    protected HashSet<Critique> getRestCritByVoteSection(HashSet<Critique> restCrit, int grade, CritiqueSections section){
        HashSet<Critique> crit = new HashSet<>();
        for (Critique c: restCrit) {
            if(c.getSections().get(section) >= grade)
                crit.add(c);
        }
        if(crit.isEmpty())
            throw new NoCritiquesException("Nessuna Critica che soddisfa i criteri selezionati.");
        else
            return crit;
    }
}
