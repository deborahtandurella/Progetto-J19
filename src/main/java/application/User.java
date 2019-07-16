package application;

/**
 * A class used to represent the user registered in the system
 */
public class User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private UserType type;

    /**
     * Create an user of the application
     *
     * @param credential, which contains username, password, name and surname
     */
    public User(String [] credential, UserType type) {
        this.username = credential[0];
        this.password = credential[1];
        this.name = credential[2];
        this.surname = credential[3];
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public UserType getType() {
        return type;
    }
}
