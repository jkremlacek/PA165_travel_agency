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
	
    void create(ExcursionCreateDto excursion);

    ExcursionDto findById(Long id);

    List<ExcursionDto> findAll();
	
    List<ExcursionDto> findByName(String name);
    
    List<ExcursionDto> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<ExcursionDto> findByDate(Date dateFrom, Date dateTo); 
    
    List<ExcursionDto> findByDestination(String destination);
    
    List<ExcursionDto> findByDuration(Date dateFrom, Date dateTo);
    
    List<ExcursionDto> findByTrip(TripDto trip);
}
