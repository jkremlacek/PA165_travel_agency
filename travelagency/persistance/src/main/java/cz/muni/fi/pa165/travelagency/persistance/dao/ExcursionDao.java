package cz.muni.fi.pa165.travelagency.persistance.dao;

import cz.muni.fi.pa165.travelagency.persistance.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistance.entity.Trip;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Data access object interface for Excursion entity
 * @author Katerina Caletkova
 */
public interface ExcursionDao extends GenericDao<Excursion, Long> {

    /**
     * Method find all Excursions with this name
     * @param name to find
     * @return list of excursion with this name
     */
    public List<Excursion> findByName(String name);
    
    /**
     * Method find all Excursions with price value between minPrice and maxPrice
     * @param minPrice minimum value of price to find
     * @param maxPrice maximum value of price to find
     * @return list of excursion with price value between minPrice and maxPrice
     */
    public List<Excursion> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Method find all Excursions with date value between dateFrom and dateTo
     * @param dateFrom minimum value of date to find
     * @param dateTo maximum value of date to find
     * @return list of excursion with date value between dateFrom and dateTo
     */
    public List<Excursion> findByDate(Date dateFrom, Date dateTo); 
    
    /**
     * Method find all Excursions with this destination
     * @param destination to find
     * @return list of excursion with this destination
     */
    public List<Excursion> findByDestination(String destination);
    
    /**
     * Method find all Excursions with duration value between dateFrom and dateTo
     * @param dateFrom minimum value of date to find
     * @param dateTo maximum value of date to find
     * @return list of excursion with duration value between dateFrom and dateTo
     */
    public List<Excursion> findByDuration(Date dateFrom, Date dateTo);
    
    /**
     * Method find all Excursions with this trip
     * @param trip to find
     * @return list of excursion with this trip
     */
    public List<Excursion> findByTrip(Trip trip);
}
