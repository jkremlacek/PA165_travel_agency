package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import java.util.Set;

/**
 *
 * @author Jakub Kremláček
 */
public interface ExcursionFacade {
	
    void create(ExcursionCreateDto excursion);

    ExcursionDto findById(Long id);

    Set<ExcursionDto> findAll();
	
    Set<ExcursionDto> findByName(String name);
    
    Set<ExcursionDto> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    
    Set<ExcursionDto> findByDate(Date dateFrom, Date dateTo); 
    
    Set<ExcursionDto> findByDestination(String destination);
    
    Set<ExcursionDto> findByDuration(Date dateFrom, Date dateTo);
    
    Set<ExcursionDto> findByTrip(TripDto trip);
}
