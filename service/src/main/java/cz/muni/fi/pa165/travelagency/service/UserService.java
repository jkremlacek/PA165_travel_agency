package cz.muni.fi.pa165.travelagency.service;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
/**
 *
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
     * Create an entity.
     * @param user to create
     */
    public void create(User user);
    
    
    /**
     * Delete user.
     * @param user to delete
     */
    public void delete(User user);
}
