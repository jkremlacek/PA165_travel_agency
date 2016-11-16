package cz.muni.fi.pa165.travelagency.service;

import java.util.Collection;
import java.util.Set;

/**
 * Created on 16.11.2016.
 *
 * @author Martin Salata
 */
public interface MappingService {
    <T> Set<T> mapTo(Collection<?> objects, Class<T> mapToClass);

    <T> T mapTo(Object u, Class<T> mapToClass);
}
