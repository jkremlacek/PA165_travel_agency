package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jakub Kremláček
 */
public interface ExcursionFacade {
	
    /**
     * Creates a new excursion
     * @param excursion DTO for excursion
     */
    void create(ExcursionCreateDto excursion);
	
    /**
     * Updates existing excursion
     * @param excursion DTO for excursion
     */
    void update(ExcursionDto excursion);
    
    /**
     * Removes existing excursion
     * @param excursion DTO for excursion
     */
    void delete(ExcursionDto excursion);

    /**
     * Finds a excursion with given ID
     * @param id ID of excursion
     * @return excursion DTO
     */
    ExcursionDto findById(Long id);

    /**
     * Finds all excursions
     * @return list of excursion DTOs
     */
    List<ExcursionDto> findAll();
	
    /**
     * Finds all excursions with given name
     * @param name
     * @return list of excursion DTOs with given name
     */
    List<ExcursionDto> findByName(String name);
    
    /**
     * Find excursions in given price interval. Border values are included.
     * @param minPrice is amount of money from, value is included into interval.
     * @param maxPrice is amount of money to, value is included into interval.
     * @return excursion DTOs in given price interval as List
     */
    List<ExcursionDto> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Find excursions in given time interval. Border values are included.
     * @param dateFrom start date of interval. Included into interval.
     * @param dateTo end date of interval. Included into interval.
     * @return excursion DTOs in given time interval as List
     */
    List<ExcursionDto> findByDate(Date dateFrom, Date dateTo); 
    
    /**
     * Find excursions with given destination
     * @param destination destination of excursion DTO to find
     * @return excursion DTOs with given destination
     */
    List<ExcursionDto> findByDestination(String destination);
    
    /**
     * Find excursions with duration in given time interval. Border values are included.
     * @param dateFrom start date of interval. Included into interval.
     * @param dateTo end date of interval. Included into interval.
     * @return excursion DTOs in given duration time interval as List
     */
    List<ExcursionDto> findByDuration(Date dateFrom, Date dateTo);
    
    /**
     * Find excursions a given trip
     * @param trip trip DTO to find excursion
     * @return excursion with the  given trip as List of user DTOs
     */
    List<ExcursionDto> findByTrip(TripDto trip);
}
