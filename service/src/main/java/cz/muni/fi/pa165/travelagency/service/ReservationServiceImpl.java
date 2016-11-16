package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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
        reservationDao.create(reservation);
    }

    public Reservation findById(Long id) {
        return reservationDao.findById(id);
    }

    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public List<Reservation> findByCustomer(Customer customer) {
        return reservationDao.findByCustomer(customer);
    }

    public List<Reservation> findByTrip(Trip trip) {
        return reservationDao.findByTrip(trip);
    }
}
