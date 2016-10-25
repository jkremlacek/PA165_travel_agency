package cz.muni.fi.pa165.travelagency.persistance.dao;

import cz.muni.fi.pa165.travelagency.persistance.config.InMemorySpring;
import cz.muni.fi.pa165.travelagency.persistance.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistance.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistance.entity.Trip;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Testing DAO implementation of Trip 
 * @author Katerina Caletkova
 */

@ContextConfiguration(classes = InMemorySpring.class)
public class TripDaoImplTest extends AbstractTestNGSpringContextTests {
    
    @PersistenceUnit(name = "default")
    private EntityManagerFactory emf;
    
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
    
    @BeforeClass
    public void setup() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,9,20);
        Date dateFrom = calendar.getTime();
        calendar.set(2017,9,28);
        Date dateTo = calendar.getTime();
        trip1 = new Trip("Podzim ve Francii",dateFrom, dateTo, "Francie", 20, BigDecimal.valueOf(5000));
        
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

    }

    @Test
    public void TestDelete() throws Exception {

    }

    @Test
    public void TestFindById() throws Exception {

    }

    @Test
    public void TestFindAll() throws Exception {
    
    }
    
    @Test
    public void TestFindByName() throws Exception {
        
    }
    
    @Test
    public void TestFindByDate() throws Exception {
        
    }
    
    @Test
    public void TestFindByDestination() throws Exception {
        
    }
    
    @Test
    public void TestFindByPrice() throws Exception {
        
    }
    
    @Test
    public void TestFindByCapacity() throws Exception {
        
    }
    
    @Test
    public void TestFindByExcursions() throws Exception {
        
    }                          
}
