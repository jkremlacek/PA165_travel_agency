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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for service layer of reservation entity
 * @author Katerina Caletkova
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class ReservationServiceTest {
    
    @Mock
    private ReservationDao reservationDao;
    
    @Mock
    private TripService tripService;
    
    @Mock
    private UserService userService;
    
    @Inject
    @InjectMocks
    private ReservationService reservationService;
    
    private Reservation reservation1;
    private Reservation reservation2;
    private Reservation reservation3;
    private Reservation reservation4;
    
    
    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Before
    public void initReservations() {
        Calendar calendar = Calendar.getInstance();
        // create user1
        User user1 = new User(1l);
        calendar.set(1991,1,1);
        user1.setBirthDate(calendar.getTime());
        calendar.clear();
        user1.setIsAdmin(false);
        user1.setMail("mail@mail.com");
        user1.setName("Jan");
        user1.setPasswordHash("heslo");
        user1.setPersonalNumber(123456789);
        user1.setPhoneNumber(777777777);
        
        User user2 = new User(2l);
        calendar.set(1990,5,5);
        user2.setBirthDate(calendar.getTime());
        calendar.clear();
        user2.setIsAdmin(false);
        user2.setMail("m@mail.com");
        user2.setName("Jana");
        user2.setPasswordHash("mojeheslo");
        user2.setPersonalNumber(111111111);
        user2.setPhoneNumber(777777776);
        
        Trip trip1 = new Trip(1l);
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
       
        Excursion excursion = new Excursion(1l);
        calendar.set(2017,1,21);
        excursion.setDate(calendar.getTime());
        calendar.clear();
        excursion.setDestination("Rim");
        calendar.set(0, 0, 0, 5, 0, 0);
        excursion.setDuration(calendar.getTime());
        calendar.clear();
        excursion.setName("Vylet do Rima");
        excursion.setPrice(BigDecimal.valueOf(80));
        excursion.setTrip(trip1);
        
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
    }
   
    @Test
    public void testCreateOk() {
        reservationService.create(reservation1);
        assertNotNull(reservation1);
        verify(reservationDao).create(reservation1);
    }
    
    @Test(expected = Exception.class)
    public void testCreateNull() {
        reservationService.create(null);
    }
    
    @Test(expected = Exception.class)
    public void testCreateExisting() {
        reservationService.create(reservation1);
        reservationService.create(reservation1);
    }
    
    @Test(expected = Exception.class)
    public void testCreateWithoutTrip() {
        reservationService.create(reservation2);
    }
    
    @Test(expected = Exception.class)
    public void testCreateWithoutUser() {
        reservationService.create(reservation3);
    }
    
    @Test
    public void testDeleteOk() {
        reservationService.create(reservation1);
        reservationService.delete(reservation1);
        verify(reservationDao).delete(reservation1);
    }
    
    @Test(expected = Exception.class)
    public void testDeleteNull() {
        reservationService.delete(null);
    }
    
    @Test(expected = Exception.class)
    public void testDeleteNonExisting() {
        reservationService.delete(reservation1);
    }
   
    @Test
    public void testFindByIdOk() {        
        when(reservationDao.findById(reservation1.getId())).thenReturn(reservation1);
        assertDeepEquals(reservationService.findById(reservation1.getId()), reservation1);
    }
    
    @Test(expected = Exception.class)
    public void testFindByIdNull() {
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
    
    @Test(expected = Exception.class)
    public void testFindByUserNull() {
        reservationService.findByUser(null);
    }
    
    @Test
    public void testFindByUserNonExisting() {
        // TODO
    }
    
    @Test
    public void testFindByTripOk() {
        Trip trip = reservation1.getTrip();
        when(reservationDao.findByTrip(trip)).thenReturn(Arrays.asList(reservation1));
        assertEquals(reservationService.findByTrip(trip).size(),1);
        assertDeepEquals(reservation1, reservationService.findByTrip(trip).get(0));
    }
    
    @Test(expected = Exception.class)
    public void testFindByTripNull() {
        reservationService.findByTrip(null);
    }
    
    @Test
    public void testFindByTripNonExisting() {
        // TODO
    }
    
    @Test
    public void testAddExcursionOk() {
        // TODO
    }
    
    @Test(expected = Exception.class)
    public void testAddExcursionNull() {
        reservationService.create(reservation1);
        reservationService.addExcursion(reservation1.getId(), null);
    }
    
    @Test
    public void testAddExcursionNonExisting() {
        // TODO
    }
    
    
    @Test
    public void testGetTotalPriceOk() {
        // TODO
    }
    
    @Test(expected = Exception.class)
    public void testGetTotalPriceNull() {
        // TODO
    }
    
    @Test
    public void testGetTotalPriceNonExisting() {
        // TODO
    }
    
    private void assertDeepEquals(Reservation res1,Reservation res2) {
        assertEquals(res1,res2);
        assertEquals(res1.getId(),res2.getId());
        assertEquals(res1.getTrip(),res2.getTrip());
        assertEquals(res1.getUser(),res2.getUser());       
    }
}
       
