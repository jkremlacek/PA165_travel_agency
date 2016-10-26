
package cz.muni.fi.pa165.travelagency.persistance.dao;

import cz.muni.fi.pa165.travelagency.persistance.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistance.entity.Trip;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@Repository
public class TripDaoImpl implements TripDao {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Trip> findByName(String name) {
        return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE t.name = :name", Trip.class)
                                .setParameter("name", name)
                                .getResultList());
    }

    @Override
    public List<Trip> findByDate(Date from, Date to) {
        return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE "+
                                "t.dateFrom >= :from AND t.dateTo <= :to", Trip.class)
                                .setParameter(":from", from)
                                .setParameter(":to", to)
                                .getResultList());
    }

    @Override
    public List<Trip> findByDestination(String destination) {
        return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE t.destination = :destination", Trip.class)
                                .setParameter("name", destination)
                                .getResultList());
    }

    @Override
    public List<Trip> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE "+
                                "t.price >= :minPrice AND t.price <= :maxPrice", Trip.class)
                                .setParameter(":minPrice", minPrice)
                                .setParameter(":maxPrice", maxPrice)
                                .getResultList());
    }

    @Override
    public List<Trip> findByCapacity(Integer capacity) {
        return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE t.capacity >= :capacity", Trip.class)
                                                .setParameter("capacity", capacity)
                                                .getResultList());
    }

    @Override
    public List<Trip> findByExcursion(Excursion excursion) {
       return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE :excursion IN t.excursions", Trip.class)
                                                .setParameter("excursion", excursion)
                                                .getResultList());
    }

    @Override
    public void create(Trip entity) {
        em.persist(entity);
    }

    @Override
    public void update(Trip entity) {
        em.merge(entity);
    }

    @Override
    public void delete(Trip entity) {
        em.remove(entity);
    }

    @Override
    public Trip findById(Long id) {
        return em.find(Trip.class, id);
    }

    @Override
    public List<Trip> findAll() {
        return em.createQuery("SELECT t FROM Trip t",Trip.class).getResultList();
    }

}
