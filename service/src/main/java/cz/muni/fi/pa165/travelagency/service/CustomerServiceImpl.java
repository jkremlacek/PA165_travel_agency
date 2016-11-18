package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.CustomerDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kateřina Caletková
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    
    @Inject
    private CustomerDao customerDao;

    @Override
    public Customer findById(Long id) {
        return customerDao.findById(id);
    }

    @Override
    public List<Customer> findByName(String name) {
        return customerDao.findByName(name);
    }

    @Override
    public List<Customer> findByPersonalNumber(Integer personalNumber) {
        return customerDao.findByPersonalNumber(personalNumber);
    }

    @Override
    public List<Customer> findByMail(String mail) {
        return customerDao.findByMail(mail);
    }

    @Override
    public List<Customer> findByPhoneNumber(Integer phoneNumber) {
        return customerDao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<Customer> findByReservation(Reservation reservation) {
        return customerDao.findByReservation(reservation);
    }

    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public void delete(Customer customer) {
        customerDao.delete(customer);
    }
    
    @Override
    public void create(Customer customer) {
         customerDao.create(customer);
        
    }

    @Override
    public List<Customer> findByBirthDate(Date date) {
        return customerDao.findByBirthDate(date);
    }

}

   
