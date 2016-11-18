package cz.muni.fi.pa165.travelagency.service;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service layer interface for user
 * @author Kateřina Caletková
 */
@Service
public interface UserService {
     
    /**
     * Find user with given id.
     * @param id to find
     * @return user with given id
     */
    User findById(Long id);
    
    /**
     * Find all users with given name.
     * @param name to find
     * @return list of users with given name
     */
    List<User> findByName(String name);
    
    /**
     * Find all Users with given birth date
     * @param date to find
     * @return list of users with given birth date
     */
    List<User> findByBirthDate(Date date);
    
     /**
     * Find all users with personal number
     * @param personalNumber to find
     * @return list of users with given personal number
     */
    List<User> findByPersonalNumber(Integer personalNumber);
    
    /**
     * Find all users with given mail.
     * @param mail ot find
     * @return list of users with given mail
     */
    List<User> findByMail(String mail);
    
    /**
     * Find all users with given phone number.
     * @param phoneNumber to find
     * @return list of users with given phone number
     */
    List<User> findByPhoneNumber(Integer phoneNumber);

     /**
     * Find all users with given reservation.
     * @param reservation to find
     * @return list of users with given reservation
     */
    List<User> findByReservation(Reservation reservation);

    /**
     * Give you all users.
     * @return list of all users
     */
    List<User> findAll();
    
    /**
     * Create and registrate user
     * @param user to be created and registrated
     * @param password to be hashed and set with user
     */
    public void createRegisteredUser(User user, String password);
    
     /**
     * Update an entity.
     * @param user to update
     */
    public void update(User user);
    
    /**
     * Delete user.
     * @param user to delete
     */
    public void delete(User user);
    
    /**
     * Find out if user is admin
     * @param user user for which should be find out if is admin
     * @return true if user is admin and false is user isn't admin
     */
    boolean isUserAdmin(User user);
    
    /**
     * Find out is user is successfully authenticated or not
     * @param user which should be authenticated
     * @param passwordHash to be use for authentication
     * @return true if user eas successfully authenticated, false if wasn't
     */
    boolean userAuthenticate(User user, String passwordHash);
}
