package cz.muni.fi.pa165.travelagency.service;

import java.util.Collection;
import java.util.List;

/**
 * Created on 16.11.2016.
 *
 * @author Martin Salata
 */
public interface MappingService {
    /**
     * Maps a collection of objects to objects of given type
     * @param objects objects to be mapped
     * @param mapToClass class to be mapped to
     * @param <T>
     * @return List of objects of type mapToClass
     */
    <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    /**
     * Maps given object to an object of given type
     * @param u object to be mapped
     * @param mapToClass class to be mapped to
     * @param <T>
     * @return Object of type mapToClass
     */
    <T> T mapTo(Object u, Class<T> mapToClass);
}
