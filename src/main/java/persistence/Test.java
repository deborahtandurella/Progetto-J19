package persistence;

import application.Restaurant;
import application.user.User;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        //System.out.println(PersistenceFacade.getInstance().getAllRestaurants().toString());
        User user = PersistenceFacade.getInstance().getUser("seb");
        PersistenceFacade.getInstance()
                .addRestaurant(Integer.toString(OIDCreator.getInstance().getNewRestaurantCode()),
                        new Restaurant("prova","via prova","tia","prova"));

    }
}
