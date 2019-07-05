package persistence;

import application.Restaurant;

import java.sql.SQLException;
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

    public Map<String, Restaurant> getAllRestaurants(){
        return ((RestaurantsMapper)mapper.get(RestaurantsMapper.class)).getRestaurant();
    }
}
