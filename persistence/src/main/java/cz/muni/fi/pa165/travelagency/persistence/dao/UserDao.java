package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;

import java.util.Date;
import java.util.List;

/**
 * Created on 20.10.2016.
 * Data access object interface for User entity
 *
 * @author Martin Salata
 */
public interface UserDao extends GenericDao<User, Long> {
    /**
     * Method to find all Users with given name
     *
     * @param name to find
     * @return list of users with given name
     */
    List<User> findByName(String name);

    /**
     * Method to find all Users with given birth date
     *
     * @param date to find
     * @return list of users with given birth date
     */
    List<User> findByBirthDate(Date date);

    /**
     * Method to find all Users with personal number
     *
     * @param personalNumber to find
     * @return list of users with given personal number
     */
    List<User> findByPersonalNumber(Integer personalNumber);

    /**
     * Method to find all Users with given mail
     *
     * @param mail ot find
     * @return list of users with given mail
     */
    List<User> findByMail(String mail);

    /**
     * Method to find all Users with given phone number
     *
     * @param phoneNumber to find
     * @return list of users with given phone number
     */
    List<User> findByPhoneNumber(Integer phoneNumber);

    /**
     * Method to find all Users with given reservations
     *
     * @param reservation to find
     * @return list of users with given reservations
     */
    List<User> findByReservation(Reservation reservation);
}
