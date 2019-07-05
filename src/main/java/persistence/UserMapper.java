package persistence;

import application.database_exception.InvalidUsernameException;
import application.user.User;
import application.user.UserType;
import application.database_exception.InvalidUsernameException;
import sun.nio.cs.ArrayEncoder;

import java.sql.PreparedStatement;
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
        super("users");
        this.user = new HashSet<>();
    }

    /**
     * Method which returns the user identified by the key username from the table USERS
     *
     * @param OID is the key of the object
     * @return
     * @throws SQLException
     */
    @Override
    protected Object getObjectFromTable(String OID) throws SQLException {
        PreparedStatement stm = conn.prepareStatement("SELECT * from "+super.tableName+" where USERNAME = ?");
        stm.setString(1,OID);
        ResultSet rs = stm.executeQuery();
        if (!rs.isBeforeFirst())
            throw new InvalidUsernameException("username inesistente");
        String [] tempCredential = new String[4];
        if(rs.next()) {
            for (int i=0; i<tempCredential.length; i++){
                tempCredential[i] = rs.getString(i+1);
            }
        }
        User user = new User(tempCredential, UserType.valueOf(rs.getString(5)));
        return user;
    }

    /**
     * Method which returns the user identified by the key username from thr cache
     *
     * @param OID the key of the object
     * @return
     */
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
        this.user.add((User) obj);
    }

    @Override
    public void put(String OID, Object obj) {
        //todo implements writing in the database the new user
    }

    public void signUpUser(User user){
        updateCache(user.getUsername(), user);
        put(user.getUsername(), user);
    }

}
