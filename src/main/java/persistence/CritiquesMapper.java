package persistence;

import application.Critique;
import application.CritiqueSections;
import application.MenuEntry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;

public class CritiquesMapper extends AbstractPersistenceMapper {
    private HashSet<Critique> critiques;
    private DishCritiquesMapper dcm ;


    public CritiquesMapper(DishCritiquesMapper dcm) throws SQLException {
        super("CRITIQUES");
        this.critiques = new HashSet<>();
        this.dcm = dcm;
        setUp();
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
        this.critiques.add((Critique)obj);
    }

    @Override
    public synchronized void put(String OID, Object obj) {
        Critique c = (Critique) obj;
        c.setCode(Integer.parseInt(OID));
        updateCache(Integer.toString(c.getCritiqueCode()),c);
        try{
            PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?,?,?,?,?,?,?,?)");
            setStringCritiquesTable(pstm,c);
            pstm.execute();
            this.dcm.put(Integer.toString(c.getCritiqueCode()),c);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void updateTable(String OID, Object obj) {

    }

    /**
     * Set the cache up when the system is started.
     * It instances all the critiques which are in the database.
     */
    public  void setUp() {
        try {
            Statement stm = super.conn.createStatement();
            ResultSet rs = stm.executeQuery("select * from "+super.tableName);
            while (rs.next()){
                Critique tmpCrit = createCritique(rs);
                tmpCrit.setComment(rs.getString(9));
                tmpCrit.voteDishes(dcm.getDishesGrades(Integer.toString(tmpCrit.getCritiqueCode())));
                updateCache(rs.getString(1),tmpCrit);
            }
            OIDCreator.getInstance().setCritiquesCode(Integer.parseInt(getLastObjectCode("CRITIQUE_COD")));
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Method used to create a critique with the data of the DBMS
     *
     * @param rs, the ResultSet used to take the information given by the SQL query
     * @return tmpCrit, the new critique which is ready to be insert in the critique cache
     * @throws SQLException
     */
    private Critique createCritique(ResultSet rs) throws SQLException {
        Critique tmpCrit = new Critique(rs.getString(3), rs.getString(2),rs.getInt(1));
        calculateGradeCritique(rs, tmpCrit);
        return tmpCrit;
    }

    /**
     * Method which inserts the grade in a critique during its creation
     *
     * @param rs, the ResultSet used to take the information given by the SQL query
     * @param tmpCrit, the critique which is in creation
     * @throws SQLException
     */
    private void calculateGradeCritique(ResultSet rs, Critique tmpCrit) throws SQLException{
        double [] grades = new double[CritiqueSections.values().length-1];
        for (int i = 0; i< CritiqueSections.values().length - 1; i++){
            grades[i] = rs.getDouble(i+4);
        }
        tmpCrit.writeVotes(grades);

    }


    /**
     * Method called by PersistenceFacade class
     *@return the cache of the critiques
     */
    public synchronized HashSet<Critique> getCritiques() {
        return critiques;
    }

    /**
     * Method called by method put of this class.
     * Set parameter for PreparedStatement for database table "critiques".
     * @param pstm is the PreparedStatement of put method
     * @param c is the critique which has to be added to the table in the database
     * @throws SQLException
     */
    private void setStringCritiquesTable(PreparedStatement pstm,Critique c)throws SQLException{
        pstm.setString(1,Integer.toString(c.getCritiqueCode()));
        pstm.setString(2,c.getRestaurantCode());
        pstm.setString(3,c.getCritico());
        pstm.setString(9,c.getComment());
        setGradeSections(pstm,c);

    }

    /**
     * Method called by method setStringCritiquesTable of this class.
     * @param pstm is the PreparedStatement of put method
     * @param c is the critique which has to be added to the table in the database
     * @throws SQLException
     */
    private void setGradeSections(PreparedStatement pstm, Critique c) throws SQLException {
        HashMap<CritiqueSections,Double> map = c.getSections();
        for (int i = 0; i<CritiqueSections.values().length ; i++) {
            double grade = map.get(CritiqueSections.values()[i]);
            pstm.setString(i+4,Double.toString(grade));
        }
    }
}
