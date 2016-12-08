package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jakub Kremláček
 */
public interface ExcursionService {
	
	/**
     * Store a new excursion
     * @param excursion excursion to be stored
     */
    Long create(Excursion excursion);
    
	/**
     * Update an existing excursion
     * @param excursion excursion to be updated
     */
    void update(Excursion excursion);

	/**
     * Delete an existing excursion
     * @param excursion excursion to be deleted
     */
    void delete(Excursion excursion);

	/**
     * Find a excursion with given id
     * @param id id of excursion
     * @return a excursion with given id
     */
    Excursion findById(Long id);
	
	/**
     * Find all excursions
     * @return all excursions as List
     */
    List<Excursion> findAll();
	
	/**
     * Find excursions with given name
     * @param name name of excursion to find
     * @return excursions with given name as List
     */
    List<Excursion> findByName(String name);
    
	/**
     * Find excursions in given price interval
     * @param minPrice is amount of money from
     * @param maxPrice is amount of money to
     * @return excursions in given price interval as List
     */
    List<Excursion> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    
	/**
     * Find excursions in given time interval
     * @param dateFrom start date of interval
     * @param dateTo end date of interval
     * @return excursions in given time interval as List
     */
    List<Excursion> findByDate(Date dateFrom, Date dateTo); 
    
	/**
     * Find excursions with given destination
     * @param destination destination of excursion to find
     * @return excursions with given destination
     */
    List<Excursion> findByDestination(String destination);
    
	/**
     * Find excursions in given duration time interval
     * @param dateFrom start date of interval
     * @param dateTo end date of interval
     * @return excursions in given time interval as List
     */
    List<Excursion> findByDuration(Date dateFrom, Date dateTo);
    
	/**
     * Finds all excursions with given trip
     * @param trip
     * @return list of excursions with given trip
     */
    List<Excursion> findByTrip(Trip trip);
}
