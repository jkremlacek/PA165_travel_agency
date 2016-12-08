package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public interface TripService {
    
    /**
     * Store a new trip
     * @param trip trip to be stored
     * @return new trip
     */
    Long create(Trip trip);
    
    /**
     * Update an existing trip
     * @param trip trip to be updated
     */
    void update(Trip trip);
    
    /**
     * Delete an existing trip
     * @param trip trip to be deleted
     */
    void delete(Trip trip);
    
    /**
     * Find a trip with given id
     * @param id id of trip
     * @return a trip with given id
     */
    Trip findById(Long id);
    
    /**
     * Find all trips
     * @return all trips as List
     */
    List<Trip> findAll();
    
    /**
     * Find trips with given name
     * @param name name of trip to find
     * @return trips with given name as List
     */
    List<Trip> findByName(String name);
    
    /**
     * Find trips in given time interval
     * @param from start date of interval
     * @param to end date of interval
     * @return trips in given time interval as List
     */
    List<Trip> findByDate(Date from, Date to);
    
    /**
     * Find trips with given destination
     * @param destination destination of trip to find
     * @return trips with given destination
     */
    List<Trip> findByDestination(String destination);
    
    /**
     * Find trips in given price interval
     * @param minPrice is amount of money from
     * @param maxPrice is amount of money to
     * @return trips in given price interval as List
     */
    List<Trip> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Find trips with available capacity equal or greater than given argument
     * @param capacity is number of desired capacity
     * @return trips with available capacity as List
     */
    List<Trip> findByAvailableCapacity(Integer capacity);
    
    /**
     * Find trips with option of given excursion
     * @param excursion
     * @return trips with given excursion as List
     */
    List<Trip> findByExcursion(Excursion excursion);
    
    /**
     * Find trips with at least one free place
     * @return trips with free capacity
     */
    List<Trip> findWithFreeCapacity();
    
    /**
     * Find trips which will start maximum in given days
     * @param countOfDays
     * @return trips in given next days as List
     */
    List<Trip> findTripsInNextDays(int countOfDays);
    
    /**
     * Find people with reservation a given trip
     * @param trip trip to find people
     * @return people with the booked given trip as List of User
     */
    List<User> findTripParticipants(Trip trip);
    
    /**
     * Find out if given trip has available capacity at least for one reservation
     * @param trip trip for query if has available capacity
     * @return true when one or more places are available, false otherwise
     */
    boolean hasTripAvailableCapacity(Trip trip);
    
    
}
