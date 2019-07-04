package persistence;

import application.Restaurant;
import application.RestaurantOverview;
import application.restaurant_exception.EmptyMenuException;

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
        this.restaurant.put(OID,(Restaurant)obj);
    }

    @Override
    public void put(String OID, Object obj) {
        //TODO implementare metodo potrebbe valre la pena di creare classe restaurant info?
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
                    rs.getString(3));
            tmp.setOverview(((RestaurantOverview) om.get(rs.getString(1))));
            try{
                tmp.addMenu(mem.getMenu(rs.getString(1)));
            }catch(EmptyMenuException e){
            }
            this.restaurant.put(rs.getString(1),tmp);

        }
        List <Integer> temp = new ArrayList<>();
        for (String s: restaurant.keySet()) {
            temp.add(Integer.parseInt(s));
        }
        counter = Collections.max(temp);

    }



    public Map<String, Restaurant> getRestaurant() {
        return restaurant;
    }
}
