package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Data access object implementation for User entity
 * @author Katerina Caletkova
 */

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;
    
    @Override
    public List<User> findByName(String name) {
       if (name == null)
            throw new NullPointerException("Name can not be null.");
        return em.createQuery("SELECT c FROM User c WHERE c.name = :name ",User.class)
            .setParameter("name", name).getResultList();

    }

    @Override
    public List<User> findByBirthDate(Date birthDate) {
        if (birthDate == null)
            throw new NullPointerException("BirthDate can not be null.");
        
        return em.createQuery("SELECT c FROM User c WHERE c.birthDate = :birthDate ",User.class)
            .setParameter("birthDate", birthDate).getResultList();
    }

    @Override
    public List<User> findByPersonalNumber(String personalNumber) {
        if (personalNumber == null)
            throw new NullPointerException("PersonalNumber can not be null.");
        return em.createQuery("SELECT c FROM User c WHERE c.personalNumber = :personalNumber ",User.class)
            .setParameter("personalNumber", personalNumber).getResultList();
    }

    @Override
    public User findByMail(String mail) {
        if (mail == null)
            throw new NullPointerException("Mail can not be null.");
        try {
        return em.createQuery("SELECT c FROM User c WHERE c.mail = :mail ",User.class)
            .setParameter("mail", mail).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<User> findByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null)
            throw new NullPointerException("PhoneNumber can not be null.");
        return em.createQuery("SELECT c FROM User c WHERE c.phoneNumber = :phoneNumber ",User.class)
            .setParameter("phoneNumber", phoneNumber).getResultList();
    }

    @Override
    public List<User> findByReservation(Reservation reservation) { 
        if (reservation == null)
            throw new NullPointerException("Reservation can not be null.");
        
        return em.createQuery("SELECT c FROM User c WHERE :reservation IN elements(c.reservations) ",User.class)
            .setParameter("reservation", reservation).getResultList();
    }

    @Override
    public Long create(User user) {
         if (user == null)
            throw new NullPointerException("User can not be null.");
         if (user.getName() == null)
             throw new NullPointerException("User's name can not be null.");
         if (user.getName().isEmpty())
             throw new NullPointerException("User's name can not be empty.");
         if (user.getPasswordHash() == null)
             throw new NullPointerException("User's password can not be null.");
         if (user.getIsAdmin() == null)
             throw new NullPointerException("User's role (is admin) can not be null.");
         if (user.getPersonalNumber() == null)
             throw new NullPointerException("User's personal number can not be null.");      
        em.persist(user);
        return user.getId();

    }

    @Override
    public void update(User user) {
        if (user == null)
            throw new NullPointerException("User can not be null.");
         if (user.getName() == null)
             throw new NullPointerException("User's name can not be null.");
         if (user.getName().isEmpty())
             throw new NullPointerException("User's name can not be empty.");
         if (user.getPasswordHash() == null)
             throw new NullPointerException("User's password can not be null.");
         if (user.getIsAdmin() == null)
             throw new NullPointerException("User's role (is admin) can not be null.");
         if (user.getPersonalNumber() == null)
             throw new NullPointerException("User's personal number can not be null.");     
        em.merge(user);
    }

    @Override
    public void delete(User user) {
        if (user == null)
            throw new NullPointerException("User can not be null.");
        em.remove(findById(user.getId()));
    }

    @Override
    public User findById(Long id) {
        if (id == null)
            throw new NullPointerException("Id can not be null.");
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT c FROM User c ",User.class).getResultList();
    }
    
    
}
