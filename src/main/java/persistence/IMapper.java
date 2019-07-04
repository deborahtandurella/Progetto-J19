package persistence;

import java.sql.SQLException;
import java.util.List;

/**
 * Interface
 */
public interface IMapper {

     String DB_URL = "jdbc:mysql://localhost:3306/clique_db";
     String USER = "cliqueAdmin";
     String PASSWORD = "cliquePassword";


    Object get(String OID) throws SQLException;
    void put(String OID, Object obj);
    //List<Object> getObjectList(String OID) throws SQLException;

}
