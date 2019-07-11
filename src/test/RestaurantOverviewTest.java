import application.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

    /**
     * Tester of RestaurantOverview class.
     */
    public class RestaurantOverviewTest {
        private final  double  delta = 0.001;
        @Test
        public void computeMeanTester(){
            RestaurantOverview ro = createRestaurantOverview();
            assertEquals(3.0,ro.getSections().get(CritiqueSections.MENU),delta);
            assertEquals(5.0,ro.getSections().get(CritiqueSections.LOCATION),delta);
            assertEquals(7.0,ro.getSections().get(CritiqueSections.SERVIZIO),delta);
            assertEquals(8.0,ro.getSections().get(CritiqueSections.CONTO),delta);
            assertEquals(5.0,ro.getSections().get(CritiqueSections.CUCINA),delta);
        }

        @Test
        public void getMeanTest(){
            RestaurantOverview ro = createRestaurantOverview();
            assertEquals(5.6,ro.getMean(),delta);
        }

        private HashSet<Critique> getCritiqueSet(){
            HashSet<Critique> list = new HashSet<>();
            Critique c1 = new Critique("","",0);
            Critique c2 = new Critique("","",0);
            double[] grade = {3,5,7,8};
            c1.writeVotes(grade);
            c2.writeVotes(grade);
            HashMap<MenuEntry,Double> map = getMenuEntryGrades();
            c1.voteDishes(map);
            c2.voteDishes(map);
            list.add(c1);
            list.add(c2);
            return list;
        }

        private HashMap<MenuEntry,Double> getMenuEntryGrades(){
            MenuEntry me1 = new MenuEntry("",0.,"","","ANTIPASTO");
            MenuEntry me2 = new MenuEntry("",0.,"","","PRIMO");
            HashMap<MenuEntry,Double> map = new HashMap<>();
            map.put(me1,6.);
            map.put(me2,4.);
            return map;
        }

        private RestaurantOverview createRestaurantOverview(){
            HashSet<Critique> list = getCritiqueSet();
            RestaurantOverview ro = new RestaurantOverview();
            ro.computeMean(list);
            return ro;
        }

    }


