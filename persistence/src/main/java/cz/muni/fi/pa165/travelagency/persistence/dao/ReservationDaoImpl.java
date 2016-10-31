package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidationException;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * Created on 20.10.2016.
 *
 * @author Martin Salata
 */
@Repository
public class ReservationDaoImpl implements ReservationDao {

    @PersistenceContext
    private EntityManager em;

    private void validateAttributes(Reservation reservation) throws ValidationException, NullPointerException {
        if(reservation == null)
            throw new NullPointerException("Reservation should not be null");
        if(reservation.getCustomer() == null)
            throw new ValidationException("Customer attribute should not be null");
        if(reservation.getTrip() == null)
            throw new ValidationException("Trip attribute should not be null");
    }

    @Override
    public void create(Reservation reservation) {
        validateAttributes(reservation);
        em.persist(reservation);
    }

    @Override
    public void update(Reservation reservation) {
        validateAttributes(reservation);
        em.merge(reservation);
    }

    @Override
    public void delete(Reservation reservation) {
        if (reservation == null)
            throw new NullPointerException("Reservation can not be null");
        em.remove(reservation);
    }

    @Override
    public Reservation findById(Long id) {
        if (id == null)
            throw new NullPointerException("ID can not be null");
        return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAll() {
        return em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
    }

    @Override
    public List<Reservation> findByCustomer(Customer customer) {
        if (customer == null)
            throw new NullPointerException("Customer can not be null");
        return em.createQuery("SELECT r FROM Reservation r WHERE r.customer=:customer", Reservation.class)
                .setParameter("customer", customer)
                .getResultList();
    }

    @Override
    public List<Reservation> findByTrip(Trip trip) {
        if (trip == null)
            throw new NullPointerException("Trip can not be null");
        return em.createQuery("SELECT r FROM Reservation r WHERE r.trip=:trip", Reservation.class)
                .setParameter("trip", trip)
                .getResultList();
    }
}
