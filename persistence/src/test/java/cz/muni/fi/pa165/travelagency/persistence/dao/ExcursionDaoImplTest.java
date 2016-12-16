package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.config.InMemorySpring;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


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
        cal.set(2121, 10, 1);
        Date startDay = cal.getTime();
        cal = (Calendar) calNow.clone();
        cal.set(2121, 10, 31);
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
        cal.set(2121, 10, 15);
        startDay = cal.getTime();
        cal.set(0, 0, 0, 7, 0);
        Integer duration = 5;
        aquaParkExcursion = new Excursion();
        aquaParkExcursion.setName("Aqua park");
        aquaParkExcursion.setDate(startDay);
        aquaParkExcursion.setDuration(duration);
        aquaParkExcursion.setDestination("Pasohlavky");
        aquaParkExcursion.setPrice(BigDecimal.valueOf(500));
        aquaParkExcursion.setDescription("Nice excursion to nice crowded aqua park");
        aquaParkExcursion.setTrip(waterTrip);
        
        calNow = Calendar.getInstance();
        cal.set(2100, 10, 1);
        startDay = cal.getTime();
        cal = (Calendar) calNow.clone();
        cal.set(2100, 10, 31);
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
        cal.set(2100, 10, 12);
        startDay = cal.getTime();
        cal.set(0, 0, 0, 2, 0);
        duration = 6;
        artGalleryExcursion = new Excursion();
        artGalleryExcursion.setName("Art gallery");
        artGalleryExcursion.setDate(startDay);
        artGalleryExcursion.setDuration(duration);
        artGalleryExcursion.setDestination("Prague");
        artGalleryExcursion.setPrice(BigDecimal.valueOf(200));
        artGalleryExcursion.setDescription("Nice excursion to nice art gallery");
        artGalleryExcursion.setTrip(pragueBrnoTrip);
        
        
        cal = (Calendar) calNow.clone();
        cal.set(2100, 10, 10);
        startDay = cal.getTime();
        cal.set(0, 0, 0, 18, 30);
        duration = 7;
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
                    .as("it should be able to find by name")
                    .contains(aquaParkExcursion);
        assertThat(excursionDao.findByName("Aqua park").size(),is(1));
        assertThat(excursionDao.findByName("NonExist Name")).isEmpty();
    }

    /**
     * Test of findByPrice where the price from is greater than price to.
     */
    @Test
    public void testFindByPriceWithNegativeDifference() {
        assertThatThrownBy(() -> excursionDao.findByPrice(new BigDecimal("200.00"), new BigDecimal("199.99")))
                .as("cannot find excursions with start price greater than end price")
                .isInstanceOf(IllegalArgumentException.class);
    }
    
    @Test
    public void testFindByPrice() {
        assertThat(excursionDao.findByPrice(new BigDecimal("200"), new BigDecimal("500")))
                .as("There shouldn't be any excursions!")
                .isEmpty();
        excursionDao.create(aquaParkExcursion);
        excursionDao.create(artGalleryExcursion);
        excursionDao.create(bigHillExcursion);
        assertThat(excursionDao.findByPrice(new BigDecimal("200"), new BigDecimal("500")))
                .as("There should be only two excursions!")
                .usingFieldByFieldElementComparator()
                .containsOnly(aquaParkExcursion, artGalleryExcursion);
        assertThat(excursionDao.findByPrice(new BigDecimal("200.01"), new BigDecimal("500")))
                .as("There should be only one excursion")
                .usingFieldByFieldElementComparator()
                .containsOnly(aquaParkExcursion);
    }

    
    
    /**
     * Test of findByDate method, of class ExcursionDaoImpl.
     */
    @Test
    public void testFindByDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(2100, 10, 20);
        Date dateFrom = cal.getTime();
        cal.set(2100, 10, 22);
        Date dateTo = cal.getTime();
        assertThat(excursionDao.findByDate(dateFrom, dateTo))
                .as("There should be no excursion found by findByDate")
                .isEmpty();
        cal.set(2121, 10, 21);
        Date dateOfExcursion = cal.getTime();
        aquaParkExcursion.setDate(dateOfExcursion);
        excursionDao.create(aquaParkExcursion);
        cal.set(2100, 10, 13);
        dateOfExcursion = cal.getTime();
        artGalleryExcursion.setDate(dateOfExcursion);
        excursionDao.create(artGalleryExcursion);
        assertThat(excursionDao.findByDate(dateOfExcursion, dateTo))
                .as("It should be one excursion found by findByDate")
                .usingFieldByFieldElementComparator()
                .containsOnly(artGalleryExcursion);
                
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
        
        assertThat(excursionDao.findByDestination("Pasohlavky"))
                .as("There should be no excursion found by findByDestination")
                .isEmpty();
        excursionDao.create(aquaParkExcursion);
        assertThat(excursionDao.findByDestination("Pasohlavky"))
                .as("There should be one excursion found by findByDestination")
                .usingFieldByFieldElementComparator()
                .containsOnly(aquaParkExcursion);
        
    }

//    /**
//     * Test of findByDuration method, of class ExcursionDaoImpl.
//     */
//    @Test
//    public void testFindByDuration() {
//        Calendar cal = Calendar.getInstance();
//        cal.set(0, 0, 0, 5, 0, 0);
//        Date durFrom = cal.getTime();
//        cal.set(0, 0, 0, 8, 0, 0);
//        Date durTo = cal.getTime();
//        assertThat(excursionDao.findByDuration(durFrom, durTo))
//                .as("There should be one excursion found by findByDuration")
//                .isEmpty();
//        cal.set(0, 0, 0, 5, 0, 1);
//        Date durOfExcursion = cal.getTime();
//        aquaParkExcursion.setDuration(durOfExcursion);
//        excursionDao.create(aquaParkExcursion);
//        cal.set(0, 0, 0, 8, 0, 1);
//        durOfExcursion = cal.getTime();
//        artGalleryExcursion.setDuration(durOfExcursion);
//        excursionDao.create(artGalleryExcursion);
//        assertThat(excursionDao.findByDuration(durFrom, durTo))
//                .as("There should be one excursion found by findByDuration")
//                .usingFieldByFieldElementComparator()
//                .containsOnly(aquaParkExcursion);
//
//    }
    
