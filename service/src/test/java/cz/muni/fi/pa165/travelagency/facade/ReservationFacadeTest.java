package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.ReservationCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;
import cz.muni.fi.pa165.travelagency.service.MappingService;
import cz.muni.fi.pa165.travelagency.service.ReservationService;
import cz.muni.fi.pa165.travelagency.service.TripService;
import cz.muni.fi.pa165.travelagency.service.UserService;
import static org.mockito.Mockito.verify;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import cz.muni.fi.pa165.travelagency.service.facade.ReservationFacadeImpl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;

/**
 * Tests for facade layer of reservation entity
 * @author Katerina Caletkova
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class ReservationFacadeTest extends AbstractTransactionalTestNGSpringContextTests{
 
    @Mock
    private ReservationService reservationService;
    
    @Mock
    private TripService tripService;
    
    @Mock
    private UserService userService;
    
    @Mock
    private  MappingService mappingService;
    
    @InjectMocks
    private final ReservationFacade reservationFacade = new ReservationFacadeImpl();
   
    @Captor
    ArgumentCaptor<Reservation> argumentCaptor;
     
    private Reservation reservation1;
    private ReservationDto reservationDto1;

    private Reservation reservation2;
    private ReservationDto reservationDto2;

    private User user1;
    private UserDto userDto1;
    private User user2;
    private UserDto userDto2;

    private Trip trip;
    private TripDto tripDto;


    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void init() {
        Calendar calendar = Calendar.getInstance();
        // create user1
        user1 = new User(1l);
        calendar.set(1991,1,1);
        user1.setBirthDate(calendar.getTime());
        calendar.clear();
        user1.setIsAdmin(false);
        user1.setMail("mail@mail.com");
        user1.setName("Jan");
        user1.setPasswordHash("heslo");
        user1.setPersonalNumber(123456789);
        user1.setPhoneNumber(777777777);
       
        userDto1 = new UserDto();
        userDto1.setBirthDate(user1.getBirthDate());
        userDto1.setIsAdmin(user1.getIsAdmin());
        userDto1.setMail(user1.getMail());
        userDto1.setName(user1.getName());
        userDto1.setPasswordHash(user1.getPasswordHash());
        userDto1.setPersonalNumber(user1.getPersonalNumber());
        userDto1.setPhoneNumber(user1.getPhoneNumber());
        
        // create user2
        user2 = new User(2l);
        calendar.set(1990,5,5);
        user2.setBirthDate(calendar.getTime());
        calendar.clear();
        user2.setIsAdmin(false);
        user2.setMail("m@mail.com");
        user2.setName("Jana");
        user2.setPasswordHash("mojeheslo");
        user2.setPersonalNumber(111111111);
        user2.setPhoneNumber(777777776);
       
        userDto2 = new UserDto();
        userDto2.setBirthDate(user2.getBirthDate());
        userDto2.setIsAdmin(user2.getIsAdmin());
        userDto2.setMail(user2.getMail());
        userDto2.setName(user2.getName());
        userDto2.setPasswordHash(user2.getPasswordHash());
        userDto2.setPersonalNumber(user2.getPersonalNumber());
        userDto2.setPhoneNumber(user2.getPhoneNumber());
                               
       // create trip
       trip = new Trip(1l);
       trip.setCapacity(20);
       calendar.set(2017,1,20);
       trip.setDateFrom(calendar.getTime());
       calendar.clear();
       calendar.set(2017,1,28);
       trip.setDateTo(calendar.getTime());
       calendar.clear();
       trip.setDestination("Italie");
       trip.setName("Dovolena u more");
       trip.setPrice(BigDecimal.valueOf(5000));
       
       tripDto = new TripDto();
       tripDto.setCapacity(trip.getCapacity());
       tripDto.setDateFrom(trip.getDateFrom());
       tripDto.setDateTo(trip.getDateTo());
       tripDto.setDestination(trip.getDestination());
       tripDto.setName(trip.getName());
       tripDto.setPrice(trip.getPrice());
       
       
       // create reservation1
       reservation1 = new Reservation();
       reservation1.setTrip(trip);
       reservation1.setUser(user1);
       
       reservationDto1 = new ReservationDto();
       reservationDto1.setTrip(tripDto);
       reservationDto1.setUser(userDto1);
       
       // create reservation2
       reservation2 = new Reservation();
       reservation2.setTrip(trip);
       reservation2.setUser(user2);
       
       reservationDto2 = new ReservationDto();
       reservationDto2.setTrip(tripDto);
       reservationDto2.setUser(userDto2);
    }
   
    @BeforeMethod
    public void initMock() {
        
        when(userService.findById(1l)).thenReturn(user1);
        when(userService.findById(2l)).thenReturn(user2);
        when(tripService.findById(1l).thenReturn(trip));
        when(reservationService.findById(0l)).thenReturn(null);
        when(reservationService.findById(1l)).thenReturn(reservation1);
        when(reservationService.findById(2l)).thenReturn(reservation2);
        
        
    }        
    @Test
    public void testCreate() {
       ReservationCreateDto reservationCreateDto = new ReservationCreateDto();
       reservationCreateDto.setTrip(tripDto);
       reservationCreateDto.setUser(userDto1);
       
       reservationFacade.create(reservationCreateDto);
       verify(reservationService).create(argumentCaptor.capture());
    }
    
    @Test
    public void testFindById() {
        when(mappingService.mapTo(reservation1,ReservationDto.class)).thenReturn(reservationDto1);
        assertEquals(reservationFacade.findById(1l).getId(), reservationDto1.getId());
        assertEquals(reservationFacade.findById(1l), reservationDto1);
    }
    
    @Test
    public void testFindAll() {
        List<Reservation> reservations = Arrays.asList(reservation1,reservation2);
        List<ReservationDto> reservationsDto = new ArrayList<>();
        reservationsDto.add(reservationDto1);
        reservationsDto.add(reservationDto2);
        when(reservationService.findAll()).thenReturn(reservations);
        when(mappingService.mapTo(ReservationDto.class,reservations)).thenReturn(reservationsDto);
        assertEquals(reservationFacade.findAll().size(),2);
        assertEquals(reservationFacade.findAll(),reservationsDto);
    }
    
    @Test
    public void testFindByUser() {   
        List<Reservation> reservations = Arrays.asList(reservation1);
        List<ReservationDto> reservationsDto = new ArrayList<>();
        reservationsDto.add(reservationDto1);
        when(reservationService.findByUser(user1)).thenReturn(reservations);
        when(mappingService.mapTo(ReservationDto.class,reservations)).thenReturn(reservationsDto);
        assertEquals(reservationFacade.findByUser(userDto1).size(),1);
        assertEquals(reservationFacade.findByUser(userDto1),reservationsDto);
    }
     
    @Test
    public void testFindByTrip() {
        List<Reservation> reservations = Arrays.asList(reservation1);
        List<ReservationDto> reservationsDto = new ArrayList<>();
        reservationsDto.add(reservationDto1);
        when(reservationService.findByTrip(trip)).thenReturn(reservations);
        when(mappingService.mapTo(ReservationDto.class,reservations)).thenReturn(reservationsDto);
        assertEquals(reservationFacade.findByTrip(tripDto).size(),1);
        assertEquals(reservationFacade.findByTrip(tripDto),reservationsDto);
        
    }
                           
}
