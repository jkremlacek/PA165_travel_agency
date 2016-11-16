package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@Service // TODO don't know it is necessary
public interface TripService {
    
    void createTrip(Trip trip);
    void updateTrip(Trip trip);
    void deleteTrip(Trip trip);
    
    Trip getById(Long id);
    List<Trip> getAll();
    
    List<Trip> findByName(String name);
    List<Trip> findByDate(Date from, Date to);
    List<Trip> findByDestination(String destination);
    List<Trip> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    List<Trip> findByCapacity(Integer capacity);
    List<Trip> findByExcursion(Excursion excursion);
    
    List<Trip> findWithFreeCapacity();
    List<Trip> findTripsNextMonth();
    
    
}
