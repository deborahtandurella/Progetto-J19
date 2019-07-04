package persistence;

import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        new RestaurantsMapper(new OverviewMapper());
    }
}
