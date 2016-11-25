package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.User;
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
     * Method for find all reservations of user
     * 
     * @param user reservation of this user will be find
     * @return reservations of user as List
     */
    List<Reservation> findByUser(User user);
    
    /**
     * 
     * Method to find all reservations of trip
     * 
     * @param trip reservation of this trip will be find
     * @return reservations of trip as List
     */
    List<Reservation> findByTrip(Trip trip);
}
