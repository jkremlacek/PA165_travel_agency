package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    EntityManager em;

    @Override
    public void create(Reservation entity) {
        em.persist(entity);
    }

    @Override
    public void update(Reservation entity) {
        em.merge(entity);
    }

    @Override
    public void delete(Reservation entity) {
        em.remove(entity);
    }

    @Override
    public Reservation findById(Long id) {
        return em.find(Reservation.class, id);
    }

    @Override
    public List<Reservation> findAll() {
        return em.createQuery("SELECT r FROM Reservation r",Reservation.class).getResultList();
    }

    @Override
    public List<Reservation> findByCustomer(Customer customer) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.customer=:customer",Reservation.class)
                .setParameter("customer", customer)
                .getResultList();
    }

    @Override
    public List<Reservation> findByTrip(Trip trip) {
        return em.createQuery("SELECT r FROM Reservation r WHERE r.trip=:trip",Reservation.class)
                .setParameter("trip", trip)
                .getResultList();
    }
}
