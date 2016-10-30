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
     * Persist an entity.
     * 
     * @param entity object to create
     */
    public void create(E entity);
    
    /**
     * 
     * Update an entity.
     * 
     * @param entity object to update
     */
    public void update(E entity);
    
    /**
     * Delete an entity.
     * 
     * @param entity object to delete
     */
    public void delete(E entity);
    
    /**
     * 
     * Find an entity by her unique id number.
     * 
     * @param id number
     * @return an entity with entered id or null when entity doesn't exist
     */
    public E findById(K id);
    
    /**
     * 
     * Method for find all entities.
     * 
     * @return entity objects as List
     */
    public List<E> findAll();
    
}
