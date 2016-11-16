package cz.muni.fi.pa165.travelagency.service.facade;

import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.CustomerDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.MappingService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;

/**
 * Created on 13.11.2016.
 *
 * @author Martin Salata
 */
@Service
@Transactional
public class ReservationFacadeImpl implements ReservationFacade{

    @Inject
    MappingService mappingService;

    @Inject
    ReservationService reservationService;

    public void create(ReservationCreateDto reservation) {
        Reservation mapped = mappingService.mapTo(reservation, Reservation.class);
        reservationService.create(mapped);
    }

    public ReservationDto findById(Long id) {
        return mappingService.mapTo(reservationService.findById(id), ReservationDto.class);
    }

    public Set<ReservationDto> findAll() {
        return mappingService.mapTo(reservationService.findAll(), ReservationDto.class);
    }

    public Set<ReservationDto> findByCustomer(CustomerDto customer) {
        Customer mapped = mappingService.mapTo(customer, Customer.class);
        return mappingService.mapTo(reservationService.findByCustomer(mapped), ReservationDto.class);
    }

    public Set<ReservationDto> findByTrip(TripDto trip) {
        Trip mapped = mappingService.mapTo(trip, Trip.class);
        return mappingService.mapTo(reservationService.findByTrip(mapped), ReservationDto.class);
    }
}
