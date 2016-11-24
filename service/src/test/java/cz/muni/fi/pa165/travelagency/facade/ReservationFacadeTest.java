package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.ReservationCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.MappingService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import static org.mockito.Mockito.verify;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import cz.muni.fi.pa165.travelagency.service.facade.ReservationFacadeImpl;
import java.math.BigDecimal;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

/**
 * Tests for facade layer of reservation entity
 * @author Katerina Caletkova
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class ReservationFacadeTest {
 
    @Mock
    private ReservationService reservationService;
    
    @Mock
    private  MappingService mappingService;
    
    @InjectMocks
    private final ReservationFacade reservationFacade = new ReservationFacadeImpl();
   
    private Reservation reservation1;
    private ReservationCreateDto reservationCreateDto1;
    private ReservationDto reservationDto1;
    
    private User user1;
    private UserDto userDto1;
    
    private Trip trip1;
    private TripDto tripDto1;

    @Before
    public void setup() {
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
       
        userDto1 = new UserDto();
        userDto1.setBirthDate(user1.getBirthDate());
        userDto1.setIsAdmin(user1.getIsAdmin());
        userDto1.setMail(user1.getMail());
        userDto1.setName(user1.getName());
        userDto1.setPasswordHash(user1.getPasswordHash());
        userDto1.setPersonalNumber(user1.getPersonalNumber());
        userDto1.setPhoneNumber(user1.getPhoneNumber());
        
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

        tripDto1 = new TripDto();
        tripDto1.setCapacity(trip1.getCapacity());
        tripDto1.setDateFrom(trip1.getDateFrom());
        tripDto1.setDateTo(trip1.getDateTo());
        tripDto1.setDestination(trip1.getDestination());
        tripDto1.setName(trip1.getName());
        tripDto1.setPrice(trip1.getPrice());

        reservation1 = new Reservation();
        reservation1.setId(1l);
        reservation1.setTrip(trip1);
        reservation1.setUser(user1);

        reservationCreateDto1 = new ReservationCreateDto();
        reservationCreateDto1.setTrip(tripDto1);
        reservationCreateDto1.setUser(userDto1);

        reservationDto1 = new ReservationDto();
        reservationDto1.setId(1l);
        reservationDto1.setTrip(tripDto1);
        reservationDto1.setUser(userDto1);

        when(mappingService.mapTo(reservationCreateDto1, Reservation.class)).thenReturn(reservation1);
        when(mappingService.mapTo(reservationDto1, Reservation.class)).thenReturn(reservation1);
        when(mappingService.mapTo(tripDto1, Trip.class)).thenReturn(trip1);
        when(mappingService.mapTo(userDto1, User.class)).thenReturn(user1);
    }
     
    @Test
    public void testCreate() throws Exception {
       reservationFacade.create(reservationCreateDto1);
       verify(reservationService).create(reservation1);
    }
    
    @Test
    public void testUpdate() throws Exception {
        reservationFacade.update(reservationDto1);
        verify(reservationService).update(reservation1);
    }
    @Test
    public void testFindById() throws Exception {
        reservationFacade.findById(1l);
        verify(reservationService).findById(1l);
    
    }
    
    @Test
    public void testFindAll() throws Exception {
        reservationFacade.findAll();
        verify(reservationService).findAll();
    }
    
    @Test
    public void testFindByUser() throws Exception {   
        reservationFacade.findByUser(userDto1);
        verify(reservationService).findByUser(user1);
    }
     
    @Test
    public void testFindByTrip() throws Exception {
        reservationFacade.findByTrip(tripDto1);
        verify(reservationService).findByTrip(trip1);
    }
    
    @Test
    public void testGetTotalPrice() throws Exception {
        reservationFacade.getTotalPrice(reservationDto1.getId());
        verify(reservationService).getTotalPrice(reservation1.getId());
    }
                           
}
