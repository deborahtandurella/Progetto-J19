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
        super("menuentry");
        menuEntries = new ArrayList<>();
        OIDCreator.getInstance().setMenuEntryCode(Integer.parseInt(getLastObjectCode("DISH_COD")));
    }



    @Override
    protected Object getObjectFromTable(String OID) throws SQLException {
        PreparedStatement pstm = conn.prepareStatement("select * from "+tableName+" where DISH_COD = ?");
        pstm.setString(1,OID);
        ResultSet rs = pstm.executeQuery();
        if(!rs.next())
            throw new ObjectNotFoundException();
        return new MenuEntry(rs.getString(2),rs.getDouble(3),rs.getInt(1),
                Integer.parseInt(OID),rs.getString(5)) ;
    }

    @Override
    protected Object getObjectFromCache(String OID) {
        for (MenuEntry me: menuEntries) {
            if (Integer.toString(me.getCod()).equals(OID))
                return me;
        }
        return null;
    }

    @Override
    protected void updateCache(String OID, Object obj) {
        menuEntries.add((MenuEntry)obj);
    }


    @Override
    public void put(String OID, Object obj) {
        MenuEntry me = (MenuEntry)obj;
        try {
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?,?)");
            pstm.setString(1,OID);
            pstm.setString(2,me.getDish());
            pstm.setString(3,Double.toString(me.getPrice()));
            pstm.setString(4,Integer.toString(me.getRestaurantCode()));
            pstm.setString(5,me.getType());
            pstm.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateTable(String OID, Object obj) {

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
        menu  = MenuHandler.getInstance().initializeMenu(menu);
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery("select * from "+super.tableName + " where RESTAURANT ="+ OID_Restaurant);
        if(!rs.isBeforeFirst())
            throw new EmptyMenuException();
        while (rs.next()){
            MenuEntry me =  new MenuEntry(rs.getString(2),rs.getDouble(3),rs.getInt(1),
                    Integer.parseInt(OID_Restaurant),rs.getString(5));
            updateCache(Integer.toString(me.getCod()),me);
            menu.get(MenuHandler.getInstance().stringConverter(rs.getString(5)))
                    .add(me);
        }
        return menu;
    }
}
