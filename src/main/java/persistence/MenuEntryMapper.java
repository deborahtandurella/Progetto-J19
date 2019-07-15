package persistence;

import application.DishType;
import application.MenuEntry;
import application.MenuHandler;
import application.restaurant_exception.EmptyMenuException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * It is the mapper of the table "menuentry"
 */
public class MenuEntryMapper extends AbstractPersistenceMapper {
    private List<MenuEntry> menuEntries ;


    protected MenuEntryMapper() throws SQLException {
        super("MENUENTRY");
        menuEntries = new ArrayList<>();
        OIDCreator.getInstance().setMenuEntryCode(getLastObjectCode("DISH_COD"));
    }



    @Override
    protected Object getObjectFromTable(String OID) throws SQLException {
        PreparedStatement pstm = conn.prepareStatement("select * from "+tableName+" where DISH_COD = ?");
        pstm.setString(1,OID);
        ResultSet rs = pstm.executeQuery();
        if(!rs.next())
            throw new ObjectNotFoundException();
        return new MenuEntry(rs.getString(2),rs.getDouble(3),rs.getString(1),OID,
                rs.getString(5)) ;
    }

    @Override
    protected Object getObjectFromCache(String OID) {
        for (MenuEntry me: menuEntries) {
            if (me.getCod().equals(OID))
                return me;
        }
        return null;
    }

    @Override
    protected void updateCache(String OID, Object obj) {
        MenuEntry me = null;
        for (MenuEntry m:menuEntries) {
            if(m.getCod().equals(OID)){
                me = m;
                break;
            }
        }
        if(me != null)
            menuEntries.remove(me);
        menuEntries.add((MenuEntry)obj);
    }


    @Override
    public synchronized void put(String OID, Object obj)throws SQLException {
        MenuEntry me = (MenuEntry)obj;
        updateCache(OID,me);

        PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?,?)");
        pstm.setString(1,OID);
        pstm.setString(2,me.getDish());
        pstm.setString(3,Double.toString(me.getPrice()));
        pstm.setString(4,me.getRestaurantCode());
        pstm.setString(5,me.getType());
        pstm.execute();

    }

    @Override
    public synchronized void updateTable(String OID, Object obj)throws SQLException {
        MenuEntry me = (MenuEntry) obj;
        updateCache(OID,me);

        PreparedStatement pstm = conn.prepareStatement("UPDATE " + tableName+" SET DISH =?, PRICE =?, " +
                "DISH_TYPE =?,where DISH_COD = ?");
        pstm.setString(1,me.getDish());
        pstm.setDouble(2,me.getPrice());
        pstm.setString(3,me.getType());
        pstm.setString(4,me.getCod());
        pstm.execute();

    }

    /**
     * Method which returns the menu of a restaurant(specified by OID, which is itd code).
     * @param OID_Restaurant is the key of the restaurant .
     * @return Map<DishType,ArrayList<MenuEntry>> which is the menu of the restaurant .
     * @throws SQLException
     * @throws EmptyMenuException if no MenuEntry are found for the restaurant
     */
    protected HashMap<DishType,ArrayList<MenuEntry>> getMenu(String OID_Restaurant) throws SQLException {
        HashMap<DishType,ArrayList<MenuEntry>> menu = new HashMap<>();
        menu  = MenuHandler.initializeMenu(menu);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select * from "+super.tableName + " where RESTAURANT ="+ OID_Restaurant);
        if(!rs.isBeforeFirst())
            throw new EmptyMenuException();
        while (rs.next()){
            MenuEntry me =  new MenuEntry(rs.getString(2),rs.getDouble(3),
                    rs.getString(1),OID_Restaurant,rs.getString(5));
            updateCache(me.getCod(),me);
            menu.get(MenuHandler.stringConverter(rs.getString(5)))
                    .add(me);
        }
        return menu;
    }

    protected synchronized void remove(String OID) throws SQLException {
        removeFromCahce(OID);
        PreparedStatement pstm = conn.prepareStatement("delete from "+ tableName+ " where DISH_COD =?");
        pstm.setString(1,OID);
        pstm.execute();

    }

    private void removeFromCahce(String OID){
        MenuEntry me = null;
        for (MenuEntry m: menuEntries) {
            if(m.getCod().equals(OID))
                me = m;
        }
        menuEntries.remove(me);
    }
}
