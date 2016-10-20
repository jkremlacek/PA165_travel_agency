package cz.muni.fi.pa165.travelagency.persistance.dao;

import cz.muni.fi.pa165.travelagency.persistance.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistance.entity.Reservation;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created on 20.10.2016.
 * Data access object interface for Customer entity
 *
 * @author Martin Salata
 */
public interface CustomerDao extends GenericDao<Customer, Long> {
    /**
     * Method to find all Customers with given name
     *
     * @param name to find
     * @return list of customers with given name
     */
    List<Customer> findByName(String name);

    /**
     * Method to find all Customers with given birth date
     *
     * @param date to find
     * @return list of customers with given birth date
     */
    List<Customer> findByBirthDate(Date date);

    /**
     * Method to find all Customers with personal number
     *
     * @param personalNumber to find
     * @return list of customers with given personal number
     */
    List<Customer> findByPersonalNumber(Integer personalNumber);

    /**
     * Method to find all Customers with given mail
     *
     * @param mail ot find
     * @return list of customers with given mail
     */
    List<Customer> findByMail(String mail);

    /**
     * Method to find all Customers with given phone number
     *
     * @param phoneNumber to find
     * @return list of customers with given phone number
     */
    List<Customer> findByPhoneNumber(Integer phoneNumber);

    /**
     * Method to find all Customers with given reservations
     *
     * @param reservation to find
     * @return list of customers with given reservations
     */
    List<Customer> findByReservation(Reservation reservation);
}
