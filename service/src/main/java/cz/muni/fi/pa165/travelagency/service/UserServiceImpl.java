package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.UserDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
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
public class UserServiceImpl implements UserService {
    
    @Inject
    private UserDao userDao;

    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public List<User> findByPersonalNumber(Integer personalNumber) {
        return userDao.findByPersonalNumber(personalNumber);
    }

    @Override
    public List<User> findByMail(String mail) {
        return userDao.findByMail(mail);
    }

    @Override
    public List<User> findByPhoneNumber(Integer phoneNumber) {
        return userDao.findByPhoneNumber(phoneNumber);
    }

    @Override
    public List<User> findByReservation(Reservation reservation) {
        return userDao.findByReservation(reservation);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }
    
    @Override
    public void create(User user) {
         userDao.create(user);
        
    }

    @Override
    public List<User> findByBirthDate(Date date) {
        return userDao.findByBirthDate(date);
    }

}

   
