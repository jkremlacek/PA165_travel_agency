package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import java.math.BigDecimal;
import java.util.Calendar;
import javax.persistence.EntityExistsException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.hibernate.service.spi.ServiceException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Matchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Tests for service layer of reservation entity
 * @author Katerina Caletkova
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class ReservationServiceTest {
    
    @Mock
    private ReservationDao reservationDao;
    
    @InjectMocks
    private final ReservationService reservationService = new ReservationServiceImpl();
    
    private Reservation reservation1;
    private Trip trip1;
    private Trip trip2;
    private User user1;
    private Excursion excursion1; 
    private Excursion excursion2; 
    
    @Before
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    
        Calendar calendar = Calendar.getInstance();
        user1 = new User(1l);
        calendar.set(1991,1,1);
        user1.setBirthDate(calendar.getTime());
        calendar.clear();
        user1.setIsAdmin(Boolean.FALSE);
        user1.setMail("mail@mail.com");
        user1.setName("Jan");
        user1.setPasswordHash("heslo");
        user1.setPersonalNumber(123456789l);
        user1.setPhoneNumber(777777777);
        
        User user2 = new User(2l);
        calendar.set(1990,5,5);
        user2.setBirthDate(calendar.getTime());
        calendar.clear();
        user2.setIsAdmin(Boolean.FALSE);
        user2.setMail("m@mail.com");
        user2.setName("Jana");
        user2.setPasswordHash("mojeheslo");
        user2.setPersonalNumber(111111111l);
        user2.setPhoneNumber(777777776);
        
        trip1 = new Trip(1l);
        trip1.setCapacity(20);
        calendar.set(2017,1,20);
        trip1.setDateFrom(calendar.getTime());
        calendar.clear();
        calendar.set(2017,1,28);
        trip1.setDateTo(calendar.getTime());
        calendar.clear();
        trip1.setDestination("Italie");
        trip1.setName("Dovolena u more");
        trip1.setPrice(BigDecimal.valueOf(5000));
        
        trip2 = new Trip(2l);
        trip2.setCapacity(10);
        calendar.set(2017,2,20);
        trip2.setDateFrom(calendar.getTime());
        calendar.clear();
        calendar.set(2017,2,28);
        trip2.setDateTo(calendar.getTime());
        calendar.clear();
        trip2.setDestination("Francie");
        trip2.setName("Dovolena ve Francii");
        trip2.setPrice(BigDecimal.valueOf(8000));
       
        excursion1 = new Excursion(1l);
        calendar.set(2017,1,21);
        excursion1.setDate(calendar.getTime());
        calendar.clear();
        excursion1.setDestination("Rim");
        calendar.set(0, 0, 0, 5, 0, 0);
        excursion1.setDuration(calendar.getTime());
        calendar.clear();
        excursion1.setName("Vylet do Rima");
        excursion1.setPrice(BigDecimal.valueOf(80));
        excursion1.setTrip(trip1);
        trip1.addExcursion(excursion1);
        
        excursion2 = new Excursion(2l);
        calendar.set(2017,1,22);
        excursion2.setDate(calendar.getTime());
        calendar.clear();
        excursion2.setDestination("Milano");
        calendar.set(0, 0, 0, 4, 0, 0);
        excursion2.setDuration(calendar.getTime());
        calendar.clear();
        excursion2.setName("Vylet do Milana");
        excursion2.setPrice(BigDecimal.valueOf(100));
        excursion2.setTrip(trip1);
        trip1.addExcursion(excursion2);
        
        reservation1 = new Reservation();
        reservation1.setId(1l);
    }
   
    @Test
    public void testCreateOk() throws Exception {
        reservation1.setTrip(trip1);
        reservation1.setUser(user1);
        reservationService.create(reservation1);
        verify(reservationDao).create(reservation1);
    }
    
    @Test
    public void testCreateNull() {
        doThrow(new NullPointerException()).when(reservationDao).create(null);
        assertThatThrownBy(() -> reservationService.create(null))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    public void testCreateDataAccessError() { 
        doThrow(new EntityExistsException()).when(reservationDao).create(any());
        assertThatThrownBy(() -> reservationService.create(any()))
                .isInstanceOf(DataAccessException.class);
    }
    
    @Test
    public void testUpdateOk() throws Exception {
        reservation1.setTrip(trip1);
        reservation1.setUser(user1);
        reservationService.create(reservation1);
        reservation1.setTrip(trip2);
        reservationService.update(reservation1);
        verify(reservationDao).update(reservation1);
    }
    
    @Test
    public void testUpdateNull() {
        doThrow(new NullPointerException()).when(reservationDao).update(null);
        assertThatThrownBy(() -> reservationService.update(null))
                .as("update(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    public void testUpdateDataAccessError() { 
        doThrow(new EntityExistsException()).when(reservationDao).update(any());
        assertThatThrownBy(() -> reservationService.update(any()))               
                .isInstanceOf(DataAccessException.class);
    }
    
    
    @Test
    public void testDeleteOk() throws Exception {
        reservation1.setTrip(trip1);
        reservation1.setUser(user1);
        reservationService.create(reservation1);
        reservationService.delete(reservation1);
        verify(reservationDao).delete(reservation1);
    }
    
    @Test
    public void testDeleteNull() {
        doThrow(new NullPointerException()).when(reservationDao).delete(null);
        assertThatThrownBy(() -> reservationService.delete(null))
                .as("delete(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    public void testDeleteDataAccessError() {
        doThrow(new EntityExistsException()).when(reservationDao).delete(any());
        assertThatThrownBy(() -> reservationService.delete(any()))
                .isInstanceOf(DataAccessException.class);
    }
   
    @Test
    public void testFindByIdOk() throws Exception {        
        reservationService.findById(reservation1.getId());
        verify(reservationDao).findById(reservation1.getId());
    }
    
    @Test
    public void testFindByIdNull() {
        doThrow(new NullPointerException()).when(reservationDao).findById(null);
        assertThatThrownBy(() -> reservationService.findById(null))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    public void testFindByIdDataAccessError() {
       doThrow(new EntityExistsException()).when(reservationDao).findById(any());
        assertThatThrownBy(() -> reservationService.findById(any()))
                .isInstanceOf(DataAccessException.class);
    }
    
    @Test
    public void testFindAllOk() throws Exception {
        reservationService.findAll();
        verify(reservationDao).findAll();
    } 
    
    
    @Test
    public void testFindByUserOk() throws Exception {
        reservationService.findByUser(user1);
        verify(reservationDao).findByUser(user1);
    }
    
    @Test
    public void testFindByUserNull() {
        doThrow(new NullPointerException()).when(reservationDao).findByUser(null);
        assertThatThrownBy(() -> reservationService.findByUser(null))
                .as("findByUser(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    public void testFindByUserDataAccessError() {
        doThrow(new EntityExistsException()).when(reservationDao).findByUser(any());
        assertThatThrownBy(() -> reservationService.findByUser(any()))
                .isInstanceOf(DataAccessException.class);
    }
    
    @Test
    public void testFindByTripOk() throws Exception {
        reservationService.findByTrip(trip1);
        verify(reservationDao).findByTrip(trip1);
    }
    
    @Test
    public void testFindByTripNull() {
        doThrow(new NullPointerException()).when(reservationDao).findByTrip(null);
        assertThatThrownBy(() -> reservationService.findByTrip(null))
                .as("findByTrip(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    public void testFindByTripUserDataAccessError() {
        doThrow(new EntityExistsException()).when(reservationDao).findByTrip(any());
        assertThatThrownBy(() -> reservationService.findByTrip(any()))
                .isInstanceOf(DataAccessException.class);
    }
    
    @Test
    public void testAddExcursionOk() {    
        reservation1.setTrip(trip1);
        reservation1.setUser(user1);
        reservationService.create(reservation1);   
        when(reservationDao.findById(1l)).thenReturn(reservation1);
        assertEquals("Excursion1 should be in reservation",reservationService
                .addExcursion(reservation1.getId(), excursion1)
                .getExcursionSet(),reservation1.getExcursionSet());
        assertEquals("Two excursions should be in reservation",reservationService
                .addExcursion(reservation1.getId(), excursion2)
                .getExcursionSet().size(),2);        
    }
    
    @Test(expected = NullPointerException.class)
    public void testAddExcursionNull() {
        reservationService.create(reservation1);
        reservationService.addExcursion(reservation1.getId(), null);
    }
    
    @Test(expected = Exception.class)
    public void testAddBadExcursion() {   
        reservation1.setTrip(trip2);
        reservation1.setUser(user1);
        reservationService.create(reservation1);   
        when(reservationDao.findById(1l)).thenReturn(reservation1);
        reservationService.addExcursion(reservation1.getId(), excursion1);
    }
    
    @Test
    public void testGetTotalPriceOk() {
        reservation1.setTrip(trip1);
        reservation1.setUser(user1);
        reservationService.create(reservation1);
        when(reservationDao.findById(1l)).thenReturn(reservation1);              
        assertEquals("Total price when reservation contains only trip without excursion",
                reservationService.getTotalPrice(reservation1.getId()),BigDecimal.valueOf(5000));
        reservationService.addExcursion(reservation1.getId(), excursion1);
        assertEquals("Total price when reservation contains trip with 1 excursion",
                reservationService.getTotalPrice(reservation1.getId()),BigDecimal.valueOf(5080));
        reservationService.addExcursion(reservation1.getId(), excursion2);
        assertEquals("Total price when reservation contains trip with 2 excursions",
                reservationService.getTotalPrice(reservation1.getId()),BigDecimal.valueOf(5180));        
    }
    
    @Test(expected = NullPointerException.class)
    public void testGetTotalPriceNull() {
        reservationService.getTotalPrice(null);
    }    
}
       
