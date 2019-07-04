package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Abstract class which has to be extended by all the Mappers.
 * Each represents a table of the Database and it only can access that table.
 */
public abstract class AbstractPersistenceMapper implements IMapper {

    protected String tableName;
    protected Connection conn;

    /**
     * Constructor of the class
     * @param tableName is name of the table of the db
     * @throws SQLException
     */
    protected AbstractPersistenceMapper(String tableName) throws SQLException {
        this.tableName = tableName;
        conn = DriverManager.getConnection(DB_URL,USER,PASSWORD);
    }

    /**
     * Method called when an objects is requested .
     * @param OID is the code (by whom the object is identified in the system)of the object which is requested.
     * @return the object
     */
    @Override
    public synchronized Object get(String OID) throws SQLException{
        Object obj = getObjectFromCache(OID);
        if(obj == null){
            obj = getObjectFromTable(OID);
            updateCache(OID, obj);
        }
        return obj;
    }

    protected abstract Object getObjectFromTable (String OID) throws SQLException;
    protected abstract Object getObjectFromCache(String OID);
    protected abstract void updateCache(String OID,Object obj);

}
