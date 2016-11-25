package cz.muni.fi.pa165.travelagency.service;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created on 16.11.2016.
 *
 * @author Martin Salata
 */
@Service
public class MappingServiceImpl implements MappingService {

    @Inject
    private Mapper dozer;

    @Override
    public <T> List<T> mapTo(Collection<?> objects, Class<T> mapToClass) {
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
