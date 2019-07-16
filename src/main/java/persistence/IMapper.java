package persistence;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface for Mapper Class
 */
public interface IMapper {

     String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7298643";
     String USER = "sql7298643";
     String PASSWORD = "kJ1S9yUPZ5";

    /**
     * Method which returns an Object for the table belonging to the Mapper
     * @param OID is the code of the object
     * @return the object requested
     * @throws SQLException
     */
    Object  get(String OID) throws SQLException;

    /**
     * Method which insert a new Object in the table belonging to he Mapper
     * @param OID is the code of the Object
     * @param obj is the new Object which has to be inserted
     * @throws SQLException
     */
    void put(String OID, Object obj) throws SQLException;

    /**
     * Method which modifies the information of the object in the table belonging to the Mapper
     * @param OID is the code of the Object
     * @param obj is the Object whose information has to be modified
     * @throws SQLException
     */
    void updateTable(String OID,Object obj) throws SQLException;

}
