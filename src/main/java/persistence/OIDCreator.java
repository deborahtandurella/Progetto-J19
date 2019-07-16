package persistence;

/**
 * A class which contains all the OID ( Object Identifier ) for the objects of the system.
 * It is implemented through Singleton pattern implementation.
 */
public class OIDCreator {
    private static OIDCreator instance = null;
    private int restaurantCode;
    private int menuEntryCode;
    private int critiquesCode;


    private OIDCreator() {
    }

    /**
     * 'Pattern Singleton Implementation'
     *
     * If the object has not already been instanced, it is instanced and it is returned.
     * @return instance(OIDCreator)
     */
    public static OIDCreator getInstance(){
        if(instance == null)
            instance = new OIDCreator();
        return instance;
    }

    public synchronized String getNewRestaurantCode() {
        restaurantCode++;
        return Integer.toString(restaurantCode);
    }

    public synchronized String getNewMenuEntryCode() {
        menuEntryCode++;
        return Integer.toString(menuEntryCode);
    }

    public synchronized int getNewCritiquesCode() {
        return ++critiquesCode;
    }

    public void setRestaurantCode(String restaurantCode) {

        this.restaurantCode = Integer.parseInt(restaurantCode);
    }

    public void setMenuEntryCode(String menuEntryCode) {
        this.menuEntryCode = Integer.parseInt(menuEntryCode);
    }

    public void setCritiquesCode(int critiquesCode) {
        this.critiquesCode = critiquesCode;
    }
}
