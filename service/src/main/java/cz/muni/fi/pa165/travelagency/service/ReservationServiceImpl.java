package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.exception.PersistenceException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created on 13.11.2016.
 *
 * @author Martin Salata
 */
@Service
public class ReservationServiceImpl implements ReservationService {

    @Inject
    private ReservationDao reservationDao;

    public void create(Reservation reservation) {
        try {
            reservationDao.create(reservation);
        } catch (NullPointerException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public void delete(Reservation reservation) {
        try {
            reservationDao.delete(reservation);
        } catch (NullPointerException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public Reservation findById(Long id) {
        try {
            return reservationDao.findById(id);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Reservation> findAll() {
        try {
            return reservationDao.findAll();
        } catch (NullPointerException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }

    }

    @Override
    public List<Reservation> findByUser(User user) {
        try {
            return reservationDao.findByUser(user);
        } catch (NullPointerException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Reservation> findByTrip(Trip trip) {
        try {
            return reservationDao.findByTrip(trip);
        } catch (NullPointerException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public Reservation addExcursion(Long reservationId, Excursion excursion) {
        try {
            Reservation toUpdate = reservationDao.findById(reservationId);
            toUpdate.addExcursion(excursion);
            reservationDao.update(toUpdate);
            return reservationDao.findById(reservationId);
        } catch (NullPointerException | ValidationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public BigDecimal getTotalPrice(Long reservationId) {
        try {
            Reservation reservation = reservationDao.findById(reservationId);
            BigDecimal total = reservation.getTrip().getPrice();
            for (Excursion excursion : reservation.getExcursionSet()) {
                total = total.add(excursion.getPrice());
            }
            return total;
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

}
