package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.ExcursionDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Jakub Kremláček
 */
public class ExcursionServiceImpl implements ExcursionService{

	@Inject
    private ExcursionDao excursionDao;
	
	public void create(Excursion excursion) {
		excursionDao.create(excursion);
	}

	public void delete(Excursion excursion) {
		excursionDao.delete(excursion);
	}

	public Excursion findById(Long id) {
		return excursionDao.findById(id);
	}
	
	public List<Excursion> findAll() {
		return excursionDao.findAll();
	}

	public List<Excursion> findByName(String name) {
		return excursionDao.findByName(name);
	}

	public List<Excursion> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
		 return excursionDao.findByPrice(minPrice, maxPrice);	
	}

	public List<Excursion> findByDate(Date dateFrom, Date dateTo) {
		return excursionDao.findByDate(dateFrom, dateTo);
	}

	public List<Excursion> findByDestination(String destination) {
		return excursionDao.findByDestination(destination);
	}

	public List<Excursion> findByDuration(Date dateFrom, Date dateTo) {
		return excursionDao.findByDuration(dateFrom, dateTo);
	}

	public List<Excursion> findByTrip(Trip trip) {
		return excursionDao.findByTrip(trip);
	}	
}
