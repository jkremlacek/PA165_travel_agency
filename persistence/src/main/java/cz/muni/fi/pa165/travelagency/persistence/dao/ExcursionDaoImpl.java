/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidationException;
import org.springframework.stereotype.Repository;

/**
 * Data access object implementation for Excursion entity
 * @author Jakub Kremláček
 */

@Repository
public class ExcursionDaoImpl implements ExcursionDao {

	@PersistenceContext
    EntityManager em;
	
	@Override
	public List<Excursion> findByName(String name) {
		if (name == null) throw new NullPointerException("Parameter name cannot be null.");
		
		return em.createQuery("SELECT e FROM Excursion e WHERE e.name = :name ",Excursion.class)
            .setParameter("name", name).getResultList();
	}

	@Override
	public List<Excursion> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
		if (minPrice == null) throw new NullPointerException("Parameter minPrice cannot be null.");
		if (maxPrice == null) throw new NullPointerException("Parameter maxPrice cannot be null.");
		
		if (maxPrice.compareTo(minPrice) == -1) throw new IllegalArgumentException("Attribute maxPrice must be higher or equal to minPrice.");
		
		return em.createQuery(
						"SELECT e FROM Excursion e WHERE e.price >= :minPrice AND e.price <= :maxPrice"
						,Excursion.class)
            .setParameter("minPrice", minPrice).setParameter("maxPrice", maxPrice).getResultList();
	}

	@Override
	public List<Excursion> findByDate(Date dateFrom, Date dateTo) {
		if (dateFrom == null) throw new NullPointerException("Parameter dateFrom cannot be null.");
		if (dateTo == null) throw new NullPointerException("Parameter dateTo cannot be null.");
		
		if (dateTo.getTime() - dateFrom.getTime() < 0) throw new IllegalArgumentException("Argument DateTo must be after DateFrom.");
		
		return em.createQuery(
						"SELECT e FROM Excursion e WHERE e.date >= :dateFrom AND e.date <= :dateTo"
						,Excursion.class)
            .setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo).getResultList();
	}

	@Override
	public List<Excursion> findByDestination(String destination) {
		if (destination == null) throw new NullPointerException("Parameter destination cannot be null.");
		
		return em.createQuery("SELECT e FROM Excursion e WHERE e.destination = :destination ",Excursion.class)
            .setParameter("destination", destination).getResultList();
	}

	@Override
	public List<Excursion> findByDuration(Date dateFrom, Date dateTo) {
		if (dateFrom == null) throw new NullPointerException("Parameter dateFrom cannot be null.");
		if (dateTo == null) throw new NullPointerException("Parameter dateTo cannot be null.");
		
		if (dateTo.getTime() < dateFrom.getTime()) throw new IllegalArgumentException("Argument DateTo must be after DateFrom.");
		
		return em.createQuery(
						"SELECT e FROM Excursion e WHERE e.duration >= :dateFrom AND e.duration <= :dateTo"
						,Excursion.class)
            .setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo).getResultList();
	}

	@Override
	public List<Excursion> findByTrip(Trip trip) {
		if (trip == null) throw new NullPointerException("Parameter trip cannot be null.");
		
		return em.createQuery("SELECT e FROM Excursion e WHERE e.trip = :trip ",Excursion.class)
            .setParameter("trip", trip).getResultList();
	}

	@Override
	public void create(Excursion entity) {
		if (entity == null) throw new NullPointerException("Parameter entity cannot be null.");
		if (entity.getTrip() == null) throw new ValidationException("Trip must be set!");
		em.persist(entity);
	}

	@Override
	public void update(Excursion entity) {
		if (entity == null) throw new NullPointerException("Parameter entity cannot be null.");
		em.merge(entity);
	}

	@Override
	public void delete(Excursion entity) {
		if (entity == null) throw new NullPointerException("Parameter entity cannot be null.");
		em.remove(entity);
	}

	@Override
	public Excursion findById(Long id) {
		return em.find(Excursion.class, id);
	}

	@Override
	public List<Excursion> findAll() {
		return em.createQuery("SELECT e FROM Excursion e ",Excursion.class).getResultList();
	}
	
}
