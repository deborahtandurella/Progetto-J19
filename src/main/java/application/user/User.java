package application.user;

/**
 * A class used to represent the user registered in the system
 */
public class User {
    private String username;
    private String password;
    private String name;
    private String surname;

    /**
     * Create an user of the application
     *
     * @param username
     * @param password
     * @param name
     * @param surname
     */
    public User(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
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

}
