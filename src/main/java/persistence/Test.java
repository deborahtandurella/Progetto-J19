package persistence;

import application.user.User;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        //System.out.println(PersistenceFacade.getInstance().getAllRestaurants().toString());
        User user = PersistenceFacade.getInstance().getUser("seb");
        System.out.println(user.getPassword());

    }
}
