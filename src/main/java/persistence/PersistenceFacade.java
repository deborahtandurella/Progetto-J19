package persistence;

import application.Critique;
import application.MenuEntry;
import application.Restaurant;
import application.RestaurantOverview;
import application.User;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;

/**
 * A class which is the Facade Controller of the package persistence.
 * It is implemented through Singleton pattern implementation.
 */
public class PersistenceFacade {
    private static PersistenceFacade instance = null;
    private Map<Class, IMapper> mapper;

    /**
     * Instanced the PersistenceFacade.
     * The Map is instanced by its Factory.
     */
    private PersistenceFacade() {
        try {
            this.mapper = MapperFactory.getInstance().getMappers();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    /**
     * 'Pattern Singleton Implementation'
     *
     * If the object has not already been instanced, it is instanced and it is returned.
     * @return instance(PersistenceFacade)
     */
    public static PersistenceFacade getInstance(){
        if(instance == null)
            instance = new PersistenceFacade();
        return instance;
    }

    /**
     * It gets all the restaurants registered to the system
     * @return Map (key : code of the restaurant, value : the restaurant itself)
     */
    public Map<String, Restaurant> getAllRestaurants(){
        return ((RestaurantsMapper)mapper.get(RestaurantsMapper.class)).getRestaurant();
    }

    /**
     * Method called when a new Restaurant is added to the system.
     * It updates both the cache and the database.
     * @param OID is the code of the restaurant
     * @param restaurant is the Restaurant which has to be added.
     */
    public void addRestaurant(String OID, Restaurant restaurant)throws SQLException{
        RestaurantOverview ro = new RestaurantOverview();
        restaurant.setOverview(ro);
        mapper.get(RestaurantsMapper.class).put(OID,restaurant);
        mapper.get(OverviewMapper.class).put(OID,ro);
    }

    public Object get(String OID, Class klass) throws SQLException{
        return this.mapper.get(klass).get(OID);
    }

    /**
     * Method  called when a new MenuEntry is added to a Restaurant.
     * It updates the database.
     * @param me the MenuEntry which has to be added
     */
    public void addMenuEntry(MenuEntry me)throws SQLException{
        mapper.get(MenuEntryMapper.class).put(me.getCod(), me);
    }

    /**
     * Method used to update the database and the cache when a new user signs up
     *
     * @param user, the new user registered
     */
    public void signUpNewUser(User user){
        ((UserMapper)mapper.get(UserMapper.class)).signUpUser(user);
    }

    /**
     * It gets all the critiques saved in the system.
     *
     * @return critiques saved in the mapper CritiquesMapper
     */
    public HashSet<Critique> getCritiques(){
        return ((CritiquesMapper)mapper.get(CritiquesMapper.class)).getCritiques();
    }

    /**
     * Method used to add a new critique in the database and in the cache memory of CritiquesMapper
     *
     * @param critique the new critique
     * @return
     */
    public void addNewCritique(Critique critique)throws SQLException{
        mapper.get(CritiquesMapper.class).put(
                Integer.toString(OIDCreator.getInstance().getNewCritiquesCode()),
                critique);
    }

    /**
     * Method called to update an Object in a table.
     * @param klass is a Class which is used to determine which Mapper has to be used
     * @param obj is the Object which has been modified
     * @param OID is the code of the Object
     * @throws SQLException
     */
    public void updateTable(Class klass,Object obj,String OID)throws SQLException{
        mapper.get(klass).updateTable(OID,obj);
    }

    /**
     * Method called when a RestaurantOwner wants to remove a dish from a menu of one of is restaurants
     * @param OID is the code of the dish
     * @param restaurantOID is the code of the Restaurant whose menu contains the dish
     * @throws SQLException
     */
    public void removeDish(String OID, String restaurantOID) throws SQLException {
        ((MenuEntryMapper)mapper.get(MenuEntryMapper.class)).remove(OID);
        ((Restaurant)mapper.get(RestaurantsMapper.class).get(restaurantOID)).removeDishFromMenu(OID);
    }
}
