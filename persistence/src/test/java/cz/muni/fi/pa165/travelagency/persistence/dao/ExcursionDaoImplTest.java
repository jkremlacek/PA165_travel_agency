package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.config.InMemorySpring;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidationException;
import static org.hamcrest.CoreMatchers.is;
import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * Testing implementation of Excursion DAO
 * 
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemorySpring.class)
@Transactional
public class ExcursionDaoImplTest {
    
    @Inject
    private ExcursionDao excursionDao;
    
    @PersistenceContext
    private EntityManager em;
    
    private Trip waterTrip;
    private Trip pragueBrnoTrip;
    
    private Excursion aquaParkExcursion;
    private Excursion artGalleryExcursion;
    private Excursion bigHillExcursion;
    
    private Calendar calNow;
    

    @Before
    public void setUp() {
        
        calNow = Calendar.getInstance();
        Calendar cal = (Calendar) calNow.clone();
        cal.add(Calendar.DAY_OF_MONTH, 10);
        Date startDay = cal.getTime();
        cal = (Calendar) calNow.clone();
        cal.add(Calendar.DAY_OF_MONTH, 20);
        Date endDay = cal.getTime();
        waterTrip = new Trip();
        waterTrip.setName("Water & relax");
        waterTrip.setCapacity(Integer.valueOf("50"));
        waterTrip.setPrice(BigDecimal.valueOf(1200));
        waterTrip.setDestination("South Moravia");
        waterTrip.setDescription("Relaxing in pools and outdoor swimming pools");
        waterTrip.setDateFrom(startDay);
        waterTrip.setDateTo(endDay);
        em.persist(waterTrip);
        
        cal = (Calendar) calNow.clone();
        cal.add(Calendar.DAY_OF_MONTH, 15);
        startDay = cal.getTime();
        cal.set(0, 0, 0, 7, 0);
        Date duration = cal.getTime();
        aquaParkExcursion = new Excursion();
        aquaParkExcursion.setName("Aqua park");
        aquaParkExcursion.setDate(startDay);
        aquaParkExcursion.setDuration(duration);
        aquaParkExcursion.setDestination("Pasohlavky");
        aquaParkExcursion.setPrice(BigDecimal.valueOf(500));
        aquaParkExcursion.setDescription("Nice excursion to nice crowded aqua park");
        aquaParkExcursion.setTrip(waterTrip);
        
        cal = (Calendar) calNow.clone();
        cal.add(Calendar.DAY_OF_MONTH, 40);
        startDay = cal.getTime();
        cal = (Calendar) calNow.clone();
        cal.add(Calendar.DAY_OF_MONTH, 45);
        endDay = cal.getTime();
        pragueBrnoTrip = new Trip();
        pragueBrnoTrip.setName("Sight in Prague & Brno");
        pragueBrnoTrip.setCapacity(Integer.valueOf("25"));
        pragueBrnoTrip.setPrice(BigDecimal.valueOf(2500));
        pragueBrnoTrip.setDestination("Prague & Brno");
        pragueBrnoTrip.setDateFrom(startDay);
        pragueBrnoTrip.setDateTo(endDay);
        em.persist(pragueBrnoTrip);
                
        
        cal = (Calendar) calNow.clone();
        cal.add(Calendar.DAY_OF_MONTH, 41);
        startDay = cal.getTime();
        cal.set(0, 0, 0, 2, 0);
        duration = cal.getTime();
        artGalleryExcursion = new Excursion();
        artGalleryExcursion.setName("Art gallery");
        artGalleryExcursion.setDate(startDay);
        artGalleryExcursion.setDuration(duration);
        artGalleryExcursion.setDestination("Prague");
        artGalleryExcursion.setPrice(BigDecimal.valueOf(200));
        artGalleryExcursion.setDescription("Nice excursion to nice art gallery");
        artGalleryExcursion.setTrip(pragueBrnoTrip);
        
        
        cal = (Calendar) calNow.clone();
        cal.add(Calendar.DAY_OF_MONTH, 41);
        startDay = cal.getTime();
        cal.set(0, 0, 0, 18, 30);
        duration = cal.getTime();
        bigHillExcursion = new Excursion();
        bigHillExcursion.setName("Cow mountain");
        bigHillExcursion.setDate(startDay);
        bigHillExcursion.setDuration(duration);
        bigHillExcursion.setDestination("Brno");
        bigHillExcursion.setPrice(BigDecimal.valueOf(1000));
        bigHillExcursion.setDescription("Nice excursion to Monte Bů mountain in Brno");
        bigHillExcursion.setTrip(pragueBrnoTrip);
        
    }
    
