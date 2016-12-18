package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created on 13.11.2016.
 *
 * @author Martin Salata
 */
public interface ReservationService {

    /**
     * Stores new reservation
     * @param reservation reservation to be stored
     * @return id of created reservation
     */
    Long create(Reservation reservation);

    /**
     * Updates existing reservation
     * @param reservation reservation to be updated
     */
    void update(Reservation reservation);

    /**
     * Deletes a reservation
     * @param reservation reservation to be deleted
     */
    void delete(Reservation reservation);

    /**
     * Finds a reservation with given ID
     * @param id ID of reservation
     * @return Reservation
     */
    Reservation findById(Long id);

    /**
     * Finds all reservations
     * @return list of reservations
     */
    List<Reservation> findAll();

    /**
     * Finds all reservations with given user
     * @param user
     * @return list of reservations with given user
     */
    List<Reservation> findByUser(User user);

    /**
     * Finds all reservations with given trip
     * @param trip
     * @return list of reservations with given trip
     */
    List<Reservation> findByTrip(Trip trip);

    /**
     * Adds an excursion to an existing reservation
     * @param reservationId ID of an existing reservation
     * @param excursion excursion to be added
     * @return reservation with updated attributes
     */
    Reservation addExcursion(Long reservationId, Excursion excursion);

    /**
     * Calculates total price of reservation (trip + all excursions)
     * @param reservationId ID of reservation
     * @return total price of reservation
     */
    BigDecimal getTotalPrice(Long reservationId);
    
}
