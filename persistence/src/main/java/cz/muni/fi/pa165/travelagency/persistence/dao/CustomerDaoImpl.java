package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 * Data access object implementation for Customer entity
 * @author Katerina Caletkova
 */

@Repository
public class CustomerDaoImpl implements CustomerDao {

    @PersistenceContext
    EntityManager em;
    
    @Override
    public List<Customer> findByName(String name) {
       if (name == null)
            throw new NullPointerException("Name can not be null.");
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE c.name = :name ",Customer.class)
            .setParameter("name", name).getResultList());

    }

    @Override
    public List<Customer> findByBirthDate(Date birthDate) {
        if (birthDate == null)
            throw new NullPointerException("BirthDate can not be null.");
        
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE c.birthDate = :birthDate ",Customer.class)
            .setParameter("birthDate", birthDate).getResultList());
    }

    @Override
    public List<Customer> findByPersonalNumber(Integer personalNumber) {
        if (personalNumber == null)
            throw new NullPointerException("PersonalNumber can not be null.");
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE c.personalNumber = :personalNumber ",Customer.class)
            .setParameter("personalNumber", personalNumber).getResultList());
    }

    @Override
    public List<Customer> findByMail(String mail) {
        if (mail == null)
            throw new NullPointerException("Mail can not be null.");
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE c.mail = :mail ",Customer.class)
            .setParameter("mail", mail).getResultList());
    }

    @Override
    public List<Customer> findByPhoneNumber(Integer phoneNumber) {
        if (phoneNumber == null)
            throw new NullPointerException("PhoneNumber can not be null.");
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber ",Customer.class)
            .setParameter("phoneNumber", phoneNumber).getResultList());
    }

    @Override
    public List<Customer> findByReservation(Reservation reservation) { 
        if (reservation == null)
            throw new NullPointerException("Reservation can not be null.");
        
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE :reservation IN elements(c.reservations) ",Customer.class)
            .setParameter("reservation", reservation).getResultList());
    }

    @Override
    public void create(Customer customer) {
         if (customer == null)
            throw new NullPointerException("Customer can not be null.");
        em.persist(customer);
    }

    @Override
    public void update(Customer customer) {
        if (customer == null)
            throw new NullPointerException("Customer can not be null.");
        em.merge(customer);
    }

    @Override
    public void delete(Customer customer) {
        if (customer == null)
            throw new NullPointerException("Customer can not be null.");
        em.remove(customer);
    }

    @Override
    public Customer findById(Long id) {
        if (id == null)
            throw new NullPointerException("Id can not be null.");
        return em.find(Customer.class, id);
    }

    @Override
    public List<Customer> findAll() {
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c ",Customer.class).getResultList());
    }
    
    
}
