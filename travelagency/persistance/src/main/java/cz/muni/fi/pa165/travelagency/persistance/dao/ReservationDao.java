package cz.muni.fi.pa165.travelagency.persistance.dao;

import cz.muni.fi.pa165.travelagency.persistance.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistance.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistance.entity.Trip;
import java.util.List;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public interface ReservationDao {
    public Reservation findById(Long id);
    public void create(Reservation reservation);
    public void update(Reservation reservation);
    public void delete(Reservation reservation);
    public List<Reservation> findAll();
    public List<Reservation> findByCustomer(Customer customer);
    public List<Reservation> findByTrip(Trip trip);
}
