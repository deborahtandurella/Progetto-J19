package persistence;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface
 */
public interface IMapper {

     String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7297779";
     String USER = "sql7297779";
     String PASSWORD = "fK6Z9ygksS";


    Object  get(String OID) throws SQLException;
    void put(String OID, Object obj) throws SQLException;
    void updateTable(String OID,Object obj) throws SQLException;

}