//    @Test(expected = IllegalArgumentException.class)
//    public void testFindByDurationFromLongerThanDurationTo() {
//        Calendar cal = Calendar.getInstance();
//        cal.set(0, 0, 0, 8, 0 , 1);
//        Date durationFrom = cal.getTime();
//        cal.clear();
//        cal.set(0, 0, 0, 8, 0, 0);
//        Date durationTo = cal.getTime();
//        excursionDao.findByDuration(durationFrom, durationTo);
//    }

    /**
     * Test of findByTrip method, of class ExcursionDaoImpl.
     */
    @Test
    public void testFindByTrip() {
        assertThat(excursionDao.findByTrip(waterTrip))
                .as("There should be no excursion found by findByTrip")
                .isEmpty();
        excursionDao.create(aquaParkExcursion);
        assertThat(excursionDao.findByTrip(waterTrip))
                .as("There should be one excursion found by findByTrip")
                .usingFieldByFieldElementComparator()
                .containsOnly(aquaParkExcursion);
        
    }

    /**
     * Test of create method, of class ExcursionDaoImpl.
     */
    @Test
    @Transactional
    public void testCreate() {
        assertThat(excursionDao.findAll())
                .as("There should be no excursion")
                .isEmpty();
        excursionDao.create(bigHillExcursion);
        assertThat(bigHillExcursion.getId())
                .as("Persisted excursion should have assigned id")
                .isNotNull();
        assertThat(em.find(Excursion.class, bigHillExcursion.getId()))
                .as("Persisted excursion should be accessible by entity manager")
                .isEqualToComparingFieldByField(bigHillExcursion);
        assertThat(excursionDao.findAll())
                .as("We should persisted just only one excursion")
                .containsOnly(bigHillExcursion);
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
    
    @Test(expected = PersistenceException.class)
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
                .as("Updated excursion should have new name")
                .isEqualToComparingFieldByField(aquaParkExcursion);
        assertThat(em.find(Excursion.class, bigHillExcursion.getId()))
                .as("Update shouldn't have an effect to others entities!")
                .isEqualToComparingFieldByField(bigHillExcursion);
    }
    
    @Test(expected = ValidationException.class)
    public void testUpdateWithNullName() {
        excursionDao.create(bigHillExcursion);
        bigHillExcursion.setName(null);
        excursionDao.update(bigHillExcursion);
    }
    
    @Test(expected = ValidationException.class)
    public void testUpdateWithNullTrip() {
        excursionDao.create(bigHillExcursion);
        bigHillExcursion.setTrip(null);
        excursionDao.update(bigHillExcursion);
    }
    
    @Test(expected = ValidationException.class)
    public void testUpdateWithNullDate() {
        excursionDao.create(bigHillExcursion);
        bigHillExcursion.setDate(null);
        excursionDao.update(bigHillExcursion);
    }
    
    @Test(expected = ValidationException.class)
    public void testUpdateWithNullDuration() {
        excursionDao.create(bigHillExcursion);
        bigHillExcursion.setDuration(null);
        excursionDao.update(bigHillExcursion);
    }
    
    @Test(expected = ValidationException.class)
    public void testUpdateWithNullDestination() {
        excursionDao.create(bigHillExcursion);
        bigHillExcursion.setDestination(null);
        excursionDao.update(bigHillExcursion);
    }
    
    @Test(expected = NullPointerException.class)
    public void testUpdateWithNullId() {
        excursionDao.create(bigHillExcursion);
        bigHillExcursion.setId(null);
        excursionDao.update(bigHillExcursion);
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
        
        assertThat(excursionDao.findAll())
            .as("Delete should delete only given excursion and has no effect to other entities")
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
                .as("It should be able to found an existing entity")
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
                .as("There should be two excursions")
                .usingFieldByFieldElementComparator()
                .containsOnly(bigHillExcursion, artGalleryExcursion);
    }
        
    @Test
    public void testNullArguments() {
        assertThatThrownBy(() -> excursionDao.create(null))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> excursionDao.update(null))
                .as("update(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> excursionDao.delete(null))
                .as("delete(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> excursionDao.findById(null))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> excursionDao.findByName(null))
                .as("findByName(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> excursionDao.findByPrice(null, BigDecimal.TEN))
                .as("findByPrice(null, bigDecimal) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> excursionDao.findByPrice(BigDecimal.TEN, null))
                .as("findByPrice(bigDecimal, null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> excursionDao.findByDate(null, calNow.getTime()))
                .as("findByDate(null, date) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> excursionDao.findByDate(calNow.getTime(), null))
                .as("findByDate(date, null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> excursionDao.findByDestination(null))
                .as("findByDestination(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
//        assertThatThrownBy(() -> excursionDao.findByDuration(null, calNow.getTime()))
//                .as("findByDuration(null, date) should throw NullPointerException")
//                .isInstanceOf(NullPointerException.class);
//        assertThatThrownBy(() -> excursionDao.findByDuration(calNow.getTime(), null))
//                .as("findByDuration(date, null) should throw NullPointerException")
//                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> excursionDao.findByTrip(null))
                .as("findByTrip(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
    }
    
}
