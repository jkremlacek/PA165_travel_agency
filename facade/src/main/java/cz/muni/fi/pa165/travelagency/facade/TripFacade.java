package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public interface TripFacade {
    
    /**
     * Store a new trip
     * @param trip trip DTO to be stored
     */
    void create(TripCreateDto trip);
    
    /**
     * Update an existing trip
     * @param trip trip DTO to be updated
     */
    void update(TripDto trip);
    
    /**
     * Delete an existing trip
     * @param trip trip DTO to be deleted
     */
    void delete(TripDto trip);
    
    /**
     * Find a trip with given id
     * @param id id of trip
     * @return a trip DTO with given id
     */
    TripDto findById(Long id);
    
    /**
     * Find all trips
     * @return all trip DTOs as List
     */
    List<TripDto> findAll();
    
    /**
     * Find trips with given name
     * @param name name of trip DTO to find
     * @return trip DTOs with given name as List
     */
    List<TripDto> findByName(String name);
    
    /**
     * Find trips in given time interval. Border values are included.
     * @param from start date of interval. Included into interval.
     * @param to end date of interval. Included into interval.
     * @return trip DTOs in given time interval as List
     */
    List<TripDto> findByDate(Date from, Date to);
    
    /**
     * Find trips with given destination
     * @param destination destination of trip DTO to find
     * @return trip DTOs with given destination
     */
    List<TripDto> findByDestination(String destination);
    
    /**
     * Find trips in given price interval. Border values are included.
     * @param minPrice is amount of money from, value is included into interval.
     * @param maxPrice is amount of money to, value is included into interval.
     * @return trip DTOs in given price interval as List
     */
    List<TripDto> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Find trips with available capacity equal or greater than given argument
     * @param capacity is number of desired capacity
     * @return trip DTOs with available capacity as List
     */
    List<TripDto> findByAvailableCapacity(Integer capacity);
    
    /**
     * Find trips with option of given excursion
     * @param excursion
     * @return trip DTOs with given excursion as List
     */
    List<TripDto> findByExcursion(ExcursionDto excursion);
    
    /**
     * Find trips with at least one free place of capacity
     * @return trip DTOs with free capacity as List
     */
    List<TripDto> findWithFreeCapacity();
    
    /**
     * Find trips which will start in next 31 days
     * @return trip DTOs in next 31 days as List
     */
    List<TripDto> findTripsNextMonth();
    
    /**
     * Find people with reservation a given trip
     * @param trip trip DTO to find people
     * @return people with the booked given trip as List of user DTOs
     */
    List<UserDto> findTripParticipants(TripDto trip);
    
    /**
     * Find out if given trip has available capacity at least for one reservation
     * @param trip trip DTO for query if has available capacity
     * @return true when one or more places are available, false otherwise
     */
    boolean hasTripAvailableCapacity(TripDto trip);
    
}
