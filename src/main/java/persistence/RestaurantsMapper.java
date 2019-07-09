package persistence;

import application.Restaurant;
import application.RestaurantOverview;
import application.restaurant_exception.EmptyMenuException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * It is the mapper of the table "restaurants"
 */
public class RestaurantsMapper extends AbstractPersistenceMapper {

    private Map<String, Restaurant> restaurant ;


    /**
     * Initialize restaurant(is the cache of this mapper) with
     * the restaurants which are registered  when the system is set up.
     * @throws SQLException
     */
    public RestaurantsMapper(OverviewMapper om, MenuEntryMapper mem) throws SQLException {
        super("restaurants");
        this.restaurant = new HashMap<>();
        setUp(om, mem);
    }

    @Override
    protected Object getObjectFromTable(String OID) {
        return null;
    }

    @Override
    protected Object getObjectFromCache(String OID) {
        if(this.restaurant.containsKey(OID))
            return this.restaurant.get(OID);
        return null;
    }

    @Override
    protected void updateCache(String OID,Object obj) {
        this.restaurant.remove(OID);
        this.restaurant.put(OID,(Restaurant)obj);
    }


    @Override
    public void put(String OID, Object obj) {
        Restaurant r = (Restaurant)obj;
        updateCache(OID,r);
        try {
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?,?)");
            pstm.setString(1,OID);
            pstm.setString(2,r.getName());
            pstm.setString(3,r.getAddress());
            pstm.setString(4,r.getCity());
            pstm.setString(5,r.getOwner());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTable(String OID, Object obj) {
        Restaurant r = (Restaurant)obj;
        updateCache(OID,r);
        try{
            PreparedStatement pstm = conn.prepareStatement("UPDATE " + tableName+" SET NAME=?, ADDRESS=?, CITY=?," +
                    " OWNER=?  WHERE COD_REST=? ");
            pstm.setString(1,r.getName());
            pstm.setString(2,r.getAddress());
            pstm.setString(3,r.getCity());
            pstm.setString(4,r.getOwner());
            pstm.setString(5,OID);
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method called when RestaurantMapper Class is created.(when the system is set up)
     * It populates the cache with the restaurant registered to the system.
     * @throws SQLException
     */
    protected void setUp(OverviewMapper om, MenuEntryMapper mem) throws SQLException {
        Statement stm = super.conn.createStatement();
        ResultSet rs = stm.executeQuery("select * from "+super.tableName);
        while (rs.next()){
            Restaurant tmp = new Restaurant(rs.getString(2),rs.getString(3),
                    rs.getString(5),rs.getString(4));
            tmp.setOverview(((RestaurantOverview) om.get(rs.getString(1))));
            try{
                tmp.addMenu(mem.getMenu(rs.getString(1)));
            }catch(EmptyMenuException e){
            }
            this.restaurant.put(rs.getString(1),tmp);

        }

        OIDCreator.getInstance().setRestaurantCode(Integer.parseInt(getLastObjectCode("COD_REST")));

    }



    public Map<String, Restaurant> getRestaurant() {
        return restaurant;
    }
}
