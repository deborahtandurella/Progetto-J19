package persistence;

import application.database_exception.InvalidUsernameException;
import application.user.User;
import application.user.UserType;
import application.database_exception.InvalidUsernameException;
import sun.nio.cs.ArrayEncoder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

/**
 * It is the mapper of the table "USERS"
 */
public class UserMapper extends AbstractPersistenceMapper{

    private HashSet<User> user;

    /**
     * Constructor of the class
     * @throws SQLException
     */
    public UserMapper() throws SQLException{
        super("USERS");
        this.user = new HashSet<>();
    }

    @Override
    protected Object getObjectFromTable(String OID) throws SQLException {
        Statement stm = super.conn.createStatement();
        ResultSet rs = stm.executeQuery("select * from "+super.tableName+" where USERTYPE = "+OID);
        if (!rs.isBeforeFirst())
            throw new InvalidUsernameException("username inesistente");
        String [] tempCredential = new String[4];
        if(!rs.next()) {
            tempCredential[0] = rs.getString(1);
            tempCredential[1] = rs.getString(2);
            tempCredential[2] = rs.getString(3);
            tempCredential[3] = rs.getString(4);
        }
        User user = new User(tempCredential, UserType.valueOf(rs.getString(5)));
        return user;
    }

    @Override
    protected Object getObjectFromCache(String OID) {
        for(User user : this.user){
            if(user.getUsername().equals(OID))
                return user;
        }
        return null;
    }

    @Override
    protected void updateCache(String OID, Object obj) {

    }

    @Override
    public void put(String OID, Object obj) {

    }

    public HashSet<User> getUser(){
        return this.user;
    }
}
