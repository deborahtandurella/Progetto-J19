package persistence;

import application.Critique;
import application.MenuEntry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Mapper of the table "critique_dish".
 * Table in which there are the codes of the critiques and
 * the dishes who are valuated in them with their grade.
 */
public class DishCritiquesMapper extends AbstractPersistenceMapper {
    private MenuEntryMapper menuEntryMapper;

    public DishCritiquesMapper(MenuEntryMapper menuEntryMapper) throws SQLException {
        super("CRITIQUE_DISH");
        this.menuEntryMapper = menuEntryMapper;
    }

    @Override
    protected Object getObjectFromTable(String OID){
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
    public synchronized void  put(String OID, Object obj)throws SQLException {
        Critique c = (Critique)obj;

        for (Map.Entry<MenuEntry,Double> temp:c.getDishes().entrySet()) {
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?)");
            pstm.setString(1,OID);
            pstm.setString(2,temp.getKey().getCod());
            pstm.setDouble(3,temp.getValue());
            pstm.execute();

        }

    }

    @Override
    public synchronized  void updateTable(String OID, Object obj){

    }

    /**
     * Method called by CritiquesMapper when the system is set up,
     * in order to instance each critique with its MenuEntry
     * @param critiqueCode
     * @return the dishes of the critique , each one matched with its grade
     */
    protected HashMap<MenuEntry,Double> getDishesGrades(String critiqueCode) throws SQLException{
        HashMap<MenuEntry, Double> gradeDish = new HashMap<>();

        PreparedStatement pstm = conn.prepareStatement("SELECT DISH_CODE,VOTO_DISH FROM "+tableName+" WHERE CRITIQUE_CODE = ?" );
        pstm.setInt(1, Integer.parseInt(critiqueCode));
        ResultSet rs = pstm.executeQuery();
        while (rs.next()){
            gradeDish.put((MenuEntry) menuEntryMapper.get(Integer.toString(rs.getInt(1))),
                    Double.parseDouble(rs.getString(2)));
        }

        return gradeDish;
    }
}
