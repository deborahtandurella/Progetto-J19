package persistence;

import application.MenuEntry;
import application.Restaurant;
import application.user.User;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        //System.out.println(PersistenceFacade.getInstance().getAllRestaurants().toString());
        User user = PersistenceFacade.getInstance().getUser("seb");
        //PersistenceFacade.getInstance().addMenuEntry(new MenuEntry("prova",23.4,1,4,"PRIMO"));
        PersistenceFacade.getInstance().addRestaurant("7", new Restaurant("prova","prova","prova","prova"));

    }
}
