package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.MappingService;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Jakub Kremláček
 */
public class ExcursionFacadeImpl implements ExcursionFacade {
	
	@Inject
	MappingService mappingService;
	
	@Inject
	ExcursionService excursionService;
	
	public void create(ExcursionCreateDto excursion) {
		Excursion mapped = mappingService.mapTo(excursion, Excursion.class);
		excursionService.create(mapped);
	}

	public ExcursionDto findById(Long id) {
		return mappingService.mapTo(excursionService.findById(id), ExcursionDto.class);
	}

	public List<ExcursionDto> findAll() {
		return mappingService.mapTo(excursionService.findAll(), ExcursionDto.class);
	}

	public List<ExcursionDto> findByName(String name) {
        return mappingService.mapTo(excursionService.findByName(name), ExcursionDto.class);
	}

	public List<ExcursionDto> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
		return mappingService.mapTo(excursionService.findByPrice(minPrice, maxPrice), ExcursionDto.class);
	}

	public List<ExcursionDto> findByDate(Date dateFrom, Date dateTo) {
		 return mappingService.mapTo(excursionService.findByDate(dateFrom, dateTo), ExcursionDto.class);
	}

	public List<ExcursionDto> findByDestination(String destination) {
		return mappingService.mapTo(excursionService.findByDestination(destination), ExcursionDto.class);
	}

	public List<ExcursionDto> findByDuration(Date dateFrom, Date dateTo) {
		return mappingService.mapTo(excursionService.findByDuration(dateFrom, dateTo), ExcursionDto.class);
	}

	public List<ExcursionDto> findByTrip(TripDto trip) {
		Trip mapped = mappingService.mapTo(trip, Trip.class);
        return mappingService.mapTo(excursionService.findByTrip(mapped), ExcursionDto.class);
	}
}
