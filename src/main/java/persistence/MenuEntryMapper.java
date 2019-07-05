package persistence;

import application.DishType;
import application.MenuEntry;
import application.MenuHandler;
import application.restaurant_exception.EmptyMenuException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * It is the mapper of the table "menuentry"
 */
public class MenuEntryMapper extends AbstractPersistenceMapper {

    protected MenuEntryMapper() throws SQLException {
        super("menuentry");
    }



    @Override
    protected Object getObjectFromTable(String OID) throws SQLException {
        return null;
    }

    @Override
    protected Object getObjectFromCache(String OID) {
        return null;
    }

    @Override
    protected void updateCache(String OID, Object obj) {

    }


    @Override
    public void put(String OID, Object obj) {

    }

    /**
     * Method which returns the menu of a restaurant(specified by OID, which is itd code).
     * @param OID is the key of the restaurant .
     * @return Map<DishType,ArrayList<MenuEntry>> which is the menu of the restaurant .
     * @throws SQLException
     * @throws EmptyMenuException if no MenuEntry are found for the restaurant
     */
    public HashMap<DishType,ArrayList<MenuEntry>> getMenu(String OID) throws SQLException {
        HashMap<DishType,ArrayList<MenuEntry>> menu = new HashMap<>();
        menu  = MenuHandler.getInstance().initializeMenu(menu);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select * from "+super.tableName + " where RESTAURANT ="+ OID);
        if(!rs.isBeforeFirst())
            throw new EmptyMenuException();
        while (rs.next()){
            menu.get(MenuHandler.getInstance().stringConverter(rs.getString(5)))
                    .add(new MenuEntry(rs.getString(2),rs.getDouble(3),rs.getInt(1)));
        }
        return menu;
    }
}
