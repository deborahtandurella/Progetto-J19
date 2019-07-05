package persistence;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        System.out.println(PersistenceFacade.getInstance().getAllRestaurants().toString());
    }
}
