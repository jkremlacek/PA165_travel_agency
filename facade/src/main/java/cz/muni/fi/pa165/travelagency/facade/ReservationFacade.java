package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.CustomerDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;

import java.util.List;

/**
 * Created on 13.11.2016.
 *
 * @author Martin Salata
 */
public interface ReservationFacade {
    void create(ReservationDto reservation);

    ReservationDto findById(Long id);

    List<ReservationDto> findAll();

    List<ReservationDto> findByCustomer(CustomerDto customer);

    List<ReservationDto> findByTrip(TripDto trip);
}
