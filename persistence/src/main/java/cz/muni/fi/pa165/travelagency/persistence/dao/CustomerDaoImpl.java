/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE c.name = :name ",Customer.class)
            .setParameter("name", name).getResultList());

    }

    @Override
    public List<Customer> findByBirthDate(Date birthDate) {
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE c.birthDate = :birthDate ",Customer.class)
            .setParameter("birthDate", birthDate).getResultList());
    }

    @Override
    public List<Customer> findByPersonalNumber(Integer personalNumber) {
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE c.personalNumber = :personalNumber ",Customer.class)
            .setParameter("personalNumber", personalNumber).getResultList());
    }

    @Override
    public List<Customer> findByMail(String mail) {
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE c.mail = :mail ",Customer.class)
            .setParameter("mail", mail).getResultList());
    }

    @Override
    public List<Customer> findByPhoneNumber(Integer phoneNumber) {
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE c.phoneNumber = :phoneNumber ",Customer.class)
            .setParameter("phoneNumber", phoneNumber).getResultList());
    }

    @Override
    public List<Customer> findByReservation(Reservation reservation) { 
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c WHERE :reservation IN (c.reservations) ",Customer.class)
            .setParameter("reservation", reservation).getResultList());
    }

    @Override
    public void create(Customer customer) {
        em.persist(customer);
    }

    @Override
    public void update(Customer customer) {
        em.merge(customer);
    }

    @Override
    public void delete(Customer customer) {
        em.remove(customer);
    }

    @Override
    public Customer findById(Long id) {
        return em.find(Customer.class, id);
    }

    @Override
    public List<Customer> findAll() {
        return Collections.unmodifiableList(em.createQuery("SELECT c FROM Customer c ",Customer.class).getResultList());
    }
    
}
