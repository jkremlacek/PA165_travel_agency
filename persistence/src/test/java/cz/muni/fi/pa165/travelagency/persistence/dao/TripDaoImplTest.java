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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Testing DAO implementation of Trip 
 * @author Katerina Caletkova
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemorySpring.class)
@Transactional
public class TripDaoImplTest {
    
    @Inject
    private TripDao tripDao;
    
    @PersistenceContext
    private EntityManager em;
    
    private Trip trip1;
    private Trip trip2;
    private Excursion excursion1;
    
    @Before
    public void setup() {
        
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,9,20);
        Date dateFrom1 = calendar.getTime();
        calendar.set(2017,9,28);
        Date dateTo1 = calendar.getTime();
        
        trip1 = new Trip("Podzim ve Francii",dateFrom1, dateTo1, "Francie", 20, BigDecimal.valueOf(5000));           
        calendar.set(0,0,0,5,0,0);
        Date excursionDuration = calendar.getTime();
        excursion1 = new Excursion("Vylet do Parize",dateFrom1, excursionDuration, "Pariz",BigDecimal.valueOf(500));
        calendar.set(2017,5,12);
        Date dateFrom2 = calendar.getTime();
        calendar.set(2017,5,26);
        Date dateTo2 = calendar.getTime();
        trip2 = new Trip("Odpocinek na Malte",dateFrom2, dateTo2, "Malta", 55, BigDecimal.valueOf(12000)); 
    }
    
    @Test
    public void testCreate() throws Exception {        
        assertTrue(tripDao.findAll().isEmpty());
        tripDao.create(trip1);        
        assertNotNull(trip1.getId());
        assertEquals(tripDao.findAll().size(), 1);
        tripDao.create(trip2);
        assertEquals(tripDao.findAll().size(), 2);
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithNullValueName() {
        trip1.setName(null);
        tripDao.create(trip1);
    }
    
    @Test(expected = NullPointerException.class)
    public void testCreateWithNullValueDateFrom() {
        trip1.setDateFrom(null);
        tripDao.create(trip1);
    }
    @Test(expected = NullPointerException.class)
    public void testCreateWithNullValueDateTo() {
        trip1.setDateTo(null);
        tripDao.create(trip1);
    }
    @Test(expected = ValidationException.class)
    public void testCreateWithNullValueDestination() {
        trip1.setDestination(null);
        tripDao.create(trip1);
    }
    @Test(expected = NullPointerException.class)
    public void testCreateWithNullValueCapacity() {
        trip1.setCapacity(null);
        tripDao.create(trip1);
    }
    @Test(expected = NullPointerException.class)
    public void testCreateWithNullValuePrice() {
        trip1.setPrice(null);
        tripDao.create(trip1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNegativeValueCapacity() {
        trip1.setCapacity(-10);
        tripDao.create(trip1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNegativeValuePrice() {
        trip1.setPrice(BigDecimal.valueOf(-10));
        tripDao.create(trip1);
    }
    
    @Test(expected = Exception.class)
    public void testCreateWithNonNullValueId() {
        trip1.setId(Long.valueOf("123"));
        tripDao.create(trip1);
    }

    @Test
    public void testUpdate() throws Exception {
        tripDao.create(trip1);
        assertEquals(trip1.getDestination(), "Francie");
        trip1.setDestination("Italie");
        tripDao.update(trip1);
         assertEquals(trip1.getDestination(), "Italie");
    }

    @Test
    public void testDelete() throws Exception {
        tripDao.create(trip1);   
        tripDao.create(trip2);      
        assertEquals(tripDao.findAll().size(), 2);
        tripDao.delete(trip1);       
        assertEquals(tripDao.findAll().size(), 1);
        tripDao.delete(trip2);   
        assertTrue(tripDao.findAll().isEmpty());
    }

    @Test
    public void testFindById() throws Exception {
        tripDao.create(trip1);   
        tripDao.create(trip2);  
        assertEquals(tripDao.findById(trip1.getId()),trip1);
    }

    @Test
    public void testFindAll() throws Exception {
       assertTrue(tripDao.findAll().isEmpty());
       tripDao.create(trip1);      
       assertEquals(tripDao.findAll().size(), 1);
       tripDao.create(trip2);
       assertEquals(tripDao.findAll().size(), 2);
    }
    
    @Test
    public void testFindByName() throws Exception {
        tripDao.create(trip1);
        assertThat(tripDao.findByName(trip1.getName()))
                .usingFieldByFieldElementComparator().containsOnly(trip1);
    }
    
    @Test(expected = NullPointerException.class)
    public void testFindByNameWithNullValue() {
        tripDao.findByName(null);
    }
    
    @Test
    public void testFindByDate() throws Exception {
        assertThat(tripDao.findByDate(trip1.getDateFrom(), trip1.getDateTo())).isEmpty();
        tripDao.create(trip1);                   
        assertThat(tripDao.findByDate(trip1.getDateFrom(), trip1.getDateTo()))
            .usingFieldByFieldElementComparator().containsOnly(trip1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFindByDateWithWrongRange() {
        tripDao.findByDate(trip1.getDateTo(), trip1.getDateFrom());
    }
    
    @Test
    public void testFindByDestination() throws Exception {
        tripDao.create(trip1);
        assertThat(tripDao.findByDestination(trip1.getDestination()))
            .usingFieldByFieldElementComparator().containsOnly(trip1);
    }
    
    @Test
    public void testFindByPrice() throws Exception {
        tripDao.create(trip1);        
        assertThat(tripDao.findByPrice(trip1.getPrice().subtract(BigDecimal.valueOf(10)),trip1.getPrice().add(BigDecimal.valueOf(10))))
            .usingFieldByFieldElementComparator().containsOnly(trip1);    
        assertThat(tripDao.findByPrice(trip1.getPrice(),trip1.getPrice()))
            .usingFieldByFieldElementComparator().containsOnly(trip1);

    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testFindByPriceWithWrongRange() {
        tripDao.create(trip1);
        tripDao.findByPrice(new BigDecimal("100"), new BigDecimal("50"));
    }
    
    @Test(expected = NullPointerException.class)
    public void testFindByPriceWithNullValueMinPrice() {
        tripDao.create(trip1);
        tripDao.findByPrice(new BigDecimal("100"), null);        
    }
    
    @Test(expected = NullPointerException.class)
    public void testFindByPriceWithNullValueMaxPrice() {
        tripDao.create(trip1);
        tripDao.findByPrice(null, new BigDecimal("100"));        
    }
    
    @Test
    public void testFindByCapacity() throws Exception {
        tripDao.create(trip1);        
        assertThat(tripDao.findByCapacity(trip1.getCapacity()))
        .usingFieldByFieldElementComparator().containsOnly(trip1);
    }
    
    @Test
    public void testFindByExcursion() throws Exception {  
        em.persist(excursion1);   
        tripDao.create(trip2);  
        assertThat(tripDao.findByExcursion(excursion1)).isEmpty();
        assertThat(tripDao.findByExcursion(excursion1)).hasSize(0);
        
        excursion1.setTrip(trip2);
        trip2.addExcursion(excursion1);  
        em.merge(excursion1);     
        tripDao.update(trip2);       
        assertThat(tripDao.findByExcursion(excursion1)).hasSize(1);  
        assertThat(tripDao.findByExcursion(excursion1))
            .usingFieldByFieldElementComparator().containsOnly(trip2);        
    } 
    
    @Test
    public void nullAttributes() {
        assertThatThrownBy(() -> tripDao.create(null))                
            .isInstanceOf(NullPointerException.class);
        
        assertThatThrownBy(() -> tripDao.update(null))                
            .isInstanceOf(NullPointerException.class);
        
        assertThatThrownBy(() -> tripDao.delete(null))                
            .isInstanceOf(NullPointerException.class);
        
        assertThatThrownBy(() -> tripDao.findByName(null))                
            .isInstanceOf(NullPointerException.class);
        
         assertThatThrownBy(() -> tripDao.findByDate(null, null))                
            .isInstanceOf(NullPointerException.class);
         
         assertThatThrownBy(() -> tripDao.findByDestination(null))                
            .isInstanceOf(NullPointerException.class); 
         
         assertThatThrownBy(() -> tripDao.findByPrice(null, null))                
            .isInstanceOf(NullPointerException.class);
                  
         assertThatThrownBy(() -> tripDao.findByCapacity(null))                
            .isInstanceOf(NullPointerException.class);
         
         assertThatThrownBy(() -> tripDao.findByExcursion(null))                
            .isInstanceOf(NullPointerException.class);         
    }
    
}
