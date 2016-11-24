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
	
	void create(Excursion excursion);
	
	void update(Excursion excursion);

    void delete(Excursion excursion);

    Excursion findById(Long id);
	
	List<Excursion> findAll();
	
    List<Excursion> findByName(String name);
    
    List<Excursion> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<Excursion> findByDate(Date dateFrom, Date dateTo); 
    
    List<Excursion> findByDestination(String destination);
    
    List<Excursion> findByDuration(Date dateFrom, Date dateTo);
    
    List<Excursion> findByTrip(Trip trip);
}
