package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;

import java.util.List;

/**
 * Created on 13.11.2016.
 *
 * @author Martin Salata
 */
public interface ReservationService {

    void create(Reservation reservation);

    void delete(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findAll();

    List<Reservation> findByUser(User user);

    List<Reservation> findByTrip(Trip trip);

    Reservation addExcursion(Long reservationId, Excursion excursion);
}
