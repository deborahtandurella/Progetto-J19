package persistence;

import application.RestaurantOverview;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * It is the mapper of te table "overview"
 */
public class OverviewMapper extends AbstractPersistenceMapper {

    private Map<String, RestaurantOverview> overview;
    private int counter;

    public OverviewMapper() throws SQLException {
        super("overview");
        this.overview = new HashMap<>();
    }

    /**
     * Method which is called to get the overview of a restaurant,when it is instanced.
     * @param OID is the key of the restaurant
     * @return the overview of the restaurant
     * @throws SQLException
     */
    @Override
    protected Object getObjectFromTable(String OID) throws SQLException {
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select * from "+ super.tableName +" where RESTAURANT ="+ OID);
        double [] grade = new double[5];
        if(rs.next()) {
            for (int i = 0; i < grade.length; i++) {
                grade[i] = rs.getDouble(i + 2);

            }
        }
        return new RestaurantOverview(grade,rs.getDouble(7));

    }

    @Override
    protected Object getObjectFromCache(String OID) {
        if(!overview.containsKey(OID))
            return null;
        return overview.get(OID);
    }

    @Override
    protected void updateCache(String OID, Object obj) {
        this.overview.put(OID,(RestaurantOverview)obj);
    }



    @Override
    public void put(String OID, Object obj) {

    }


}
