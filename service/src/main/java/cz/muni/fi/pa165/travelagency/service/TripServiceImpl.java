
package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelagency.persistence.dao.TripDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.exception.PersistenceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.validation.ValidationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@Service
public class TripServiceImpl implements TripService {
    
    @Inject
    private TripDao tripDao;
    
    @Inject
    private ReservationDao reservationDao;

    @Override
    public void create(Trip trip) {
        try {
            tripDao.create(trip);
        } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public void update(Trip trip) {
        try {
            tripDao.update(trip);
        } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public void delete(Trip trip) {
        try {
            tripDao.delete(trip);
        } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public Trip findById(Long id) {
        try {
            return tripDao.findById(id);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Trip> findAll() {
        try {
            return tripDao.findAll();
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Trip> findByName(String name) {
        try {
            return tripDao.findByName(name);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Trip> findByDate(Date from, Date to) {
        try {
            return tripDao.findByDate(from, to);
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Trip> findByDestination(String destination) {
        try {
            return tripDao.findByDestination(destination);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Trip> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        try {
            return tripDao.findByPrice(minPrice, maxPrice);
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }
    
    @Override
    public List<Trip> findByAvailableCapacity(Integer capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException(
                    "Cannot find trip with available capacity smaller than 1");
        }
        try {
            List<Trip> trips = tripDao.findByTotalCapacity(capacity);
            Iterator<Trip> it = trips.iterator();
            while (it.hasNext()) {
                Trip trip = it.next();
                if (trip.getCapacity() - reservationDao.findByTrip(trip).size() <= 0) {
                    it.remove();
                }
            }
            return trips;
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Trip> findByExcursion(Excursion excursion) {
        try {
            return tripDao.findByExcursion(excursion);
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public List<Trip> findWithFreeCapacity() {
        return findByAvailableCapacity(1);
    }

    @Override
    public List<Trip> findTripsInNextDays(int countOfDays) {
        if (countOfDays < 1) {
            throw new IllegalArgumentException("Argument cannot be smaller than 1 --"
                    + "you cannot find trips in past");
        }
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date from = cal.getTime();
        cal.add(Calendar.DATE, countOfDays);
        Date to = cal.getTime();
        return findByDate(from, to);
    }

    @Override
    public List<User> findTripParticipants(Trip trip) {
        try {
            List<Reservation> reservations = reservationDao.findByTrip(trip);
            List<User> users = new ArrayList<>();
            for (Reservation reservation : reservations) {
                users.add(reservation.getUser());
            }
            return users;
            } catch (NullPointerException ex) {
                throw ex;
            } catch (Exception ex)  {
                throw new PersistenceException(ex.getMessage());
            }
    }

    @Override
    public boolean hasTripAvailableCapacity(Trip trip) {
        if (trip == null) {
            throw new NullPointerException("trip is null");
        }
        try {
            return trip.getCapacity() > reservationDao.findByTrip(trip).size();
        } catch (NullPointerException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

}
