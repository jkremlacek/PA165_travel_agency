package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.User;
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

    private void validateAttributes(Reservation reservation) {
        if(reservation == null)
            throw new NullPointerException("Reservation should not be null");
        if(reservation.getUser() == null)
            throw new ValidationException("User attribute should not be null");
        if(reservation.getTrip() == null)
            throw new ValidationException("Trip attribute should not be null");
    }

    @Override
    public Long create(Reservation reservation) {
        validateAttributes(reservation);
        em.persist(reservation);
        //em.flush();
        return reservation.getId();
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
        em.remove(findById(reservation.getId()));
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
    public List<Reservation> findByUser(User user) {
        if (user == null)
            throw new NullPointerException("User can not be null");
        return em.createQuery("SELECT r FROM Reservation r WHERE r.user=:user", Reservation.class)
                .setParameter("user", user)
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
