package persistence;

/**
 * A class which contains all the OID ( Object Identifier ) for the objects of the system.
 * It is implemented through Singleton pattern implementation.
 */
public class OIDCreator {
    private static OIDCreator instance = null;
    private int restaurantCode;
    private int menuEntryCode;
    private int criticCode;
    private int critiquesCode;

    private OIDCreator() {
    }

    public static OIDCreator getInstance(){
        if(instance == null)
            instance = new OIDCreator();
        return instance;
    }

    public int getRestaurantCode() {
        return restaurantCode;
    }

    public int getMenuEntryCode() {
        return menuEntryCode;
    }

    public int getCriticCode() {
        return criticCode;
    }

    public int getCritiquesCode() {
        return critiquesCode;
    }

    public void setRestaurantCode(int restaurantCode) {
        this.restaurantCode = restaurantCode;
    }

    public void setMenuEntryCode(int menuEntryCode) {
        this.menuEntryCode = menuEntryCode;
    }

    public void setCriticCode(int criticCode) {
        this.criticCode = criticCode;
    }

    public void setCritiquesCode(int critiquesCode) {
        this.critiquesCode = critiquesCode;
    }
}
