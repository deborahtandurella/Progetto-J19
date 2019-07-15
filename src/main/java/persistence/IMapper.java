package persistence;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface
 */
public interface IMapper {

     String DB_URL = "jdbc:mysql://sql7.freesqldatabase.com/sql7298643";
     String USER = "sql7298643";
     String PASSWORD = "kJ1S9yUPZ5";


    Object  get(String OID) throws SQLException;
    void put(String OID, Object obj) throws SQLException;
    void updateTable(String OID,Object obj) throws SQLException;

}
