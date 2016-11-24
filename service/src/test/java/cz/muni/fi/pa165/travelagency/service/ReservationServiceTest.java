package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import javax.inject.Inject;
import javax.validation.ValidationException;
import org.hibernate.service.spi.ServiceException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
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
    
    @Mock
    private TripService tripService;
    
    @Mock
    private UserService userService;
    
    @Mock
    private ExcursionService excursionService;
    
    @Inject
    @InjectMocks
    private ReservationService reservationService;
    
    private Reservation reservation1;
    private Reservation reservation2;
    private Reservation reservation3;
    private Reservation reservation4;
    private Reservation reservation5;
    
    private Trip trip1;
    private User user1;
    private Excursion excursion1; 
    
    @Before
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @Before
    public void initReservations() {
        Calendar calendar = Calendar.getInstance();
        // create user1
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
        
        Trip trip2 = new Trip(2l);
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
        
        reservation1 = new Reservation();
        reservation1.setId(1l);
        reservation1.setTrip(trip1);
        reservation1.setUser(user1);

        reservation2 = new Reservation();
        reservation1.setId(2l);
        reservation2.setUser(user1);
        
        reservation3 = new Reservation();
        reservation1.setId(3l);
        reservation3.setTrip(trip1);
        
        reservation4 = new Reservation();
        reservation4.setId(4l);
        reservation4.setTrip(trip2);
        reservation4.setUser(user2);
        
        reservation5 = new Reservation();
        reservation5.setId(5l);
        reservation5.setTrip(trip1);
        reservation5.setUser(user2);
        reservation5.addExcursion(excursion1);
    }
   
    @Test
    public void testCreateOk() {
        reservationService.create(reservation1);
        assertNotNull(reservation1);
        verify(reservationDao).create(reservation1);
    }
    
    @Test(expected = NullPointerException.class)
    public void testCreateNull() {
        doThrow(new NullPointerException()).when(reservationDao).create(null);
        reservationService.create(null);
    }
    
    @Test(expected = Exception.class)
    public void testCreateExisting() {
        reservationService.create(reservation1);
        doThrow(new Exception()).when(reservationDao).create(reservation1);
        reservationService.create(reservation1);
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithoutTrip() {
        doThrow(new ValidationException()).when(reservationDao).create(reservation2);
        reservationService.create(reservation2);
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithoutUser() {
        doThrow(new ValidationException()).when(reservationDao).create(reservation3);
        reservationService.create(reservation3);
    }
    
    @Test
    public void testDeleteOk() {
        reservationService.create(reservation1);
        reservationService.delete(reservation1);
        verify(reservationDao).delete(reservation1);
    }
    
    @Test(expected = NullPointerException.class)
    public void testDeleteNull() {
        doThrow(new NullPointerException()).when(reservationDao).delete(null);
        reservationService.delete(null);
    }
    
    @Test(expected = Exception.class)
    public void testDeleteNonExisting() {
        Reservation reservation = new Reservation();
        doThrow(new Exception()).when(reservationDao).delete(reservation);
        reservationService.delete(reservation);
    }
   
    @Test
    public void testFindByIdOk() {        
        when(reservationDao.findById(reservation1.getId())).thenReturn(reservation1);
        assertDeepEquals(reservationService.findById(reservation1.getId()), reservation1);
    }
    
    @Test(expected = NullPointerException.class)
    public void testFindByIdNull() {
        doThrow(new NullPointerException()).when(reservationDao).findById(null);
        reservationService.findById(null);
    }
    
    @Test
    public void testFindByIdNonExisting() {
        assertNull(reservationService.findById(0l));
    }
    
    @Test
    public void testFindAllOk() {
        when(reservationDao.findAll()).thenReturn(new ArrayList<>());
        assertEquals(reservationService.findAll().size(),0);
        when(reservationDao.findAll()).thenReturn(Arrays.asList(reservation1));
        assertEquals(reservationService.findAll().size(),1);
        when(reservationDao.findAll()).thenReturn(Arrays.asList(reservation1, reservation4));
        assertEquals(reservationService.findAll().size(),2);        
    } 
    
    @Test
    public void testFindByUserOk() {
        User user = reservation1.getUser();
        when(reservationDao.findByUser(user)).thenReturn(Arrays.asList(reservation1));
        assertEquals(reservationService.findByUser(user).size(),1);
        assertDeepEquals(reservation1, reservationService.findByUser(user).get(0));
    }
    
    @Test(expected = NullPointerException.class)
    public void testFindByUserNull() {
        doThrow(new NullPointerException()).when(reservationDao).findByUser(null);
        reservationService.findByUser(null);
    }
    
    @Test
    public void testFindByUserNonExisting() {
        User user = new User();
        when(reservationDao.findByUser(user)).thenReturn(new ArrayList<>());
        assertEquals(reservationService.findByUser(user).size(), 0);
    }
    
    @Test
    public void testFindByTripOk() {
        Trip trip = reservation1.getTrip();
        when(reservationDao.findByTrip(trip)).thenReturn(Arrays.asList(reservation1));
        assertEquals(reservationService.findByTrip(trip).size(),1);
        assertDeepEquals(reservation1, reservationService.findByTrip(trip).get(0));
    }
    
    @Test(expected = NullPointerException.class)
    public void testFindByTripNull() {
        doThrow(new NullPointerException()).when(reservationDao).findByTrip(null);
        reservationService.findByTrip(null);
    }
    
    @Test
    public void testFindByTripNonExisting() {
        Trip trip = new Trip();
        when(reservationDao.findByTrip(trip)).thenReturn(new ArrayList<>());
        assertEquals(reservationService.findByTrip(trip).size(), 0);
    }
    
    @Test
    public void testAddExcursionOk() {
        Reservation reservationPom = new Reservation();
        reservationPom.setId(2l);
        reservationPom.setTrip(trip1);
        reservationPom.setUser(user1);
        
        Reservation reservationPom2 = new Reservation();
        reservationPom2.setId(2l);
        reservationPom2.setTrip(trip1);
        reservationPom2.setUser(user1);
        reservationPom2.addExcursion(excursion1);
        excursionService.create(excursion1);
        reservationService.create(reservation1);
        when(reservationDao.findById(1l)).thenReturn(reservationPom);
        when(reservationDao.findById(2l)).thenReturn(reservationPom2);
        assertEquals(reservation1.getExcursionSet(),reservationService.addExcursion(reservation1.getId(), excursion1).getExcursionSet());
    }
    
    @Test(expected = NullPointerException.class)
    public void testAddExcursionNull() {
        reservationService.create(reservation1);
        doThrow(new NullPointerException()).when(reservationDao).update(null);
        reservationService.addExcursion(reservation1.getId(), null);
    }
    
    @Test
    public void testAddExcursionNonExisting() {
       Excursion excursion = new Excursion();
       when(reservationDao.findById(5l)).thenReturn(reservation5);
       assertEquals(reservationService.addExcursion(reservation5.getId(), excursion).getExcursionSet().size(),0);      
    }
    
    @Test
    public void testGetTotalPriceOk() {
        when(reservationDao.findById(1l)).thenReturn(reservation1);      
        assertEquals(reservationService.getTotalPrice(reservation1.getId()),BigDecimal.valueOf(5000));
        
        when(reservationDao.findById(5l)).thenReturn(reservation5);
        assertEquals(reservationService.getTotalPrice(reservation5.getId()),BigDecimal.valueOf(5080));
    }
    
    @Test(expected = NullPointerException.class)
    public void testGetTotalPriceNull() {
        reservationService.getTotalPrice(null);
    }
    
    @Test(expected = Exception.class)
    public void testGetTotalPriceNonExisting() {
        when(reservationDao.findById(0l)).thenReturn(null);
        reservationService.getTotalPrice(0l);
    }
    
    private void assertDeepEquals(Reservation res1,Reservation res2) {
        assertEquals(res1,res2);
        assertEquals(res1.getId(),res2.getId());
        assertEquals(res1.getTrip(),res2.getTrip());
        assertEquals(res1.getUser(),res2.getUser());       
    }
}
       
