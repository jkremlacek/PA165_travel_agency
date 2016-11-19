package cz.muni.fi.pa165.travelagency.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created on 16.11.2016.
 *
 * @author Martin Salata
 */
public interface MappingService {
    <T> Set<T> mapTo(Collection<?> objects, Class<T> mapToClass);
    
    <T> List<T> mapTo(Class<T> mapToClass,Collection<?> objects);

    <T> T mapTo(Object u, Class<T> mapToClass);
}
