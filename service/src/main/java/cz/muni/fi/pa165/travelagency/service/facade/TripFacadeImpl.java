package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.MappingService;
import cz.muni.fi.pa165.travelagency.service.TripService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@Service
@Transactional
public class TripFacadeImpl implements TripFacade {
    
    @Inject
    private MappingService mappingService;
    
    @Inject
    private TripService tripService;

    @Override
    public Long create(TripCreateDto trip) {
        Trip mapped = mappingService.mapTo(trip, Trip.class);
        return tripService.create(mapped);
    }

    @Override
    public void update(TripDto trip) {
        tripService.update(mappingService.mapTo(trip, Trip.class));
    }

    @Override
    public void delete(TripDto trip) {
        tripService.delete(mappingService.mapTo(trip, Trip.class));
    }

    @Override
    public TripDto findById(Long id) {
        Trip trip = tripService.findById(id);
        if (trip == null) {
            return null;
        }
        return mappingService.mapTo(trip, TripDto.class);
    }

    @Override
    public List<TripDto> findAll() {
        return mappingService.mapTo(tripService.findAll(), TripDto.class);
    }

    @Override
    public List<TripDto> findByName(String name) {
        return mappingService.mapTo(tripService.findByName(name), TripDto.class);
    }

    @Override
    public List<TripDto> findByDate(Date from, Date to) {
        return mappingService.mapTo(tripService.findByDate(from, to), TripDto.class);
    }

    @Override
    public List<TripDto> findByDestination(String destination) {
        return mappingService.mapTo(tripService.findByDestination(destination), TripDto.class);
    }

    @Override
    public List<TripDto> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return mappingService.mapTo(tripService.findByPrice(minPrice, maxPrice), TripDto.class);
    }

    @Override
    public List<TripDto> findByAvailableCapacity(Integer capacity) {
        return mappingService.mapTo(tripService.findByAvailableCapacity(capacity), TripDto.class);
    }

    @Override
    public List<TripDto> findByExcursion(ExcursionDto excursion) {
        Excursion mapped = mappingService.mapTo(excursion, Excursion.class);
        return mappingService.mapTo(tripService.findByExcursion(mapped), TripDto.class);
    }

    @Override
    public List<TripDto> findWithFreeCapacity() {
        return mappingService.mapTo(tripService.findWithFreeCapacity(), TripDto.class);
    }

    @Override
    public List<TripDto> findTripsInNextDays(int count) {
        return mappingService.mapTo(tripService.findTripsInNextDays(count), TripDto.class);
    }

    @Override
    public List<UserDto> findTripParticipants(TripDto trip) {
        Trip mapped = mappingService.mapTo(trip, Trip.class);
        return mappingService.mapTo(tripService.findTripParticipants(mapped), UserDto.class);
    }

    @Override
    public boolean hasTripAvailableCapacity(TripDto trip) {
        return tripService.hasTripAvailableCapacity(mappingService.mapTo(trip, Trip.class));
    }

}
