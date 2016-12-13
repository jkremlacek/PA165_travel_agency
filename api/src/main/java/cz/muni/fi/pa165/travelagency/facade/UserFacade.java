package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.UserCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserAuthenticateDto;
import java.util.Date;
import java.util.List;

/**
 * Facade interface for User entity
 * @author Kateřina Caletková
 */
public interface UserFacade {
    
    /**
     * Create a new user
     * @param user to be created
     * @return id of created user
     */
    Long create(UserCreateDto user);
    
    /**
     * Update user
     * @param user to be updated
     */
    void update(UserDto user);
    
    /**
     * Delete user
     * @param user to be deleted
     */
    void delete(UserDto user);
    
    /**
     * Find user by id
     * @param id user id
     * @return user with given id
     */
    UserDto findById(Long id);
    
    /**
     * Find all users
     * @return all users
     */
    List<UserDto> findAll();
    
    /**
     * Find users by name
     * @param name user name
     * @return users with given name
     */
    List<UserDto> findByName(String name);
    
    /**
     * Find users by birth date
     * @param date user birth date
     * @return users with given birth date
     */
    List<UserDto> findByBirthDate(Date date);
    
    /**
     * Find users by personal number
     * @param personalNumber user personal number
     * @return users with given personal number
     */
    List<UserDto> findByPersonalNumber(String personalNumber);
    
    /**
     * Find user by mail
     * @param mail user mail
     * @return user with given mail
     */
    UserDto findByMail(String mail);
    
    /**
     * Find users by phone number
     * @param phoneNumber user phone number
     * @return users with given phone number
     */
    List<UserDto> findByPhoneNumber(String phoneNumber);
    
    /**
     * Find uses by reservation
     * @param reservation user's reservation
     * @return users which made given reservation
     */
    List<UserDto> findByReservation(ReservationDto reservation);
    
    /**
     * Find out if user is admin
     * @param user user
     * @return true if user is admin, false if user isn't admin
     */
    Boolean isUserAdmin(UserDto user);
    
    /**
     * Find out if user was successfully authenticated
     * @param user user
     * @return authenticated user if correct  login information were entered
     */
    UserDto userAuthenticate (UserAuthenticateDto user);
}
