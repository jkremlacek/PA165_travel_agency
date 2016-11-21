
package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelagency.persistence.dao.TripDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.exception.PersistenceException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.validation.ValidationException;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
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
    public List<Trip> findByCapacity(Integer capacity) {
        try {
            List<Trip> trips = tripDao.findByCapacity(capacity);
            for (Trip trip : trips) {
                if (trip.getCapacity() - reservationDao.findByTrip(trip).size() <= 0) {
                    trips.remove(trip);
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
        return findByCapacity(1);
    }

    @Override
    public List<Trip> findTripsNextMonth() {
        Calendar cal = Calendar.getInstance();
        Date from = cal.getTime();
        cal.add(Calendar.DATE, 31);
        Date to = cal.getTime();
        return findByDate(from, to);
    }

}
