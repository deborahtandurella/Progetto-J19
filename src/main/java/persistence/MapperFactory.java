package persistence;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class which is the Factory of the mappers of the Persistence Framework.
 * It is implemented through Singleton pattern implementation.
 */
public class MapperFactory {
    private static MapperFactory instance = null;
    private Map<Class,IMapper> mappers;

    /**
     *Initialize a map which contains all the mappers( key : the Class Object of the mapper,
     * and value : is the instance of the mapper itself.
     * @throws SQLException
     */
    private MapperFactory()throws SQLException {
        this.mappers  = new HashMap<>();
        OverviewMapper om = new OverviewMapper();
        MenuEntryMapper mem = new MenuEntryMapper();
        this.mappers.put(OverviewMapper.class, om);
        this.mappers.put(MenuEntryMapper.class, mem);
        this.mappers.put(RestaurantsMapper.class, new RestaurantsMapper(om,mem));
    }
    /**
     * 'Pattern Singleton Implementation'
     *
     * If the object has not already been instanced, it is instanced and it is returned.
     * @return instance(MapperFactory)
     */
    public MapperFactory getInstance()throws SQLException {
        if(instance == null)
            instance = new MapperFactory();
        return instance;
    }

    public Map<Class, IMapper> getMappers() {
        return mappers;
    }
}
