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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jakub Kremláček
 */
@Service
@Transactional
public class ExcursionFacadeImpl implements ExcursionFacade {
	
    @Inject
    private MappingService mappingService;
	
    @Inject
    private ExcursionService excursionService;
	
    @Override
    public Long create(ExcursionCreateDto excursion) {
        Excursion mapped = mappingService.mapTo(excursion, Excursion.class);
        return excursionService.create(mapped);
    }
	
    @Override
    public void update(ExcursionDto excursion) {
        Excursion mapped = mappingService.mapTo(excursion, Excursion.class);
        excursionService.update(mapped);
    }
    
    @Override
    public void delete(ExcursionDto excursion) {
        Excursion mapped = mappingService.mapTo(excursion, Excursion.class);
        excursionService.delete(mapped);
    }
	
    @Override
    public ExcursionDto findById(Long id) {
        return mappingService.mapTo(excursionService.findById(id), ExcursionDto.class);
    }
	
    @Override
    public List<ExcursionDto> findAll() {
        return mappingService.mapTo(excursionService.findAll(), ExcursionDto.class);
    }

    @Override
    public List<ExcursionDto> findByName(String name) {
        return mappingService.mapTo(excursionService.findByName(name), ExcursionDto.class);
    }

    @Override
    public List<ExcursionDto> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return mappingService.mapTo(excursionService.findByPrice(minPrice, maxPrice), ExcursionDto.class);
    }

    @Override
    public List<ExcursionDto> findByDate(Date dateFrom, Date dateTo) {
        return mappingService.mapTo(excursionService.findByDate(dateFrom, dateTo), ExcursionDto.class);
    }

    @Override
    public List<ExcursionDto> findByDestination(String destination) {
        return mappingService.mapTo(excursionService.findByDestination(destination), ExcursionDto.class);
    }

    @Override
    public List<ExcursionDto> findByDuration(Date dateFrom, Date dateTo) {
        return mappingService.mapTo(excursionService.findByDuration(dateFrom, dateTo), ExcursionDto.class);
    }

    @Override
    public List<ExcursionDto> findByTrip(TripDto trip) {
        Trip mapped = mappingService.mapTo(trip, Trip.class);
        return mappingService.mapTo(excursionService.findByTrip(mapped), ExcursionDto.class);
    }
}
