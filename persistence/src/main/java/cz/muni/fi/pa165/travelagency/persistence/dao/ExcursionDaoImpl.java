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
		return Collections.unmodifiableList(em.createQuery("SELECT e FROM Excursion e WHERE e.name = :name ",Excursion.class)
            .setParameter("name", name).getResultList());
	}

	@Override
	public List<Excursion> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
		return Collections.unmodifiableList(
				em.createQuery(
						"SELECT e FROM Excursion e WHERE e.minPrice >= :minPrice AND e.maxPrice <= :maxPrice"
						,Excursion.class)
            .setParameter("minPrice", minPrice).setParameter("maxPrice", maxPrice).getResultList());
	}

	@Override
	public List<Excursion> findByDate(Date dateFrom, Date dateTo) {
		return Collections.unmodifiableList(
				em.createQuery(
						"SELECT e FROM Excursion e WHERE e.date >= :dateFrom AND e.date <= :dateTo"
						,Excursion.class)
            .setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo).getResultList());
	}

	@Override
	public List<Excursion> findByDestination(String destination) {
		return Collections.unmodifiableList(em.createQuery("SELECT e FROM Excursion e WHERE e.destination = :destination ",Excursion.class)
            .setParameter("destination", destination).getResultList());
	}

	@Override
	public List<Excursion> findByDuration(Date dateFrom, Date dateTo) {
		return Collections.unmodifiableList(
				em.createQuery(
						"SELECT e FROM Excursion e WHERE e.duration >= :dateFrom AND e.duration <= :dateTo"
						,Excursion.class)
            .setParameter("dateFrom", dateFrom).setParameter("dateTo", dateTo).getResultList());
	}

	@Override
	public List<Excursion> findByTrip(Trip trip) {
		return Collections.unmodifiableList(em.createQuery("SELECT e FROM Excursion e WHERE e.trip = :trip ",Excursion.class)
            .setParameter("trip", trip).getResultList());
	}

	@Override
	public void create(Excursion entity) {
		em.persist(entity);
	}

	@Override
	public void update(Excursion entity) {
		em.merge(entity);
	}

	@Override
	public void delete(Excursion entity) {
		em.remove(entity);
	}

	@Override
	public Excursion findById(Long id) {
		return em.find(Excursion.class, id);
	}

	@Override
	public List<Excursion> findAll() {
		return Collections.unmodifiableList(em.createQuery("SELECT e FROM Excursion e ",Excursion.class).getResultList());
	}
	
}
