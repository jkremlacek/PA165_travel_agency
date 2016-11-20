package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created on 13.11.2016.
 *
 * @author Martin Salata
 */
public interface ReservationFacade {

    void create(ReservationCreateDto reservation);

    ReservationDto findById(Long id);

    List<ReservationDto> findAll();

    List<ReservationDto> findByUser(UserDto user);

    List<ReservationDto> findByTrip(TripDto trip);

    BigDecimal getTotalPrice(Long id);
}
