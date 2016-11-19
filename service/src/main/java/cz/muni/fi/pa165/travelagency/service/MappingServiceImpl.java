package cz.muni.fi.pa165.travelagency.service;

import java.util.ArrayList;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created on 16.11.2016.
 *
 * @author Martin Salata
 */
@Service
public class MappingServiceImpl implements MappingService {

    @Inject
    Mapper dozer;

    @Override
    public <T> Set<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
        Set<T> mappedCollection = new HashSet<>();
        for (Object object : objects) {
            if (object == null) continue;
            mappedCollection.add(this.mapTo(object, mapToClass));
        }
        return mappedCollection;
    }
    
    public <T> List<T> mapTo(Class<T> mapToClass,Collection<?> objects) {
        List<T> mappedCollection = new ArrayList<>();
        for (Object object : objects) {
            if (object == null) continue;
            mappedCollection.add(this.mapTo(object, mapToClass));
        }
        return mappedCollection;
    }

    @Override
    public <T> T mapTo(Object u, Class<T> mapToClass) {
        if(u == null) return null;
        return dozer.map(u, mapToClass);
    }
}
