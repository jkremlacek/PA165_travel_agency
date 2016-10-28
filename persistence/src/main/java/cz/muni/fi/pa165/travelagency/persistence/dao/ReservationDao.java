package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import java.util.List;

/**
 *
 * Reservation interface
 * 
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public interface ReservationDao extends GenericDao<Reservation, Long>{
    
    /**
     * 
     * Method for find all reservations of customer
     * 
     * @param customer reservation of this customer will be find
     * @return reservations of customer as List
     */
    public List<Reservation> findByCustomer(Customer customer);
    
    /**
     * 
     * Method to find all reservations of trip
     * 
     * @param trip reservation of this trip will be find
     * @return reservations of trip as List
     */
    public List<Reservation> findByTrip(Trip trip);
}
