package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.ExcursionDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.exception.PersistenceException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.validation.ValidationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jakub Kremláček
 */
@Service
public class ExcursionServiceImpl implements ExcursionService{

    @Inject
    private ExcursionDao excursionDao;
	
    @Override
    public Long create(Excursion excursion) {
        try {
                return excursionDao.create(excursion);
            } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new PersistenceException(ex.getMessage());
            }	
    }
	
    @Override
    public void update(Excursion excursion) {
        try {
                excursionDao.update(excursion);
            } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new PersistenceException(ex.getMessage());
            }
    }

    @Override
    public void delete(Excursion excursion) {
        try {
            excursionDao.delete(excursion);
        } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new PersistenceException(ex.getMessage());
        }
    }

    @Override
    public Excursion findById(Long id) {
        try {
                return excursionDao.findById(id);
            } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new PersistenceException(ex.getMessage());
            }
    }
	
    @Override
    public List<Excursion> findAll() {
        try {
                return excursionDao.findAll();
            } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new PersistenceException(ex.getMessage());
            }
    }

    @Override
    public List<Excursion> findByName(String name) {
        try {
                return excursionDao.findByName(name);
            } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new PersistenceException(ex.getMessage());
            }
    }

    @Override
    public List<Excursion> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        try {
                return excursionDao.findByPrice(minPrice, maxPrice);
            } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new PersistenceException(ex.getMessage());
            }
    }

    @Override
    public List<Excursion> findByDate(Date dateFrom, Date dateTo) {
        try {
                return excursionDao.findByDate(dateFrom, dateTo);
            } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new PersistenceException(ex.getMessage());
            }
    }

    @Override
    public List<Excursion> findByDestination(String destination) {
        try {
                return excursionDao.findByDestination(destination);
            } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new PersistenceException(ex.getMessage());
            }
    }

//    @Override
//    public List<Excursion> findByDuration(Date dateFrom, Date dateTo) {
//        try {
//                return excursionDao.findByDuration(dateFrom, dateTo);
//            } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
//                throw ex;
//            } catch (Exception ex) {
//                throw new PersistenceException(ex.getMessage());
//            }
//    }

    @Override
    public List<Excursion> findByTrip(Trip trip) {
        try {
                return excursionDao.findByTrip(trip);
            } catch (NullPointerException | ValidationException | IllegalArgumentException ex) {
                throw ex;
            } catch (Exception ex) {
                throw new PersistenceException(ex.getMessage());
            }
    }	
}