    /**
     * Test of findByName method, of class ExcursionDaoImpl.
     */
    @Test
    public void testFindByName() {
        assertThat(excursionDao.findByName(""))
                              .isEmpty();
        excursionDao.create(bigHillExcursion);
        excursionDao.create(aquaParkExcursion);
        assertThat(excursionDao.findByName("Aqua park"))
                                .contains(aquaParkExcursion);
        assertThat(excursionDao.findByName("Aqua park").size(),is(1));
        assertThat(excursionDao.findByName("NonExist Name")).isEmpty();
    }
    
    @Test(expected = NullPointerException.class)
    public void testFindByNullName() {
        excursionDao.findByName(null);
    }

    /**
     * Test of findByPrice where the price from is greater than price to.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFindByPriceWithNegativeDifference() {
        excursionDao.findByPrice(new BigDecimal("200.00"), new BigDecimal("199.99"));
    }
    
    @Test(expected = NullPointerException.class)
    public void testFindByNullFromPrice() {
        excursionDao.findByPrice(null, BigDecimal.TEN);
    }
    
    @Test(expected = NullPointerException.class)
    public void testFindByNullToPrice() {
        excursionDao.findByPrice(BigDecimal.ONE, null);
    }
    
    @Test
    public void testFindByPrice() {
        assertThat(excursionDao.findByPrice(new BigDecimal("200"), new BigDecimal("500")))
                                                .isEmpty();
        excursionDao.create(aquaParkExcursion);
        excursionDao.create(artGalleryExcursion);
        excursionDao.create(bigHillExcursion);
        assertThat(excursionDao.findByPrice(new BigDecimal("200"), new BigDecimal("500")))
                .usingFieldByFieldElementComparator()
                .containsOnly(aquaParkExcursion, artGalleryExcursion);
        assertThat(excursionDao.findByPrice(new BigDecimal("200.01"), new BigDecimal("500")))
                .usingFieldByFieldElementComparator()
                .containsOnly(aquaParkExcursion);
    }

    
    
    /**
     * Test of findByDate method, of class ExcursionDaoImpl.
     */
    @Test
    public void testFindByDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 10, 20);
        Date dateFrom = cal.getTime();
        cal.set(2017, 10, 22);
        Date dateTo = cal.getTime();
        assertThat(excursionDao.findByDate(dateFrom, dateTo)).isEmpty();
        cal.set(2017, 10, 21);
        Date dateOfExcursion = cal.getTime();
        aquaParkExcursion.setDate(dateOfExcursion);
        excursionDao.create(aquaParkExcursion);
        cal.set(2017, 10, 23);
        dateOfExcursion = cal.getTime();
        artGalleryExcursion.setDate(dateOfExcursion);
        excursionDao.create(artGalleryExcursion);
        assertThat(excursionDao.findByDate(dateFrom, dateTo))
                .usingFieldByFieldElementComparator()
                .containsOnly(aquaParkExcursion);
                
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFindByDateFromAfterDateTo() {
        Calendar cal = (Calendar) calNow.clone();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date dateTo = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date dateFrom = cal.getTime();
        excursionDao.findByDate(dateFrom, dateTo);
    }

    /**
     * Test of findByDestination method, of class ExcursionDaoImpl.
     */
    @Test
    public void testFindByDestination() {
        
        assertThat(excursionDao.findByDestination("Pasohlavky")).isEmpty();
        excursionDao.create(aquaParkExcursion);
        assertThat(excursionDao.findByDestination("Pasohlavky"))
                .usingFieldByFieldElementComparator()
                .containsOnly(aquaParkExcursion);
        
    }

    /**
     * Test of findByDuration method, of class ExcursionDaoImpl.
     */
    @Test
    public void testFindByDuration() {
        Calendar cal = Calendar.getInstance();
        cal.set(0, 0, 0, 5, 0, 0);
        Date durFrom = cal.getTime();
        cal.set(0, 0, 0, 8, 0, 0);
        Date durTo = cal.getTime();
        assertThat(excursionDao.findByDuration(durFrom, durTo)).isEmpty();
        cal.set(0, 0, 0, 5, 0, 1);
        Date durOfExcursion = cal.getTime();
        aquaParkExcursion.setDuration(durOfExcursion);
        excursionDao.create(aquaParkExcursion);
        cal.set(0, 0, 0, 8, 0, 1);
        durOfExcursion = cal.getTime();
        artGalleryExcursion.setDuration(durOfExcursion);
        excursionDao.create(artGalleryExcursion);
        assertThat(excursionDao.findByDuration(durFrom, durTo))
                .usingFieldByFieldElementComparator()
                .containsOnly(aquaParkExcursion);
        
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFindByDurationFromLongerThanDurationTo() {
        Calendar cal = Calendar.getInstance();
        cal.set(0, 0, 0, 8, 0 , 1);
        Date durationFrom = cal.getTime();
        cal.set(0, 0, 0, 8, 0, 0);
        Date durationTo = cal.getTime();
        excursionDao.findByDuration(durationFrom, durationTo);
    }

    /**
     * Test of findByTrip method, of class ExcursionDaoImpl.
     */
    @Test
    public void testFindByTrip() {
        assertThat(excursionDao.findByTrip(waterTrip)).isEmpty();
        excursionDao.create(aquaParkExcursion);
        assertThat(excursionDao.findByTrip(waterTrip))
                .usingFieldByFieldElementComparator()
                .containsOnly(aquaParkExcursion);
        
    }

    /**
     * Test of create method, of class ExcursionDaoImpl.
     */
    @Test
    @Transactional
    public void testCreate() {
        assertThat(excursionDao.findAll()).isEmpty();
        excursionDao.create(bigHillExcursion);
        assertThat(bigHillExcursion.getId()).isNotNull();
        assertThat(em.find(Excursion.class, bigHillExcursion.getId()))
                .isEqualToComparingFieldByField(bigHillExcursion);
        assertThat(excursionDao.findAll().size(),is(1));
        
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithNullName() {
        bigHillExcursion.setName(null);
        excursionDao.create(bigHillExcursion);
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithNullTrip() {
        bigHillExcursion.setTrip(null);
        excursionDao.create(bigHillExcursion);
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithNullDate() {
        bigHillExcursion.setDate(null);
        excursionDao.create(bigHillExcursion);
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithNullDuration() {
        bigHillExcursion.setDuration(null);
        excursionDao.create(bigHillExcursion);
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithNullDestination() {
        bigHillExcursion.setDestination(null);
        excursionDao.create(bigHillExcursion);
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithNonNullId() {
        bigHillExcursion.setId(Long.valueOf("1"));
        excursionDao.create(bigHillExcursion);
    }

    /**
     * Test of update method, of class ExcursionDaoImpl.
     */
    @Test
    public void testUpdate() {
        excursionDao.create(bigHillExcursion);
        excursionDao.create(aquaParkExcursion);
        
        aquaParkExcursion.setName("Aqualand");
        excursionDao.update(aquaParkExcursion);
        
        assertThat(em.find(Excursion.class, aquaParkExcursion.getId()))
                .isEqualToComparingFieldByField(aquaParkExcursion);
        assertThat(em.find(Excursion.class, bigHillExcursion.getId()))
                .isEqualToComparingFieldByField(bigHillExcursion);
    }

    /**
     * Test of delete method, of class ExcursionDaoImpl.
     */
    @Test
    public void testDelete() {
        excursionDao.create(bigHillExcursion);
        excursionDao.create(aquaParkExcursion);
        excursionDao.create(artGalleryExcursion);
        
        assertThat(excursionDao.findAll().size(),is(3));
        
        excursionDao.delete(bigHillExcursion);
        
        assertThat(excursionDao.findAll().size(),is(2));
        assertThat(excursionDao.findAll())
                .usingFieldByFieldElementComparator()
                .containsOnly(aquaParkExcursion,artGalleryExcursion);
        
    }

    /**
     * Test of findById method, of class ExcursionDaoImpl.
     */
    @Test
    public void testFindById() {
        assertThat(excursionDao.findById(Long.MIN_VALUE)).isNull();
        excursionDao.create(aquaParkExcursion);
        excursionDao.create(artGalleryExcursion);
        assertThat(excursionDao.findById(artGalleryExcursion.getId()))
                .isEqualToComparingFieldByField(artGalleryExcursion);
        
    }

    /**
     * Test of findAll method, of class ExcursionDaoImpl.
     */
    @Test
    public void testFindAll() {
        assertThat(excursionDao.findAll()).isEmpty();
        
        excursionDao.create(bigHillExcursion);
        excursionDao.create(artGalleryExcursion);
        
        assertThat(excursionDao.findAll())
                .usingFieldByFieldElementComparator()
                .containsOnly(bigHillExcursion, artGalleryExcursion);
    }
    
}
