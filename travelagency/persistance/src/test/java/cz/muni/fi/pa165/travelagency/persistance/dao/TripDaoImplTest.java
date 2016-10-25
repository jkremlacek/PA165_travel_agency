package cz.muni.fi.pa165.travelagency.persistance.dao;

import cz.muni.fi.pa165.travelagency.persistance.config.InMemorySpring;
import cz.muni.fi.pa165.travelagency.persistance.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistance.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistance.entity.Trip;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Testing DAO implementation of Trip 
 * @author Katerina Caletkova
 */

@ContextConfiguration(classes = InMemorySpring.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TripDaoImplTest extends AbstractTestNGSpringContextTests {
    
    @Inject
    private TripDao tripDao;
    @Inject
    private TripDao excursionDao;
    @Inject
    private TripDao reservationDao;
            
    private Trip trip1;
    private Trip trip2;
    private Excursion excursion1;
    private Excursion excursion2;
    private Reservation reservation1;
    private Reservation reservation2;
    
    @BeforeTest
    public void setup() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,9,20);
        Date dateFrom = calendar.getTime();
        calendar.set(2017,9,28);
        Date dateTo = calendar.getTime();
        trip1 = new Trip("Podzim ve Francii",dateFrom, dateTo, "Francie", 20, BigDecimal.valueOf(5000));
        System.out.println(trip1.toString());
        calendar.set(2017,5,12);
        dateFrom = calendar.getTime();
        calendar.set(2017,5,26);
        dateTo = calendar.getTime();
        trip2 = new Trip("Odpocinek na Malte",dateFrom, dateTo, "Malta", 55, BigDecimal.valueOf(12000));

    }
    
    @Test
    public void TestCreate() throws Exception {       
        assertTrue(tripDao.findAll().isEmpty());
        tripDao.create(trip1);        
        assertNotNull(trip1.getId());
        assertEquals(tripDao.findAll().size(), 1);
        tripDao.create(trip2);
        assertEquals(tripDao.findAll().size(), 2);
    }

    @Test
    public void TestUpdate() throws Exception {
        tripDao.create(trip1);
        assertEquals(trip1.getDestination(), "Francie");
        trip1.setDestination("Italie");
        tripDao.update(trip1);
         assertEquals(trip1.getDestination(), "Italie");
    }

    @Test
    public void TestDelete() throws Exception {
        tripDao.create(trip1);
        assertFalse(tripDao.findAll().isEmpty());
        tripDao.delete(trip1);
        assertTrue(tripDao.findAll().isEmpty());
    }

    @Test
    public void TestFindById() throws Exception {
        tripDao.create(trip1);
        assertEquals(tripDao.findById(trip1.getId()),trip1);
    }

    @Test
    public void TestFindAll() throws Exception {
        assertTrue(tripDao.findAll().isEmpty());
        tripDao.create(trip1);
        tripDao.create(trip2);
        assertEquals(tripDao.findAll().size(), 2);
    }
    
    @Test
    public void TestFindByName() throws Exception {
        tripDao.create(trip1);
        assertEquals(tripDao.findByName(trip1.getName()),trip1);
    }
    
    @Test
    public void TestFindByDate() throws Exception {
        tripDao.create(trip1);
        assertEquals(tripDao.findByDate(trip1.getDateFrom(),trip1.getDateTo()),trip1);
    }
    
    @Test
    public void TestFindByDestination() throws Exception {
        tripDao.create(trip1);
        assertEquals(tripDao.findByDestination(trip1.getDestination()),trip1);
    }
    
    @Test
    public void TestFindByPrice() throws Exception {
        tripDao.create(trip1);
        assertEquals(tripDao.findByPrice(trip1.getPrice().subtract(BigDecimal.valueOf(10)),trip1.getPrice().add(BigDecimal.valueOf(10))),trip1);
    }
    
    @Test
    public void TestFindByCapacity() throws Exception {
        tripDao.create(trip1);
        assertEquals(tripDao.findByCapacity(trip1.getCapacity()),trip1);
    }
    
    @Test
    public void TestFindByExcursions() throws Exception {
        // TODO dodelat az to bude opravene v TripDao a v TripDaoImpl
    }                          
}
