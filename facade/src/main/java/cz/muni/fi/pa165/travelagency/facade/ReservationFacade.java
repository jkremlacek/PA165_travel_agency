package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;

import java.util.Set;

/**
 * Created on 13.11.2016.
 *
 * @author Martin Salata
 */
public interface ReservationFacade {

    void create(ReservationCreateDto reservation);

    ReservationDto findById(Long id);

    Set<ReservationDto> findAll();

    Set<ReservationDto> findByUser(UserDto user);

    Set<ReservationDto> findByTrip(TripDto trip);
}
