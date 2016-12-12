package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserAuthenticateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.MappingService;
import cz.muni.fi.pa165.travelagency.service.UserService;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import cz.muni.fi.pa165.travelagency.service.facade.UserFacadeImpl;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class UserFacadeTest {
    
    @Mock
    private UserService userService;

    @Mock
    private MappingService mappingService;
    
    @InjectMocks
    private UserFacade userFacade = new UserFacadeImpl();
    
    private User user;
    private UserCreateDto userCreateDto;
    private UserDto userDto;
    
    private Reservation reservation;
    private ReservationDto reservationDto;
    private Trip trip;
    private TripDto tripDto;
    
    private Calendar calNow;
    
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        Long id = 13L;
        String name = "John Bedar";
        calNow = Calendar.getInstance();
        Calendar cal = (Calendar) calNow.clone();
        cal.add(Calendar.YEAR, -20);
        Date birthDate = cal.getTime();
        Long personalNumber = Long.valueOf("951245");
        String mail = "john.bedar@comp.nz";
        Integer phoneNumber = Integer.valueOf("605125748");
        Boolean isAdmin = Boolean.FALSE;
        String passwordHash = "password";
        
        trip = new Trip();
        trip.setId(2L);
        trip.setName("Trip to Budapest");
        trip.setDescription("City walking & spa");
        trip.setDestination("Budapest");
        trip.setCapacity(Integer.valueOf("20"));
        trip.setPrice(BigDecimal.valueOf(3000));
        cal = (Calendar) calNow.clone();
        cal.add(Calendar.MONTH, 2);
        trip.setDateFrom(cal.getTime());
        cal.add(Calendar.DATE, 5);
        trip.setDateTo(cal.getTime());
        
        tripDto = new TripDto();
        tripDto.setId(2L);
        tripDto.setName("Trip to Budapest");
        tripDto.setDescription("City walking & spa");
        tripDto.setDestination("Budapest");
        tripDto.setCapacity(Integer.valueOf("20"));
        tripDto.setPrice(BigDecimal.valueOf(3000));
        cal = (Calendar) calNow.clone();
        cal.add(Calendar.MONTH, 2);
        tripDto.setDateFrom(cal.getTime());
        cal.add(Calendar.DATE, 5);
        tripDto.setDateTo(cal.getTime());
        
        user = new User(id);
        user.setName(name);
        user.setBirthDate(birthDate);
        user.setPersonalNumber(personalNumber);
        user.setMail(mail);
        user.setPhoneNumber(phoneNumber);
        user.setIsAdmin(isAdmin);
        user.setPasswordHash(passwordHash);
        
        userCreateDto = new UserCreateDto();
        userCreateDto.setName(name);
        userCreateDto.setBirthDate(birthDate);
        userCreateDto.setPersonalNumber(personalNumber);
        userCreateDto.setMail(mail);
        userCreateDto.setPhoneNumber(phoneNumber);
        userCreateDto.setIsAdmin(isAdmin);
        userCreateDto.setPasswordHash(passwordHash);
        
        userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setBirthDate(birthDate);
        userDto.setPersonalNumber(personalNumber);
        userDto.setMail(mail);
        userDto.setPhoneNumber(phoneNumber);
        userDto.setIsAdmin(isAdmin);
        userDto.setPasswordHash(passwordHash);
        
        reservation = new Reservation();
        reservation.setId(25L);
        reservation.setTrip(trip);
        reservation.setUser(user);
        
        reservationDto = new ReservationDto();
        reservationDto.setId(25L);
        reservationDto.setTrip(tripDto);
        reservationDto.setUser(userDto);
        
        when(mappingService.mapTo(userCreateDto, User.class)).thenReturn(user);
        when(mappingService.mapTo(userDto, User.class)).thenReturn(user);
        when(mappingService.mapTo(tripDto, Trip.class)).thenReturn(trip);
        when(mappingService.mapTo(reservationDto, Reservation.class)).thenReturn(reservation);
        
        
    }
    
    @Test
    public void testCreate() {
        userFacade.create(userCreateDto);
        verify(userService).createRegisteredUser(user, "password");
    }

    @Test
    public void testUpdate() {
        userFacade.update(userDto);
        verify(userService).update(user);
    }

    @Test
    public void testDelete() {
        userFacade.delete(userDto);
        verify(userService).delete(user);
    }

    @Test
    public void testFindById() {
        userFacade.findById(5L);
        verify(userService).findById(5L);
    }

    @Test
    public void testFindAll() {
        userFacade.findAll();
        verify(userService).findAll();
    }
    
    @Test
    public void testFindByName() {
        userFacade.findByName("Carl von Bahnhof");
        verify(userService).findByName("Carl von Bahnhof");
    }
    
    @Test
    public void testFindByMail() {
        userFacade.findByMail("carl.bahn@gmail.com");
        verify(userService).findByMail("carl.bahn@gmail.com");
    }
    
    @Test
    public void testFindByBirthDate() {
        Calendar cal = (Calendar) calNow.clone();
        cal.set(Calendar.YEAR, -19);
        userFacade.findByBirthDate(cal.getTime());
        verify(userService).findByBirthDate(cal.getTime());
    }
    
    @Test
    public void testFindByPersonalNumber() {
        userFacade.findByPersonalNumber(13L);
        verify(userService).findByPersonalNumber(13L);
    }
    
    @Test
    public void testFindByPhoneNumber() {
        userFacade.findByPhoneNumber(Integer.valueOf("545789245"));
        verify(userService).findByPhoneNumber(Integer.valueOf("545789245"));
    }
    
    @Test
    public void testFindByReservation() {
        userFacade.findByReservation(reservationDto);
        verify(userService).findByReservation(reservation);
    }
    
    @Test
    public void testIsAdmin() {
        userFacade.isUserAdmin(userDto);
        verify(userService).isUserAdmin(user);
    }
    
    @Test
    public void testUserAuthenticate() {
        UserAuthenticateDto userAuth = new UserAuthenticateDto();
        userAuth.setMail("john.bedar@comp.nz");
        userAuth.setPassword("password");
        when(userService.findByMail(user.getMail())).thenReturn(user);
        userFacade.userAuthenticate(userAuth);
        verify(userService).userAuthenticate(user, "password");
    }

}
