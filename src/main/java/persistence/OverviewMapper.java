package persistence;

import application.CritiqueSections;
import application.RestaurantOverview;

import java.sql.PreparedStatement;
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

    public OverviewMapper() throws SQLException {
        super("OVERVIEW");
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
        this.overview.remove(OID);
        this.overview.put(OID,(RestaurantOverview)obj);
    }



    @Override
    public synchronized void put(String OID, Object obj) {
        RestaurantOverview ro = (RestaurantOverview) obj;
        updateCache(OID,ro);
        try{
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?,?,?,?)");
            pstm.setString(1,OID);
            pstm.setString(7,Double.toString(ro.getMean()));
            for(int i = 0; i< RestaurantOverview.CRITIQUE_SECTIONS.length; i++){
                pstm.setString(i+2,Double.toString(ro.getSections()
                        .get(RestaurantOverview.CRITIQUE_SECTIONS[i])));
            }
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public synchronized void updateTable(String OID,Object obj){
        RestaurantOverview ro = (RestaurantOverview)obj;
        updateCache(OID,ro);
        try{
            String query = "UPDATE " + tableName+" SET MENU= ? , LOCATION =? , SERVIZIO = ? , CONTO = ? , CUCINA = ? , MEAN = ?" +
                    " where RESTAURANT = ?";
            PreparedStatement pstm = conn.prepareStatement(query);
            setQueryParameters(pstm,ro,OID);
            pstm.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setQueryParameters(PreparedStatement pstm,RestaurantOverview ro, String OID) throws SQLException {
        pstm.setString(7,OID);
        pstm.setDouble(6,ro.getMean());
        HashMap<CritiqueSections,Double> tmp = ro.getSections();
        for (int i =0; i<CritiqueSections.values().length; i++) {
            pstm.setDouble(i+1,tmp.get(CritiqueSections.values()[i]));
        }
    }

}
