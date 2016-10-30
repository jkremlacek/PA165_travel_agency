
package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
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
        if (name == null) {
            throw new NullPointerException("Argument name cannot be null.");
        }
        return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE t.name = :name", Trip.class)
                                .setParameter("name", name)
                                .getResultList());
    }

    @Override
    public List<Trip> findByDate(Date from, Date to) {
        if (from == null) {
            throw new NullPointerException("Argument from cannot be null.");
        }
        if (to == null) {
            throw new NullPointerException("Argument to cannot be null.");
        }
        if (from.after(to)) {
            throw new IllegalArgumentException("Date from cannot be greater than date to");
        }
        return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE "+
                                "t.dateFrom >= :from AND t.dateTo <= :to", Trip.class)
                                .setParameter("from", from)
                                .setParameter("to", to)
                                .getResultList());
    }

    @Override
    public List<Trip> findByDestination(String destination) {
        if (destination == null) {
            throw new NullPointerException("Argument destination cannot be null.");
        }
        return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE t.destination = :destination", Trip.class)
                                .setParameter("destination", destination)
                                .getResultList());
    }

    @Override
    public List<Trip> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        if (minPrice == null) {
            throw new NullPointerException("Argument minPrice cannot be null.");
        }
        if (maxPrice == null) {
            throw new NullPointerException("Argument maxPrice cannot be null.");
        }
        if (minPrice.compareTo(maxPrice)>0) {
            throw new IllegalArgumentException("Argument minPrice cannot be greater than maxPrice");
        }
        return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE "+
                                "t.price >= :minPrice AND t.price <= :maxPrice", Trip.class)
                                .setParameter("minPrice", minPrice)
                                .setParameter("maxPrice", maxPrice)
                                .getResultList());
    }

    @Override
    public List<Trip> findByCapacity(Integer capacity) {
        if (capacity == null) {
            throw new NullPointerException("Argument capacity cannot be null.");
        }
        if (capacity.compareTo(Integer.valueOf("1"))<1) {
            throw new IllegalArgumentException("Input capacity to find cannot be smaller than 1");
        }
        // TO DO finding total capacity of trip, not acutal!
        return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE t.capacity >= :capacity", Trip.class)
                                                .setParameter("capacity", capacity)
                                                .getResultList());
    }

    @Override
    public List<Trip> findByExcursion(Excursion excursion) {
        if (excursion == null) {
            throw new NullPointerException("Argument excursion cannot be null.");
        }
        return Collections.unmodifiableList(
                em.createQuery("SELECT t FROM Trip t WHERE :excursion IN elements(t.excursions)", Trip.class)
                                                .setParameter("excursion", excursion)
                                                .getResultList());
    }

    @Override
    public void create(Trip entity) {
        if (entity == null) {
            throw new NullPointerException("Argument trip entity cannot be null.");
        }
        em.persist(entity);
    }

    @Override
    public void update(Trip entity) {
        if (entity == null) {
            throw new NullPointerException("Argument trip entity cannot be null.");
        }
        em.merge(entity);
    }

    @Override
    public void delete(Trip entity) {
        if (entity == null) {
            throw new NullPointerException("Argument trip entity cannot be null.");
        }
        em.remove(entity);
    }

    @Override
    public Trip findById(Long id) {
        if (id == null) {
            throw new NullPointerException("Argument id cannot be null.");
        }
        return em.find(Trip.class, id);
    }

    @Override
    public List<Trip> findAll() {
        return em.createQuery("SELECT t FROM Trip t",Trip.class).getResultList();
    }

}
