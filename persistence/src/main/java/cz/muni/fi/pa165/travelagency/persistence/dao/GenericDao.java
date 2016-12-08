package cz.muni.fi.pa165.travelagency.persistence.dao;

import java.util.List;

/**
 *
 * Generic DAO interface with shared operations for all classes
 * 
 * @author Josef Pavelec, jospavelec@gmail.com
 * @param <E> represents entity
 * @param <K> represents entity key (id)
 */
public interface GenericDao<E, K> {
    
    /**
     * Create an entity
     * @param entity
     * @return id of created entity
     */
    Long create(E entity);
    
    /**
     * 
     * Update an entity.
     * 
     * @param entity object to update
     */
    void update(E entity);
    
    /**
     * Delete an entity.
     * 
     * @param entity object to delete
     */
    void delete(E entity);
    
    /**
     * 
     * Find an entity by her unique id number.
     * 
     * @param id number
     * @return an entity with entered id or null when entity doesn't exist
     */
    E findById(K id);
    
    /**
     * 
     * Method for find all entities.
     * 
     * @return entity objects as List
     */
    List<E> findAll();
    
}
