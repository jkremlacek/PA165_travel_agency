package cz.muni.fi.pa165.travelagency.service;
import cz.muni.fi.pa165.travelagency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
/**
 *
 * @author Kateřina Caletková
 */
@Service
public interface CustomerService {
     
    /**
     * Find customer with given id.
     * @param id to find
     * @return customer with given id
     */
    Customer findById(Long id);
    
    /**
     * Find all customers with given name.
     * @param name to find
     * @return list of customers with given name
     */
    List<Customer> findByName(String name);
    
    /**
     * Find all Customers with given birth date
     * @param date to find
     * @return list of customers with given birth date
     */
    List<Customer> findByBirthDate(Date date);
    
     /**
     * Find all customers with personal number
     * @param personalNumber to find
     * @return list of customers with given personal number
     */
    List<Customer> findByPersonalNumber(Integer personalNumber);
    
    /**
     * Find all customers with given mail.
     * @param mail ot find
     * @return list of customers with given mail
     */
    List<Customer> findByMail(String mail);
    
    /**
     * Find all customers with given phone number.
     * @param phoneNumber to find
     * @return list of customers with given phone number
     */
    List<Customer> findByPhoneNumber(Integer phoneNumber);

     /**
     * Find all customers with given reservation.
     * @param reservation to find
     * @return list of customers with given reservation
     */
    List<Customer> findByReservation(Reservation reservation);

    /**
     * Give you all customers.
     * @return list of all customers
     */
    List<Customer> findAll();
    
    /**
     * Create an entity.
     * @param customer to create
     */
    public void create(Customer customer);
    
    
    /**
     * Delete customer.
     * @param customer to delete
     */
    public void delete(Customer customer);
}
