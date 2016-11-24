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

    /**
     * Creates a new reservation
     * @param reservation DTO for reservation
     */
    void create(ReservationCreateDto reservation);

    /**
     * Updates existing reservation
     * @param reservationDto DTO for reservation
     */
    void update(ReservationDto reservationDto);

    /**
     * Finds a reservation with given ID
     * @param id ID of reservation
     * @return Reservation DTO
     */
    ReservationDto findById(Long id);

    /**
     * Finds all reservations
     * @return list of reservation DTOs
     */
    List<ReservationDto> findAll();

    /**
     * Finds all reservations with given user
     * @param user
     * @return list of reservation DTOs with given user
     */
    List<ReservationDto> findByUser(UserDto user);

    /**
     * Finds all reservations with given trip
     * @param trip
     * @return list of reservation DTOs with given trip
     */
    List<ReservationDto> findByTrip(TripDto trip);

    /**
     * Calculates total price of reservation (trip + all excursions)
     * @param id ID of reservation
     * @return total price of reservation
     */
    BigDecimal getTotalPrice(Long id);
}
