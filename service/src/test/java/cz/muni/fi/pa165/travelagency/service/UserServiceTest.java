package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.UserDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.springframework.dao.DataAccessException;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class UserServiceTest {
    
    @Mock
    private UserDao userDao;
    
    @Inject
    @InjectMocks
    private UserService userService;
    
    private Calendar calNow;
    
    private User johnBedarUser;
    
    private Reservation johnReservation;
    
    private Trip bollywoodTrip;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        calNow = Calendar.getInstance();
        Calendar cal = (Calendar) calNow.clone();
        
        bollywoodTrip = new Trip();
        bollywoodTrip.setCapacity(Integer.valueOf("5"));
        bollywoodTrip.setName("Bollywood");
        bollywoodTrip.setDescription("Trip to India");
        bollywoodTrip.setDestination("India");
        bollywoodTrip.setPrice(BigDecimal.valueOf(20000));
        cal.add(Calendar.MONTH, 2);
        bollywoodTrip.setDateFrom(cal.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 14);
        bollywoodTrip.setDateTo(cal.getTime());
        
        johnBedarUser = new User();
        johnBedarUser.setName("John Bedar");
        johnBedarUser.setPersonalNumber(92072147l);
        cal.set(1992, 7, 21);
        johnBedarUser.setBirthDate(cal.getTime());
        johnBedarUser.setIsAdmin(Boolean.FALSE);
        johnBedarUser.setPasswordHash("mypass");
        johnBedarUser.setMail("john.bedar@comp.nz");
        johnBedarUser.setPhoneNumber(Integer.valueOf("698425741"));
        
        johnReservation = new Reservation();
        johnReservation.setTrip(bollywoodTrip);
        johnReservation.setUser(johnBedarUser);
        
        johnBedarUser.addReservation(johnReservation);
    }
    

    @Test
    public void testCreateRegisteredUser() {
        userService.createRegisteredUser(johnBedarUser, "mypassword");
        assertThat(johnBedarUser)
                .as("user shouldn't be null after create user")
                .isNotNull();
        verify(userDao).create(johnBedarUser);
        assertThat(johnBedarUser.getPasswordHash())
                .as("password hash sholdn't be null after create user")
                .isNotNull();
    }

    @Test(expected = Exception.class)
    public void testCreateExisting() {
        userService.createRegisteredUser(johnBedarUser, "password");
        doThrow(new Exception()).when(userDao).create(johnBedarUser);
        userService.createRegisteredUser(johnBedarUser, "password");
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithoutName() {
        johnBedarUser.setName(null);
        doThrow(new ValidationException()).when(userDao).create(johnBedarUser);
        userService.createRegisteredUser(johnBedarUser, "johnBedarUser");
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithoutPersonalNumber() {
        johnBedarUser.setPersonalNumber(null);
        doThrow(new ValidationException()).when(userDao).create(johnBedarUser);
        userService.createRegisteredUser(johnBedarUser, "johnBedarUser");
    }
    
    @Test(expected = ValidationException.class)
    public void testCreateWithoutIsAdmin() {
        johnBedarUser.setIsAdmin(null);
        doThrow(new ValidationException()).when(userDao).create(johnBedarUser);
        userService.createRegisteredUser(johnBedarUser, "johnBedarUser");
    }
    
    @Test
    public void testUpdate() {
        userService.createRegisteredUser(johnBedarUser, "password");
        johnBedarUser.setPhoneNumber(Integer.valueOf("516456168"));
        userService.update(johnBedarUser);
        verify(userDao).update(johnBedarUser);
    }
    
    @Test(expected = Exception.class)
    public void testUpdateNonExisting() {
        User user = new User();
        doThrow(new Exception()).when(userDao).update(user);
        userService.update(user);
    }
    
    @Test(expected = Exception.class)
    public void testDeleteNonExisting() {
        User user = new User();
        doThrow(new Exception()).when(userDao).delete(user);
        userService.delete(user);
    }
    
    @Test
    public void testDelete() {
        userService.createRegisteredUser(johnBedarUser, "password");
        userService.delete(johnBedarUser);
        verify(userDao).delete(johnBedarUser);
    }
    
    @Test
    public void testFindById() {
        johnBedarUser.setId(Long.valueOf("13"));
        when(userDao.findById(johnBedarUser.getId())).thenReturn(johnBedarUser);
        assertThat(userService.findById(johnBedarUser.getId()))
                .as("It could be possible to find user by existing id")
                .isEqualToComparingFieldByField(johnBedarUser);
        userService.findById(Long.valueOf("13"));
        verify(userDao, times(2)).findById(Long.valueOf("13"));
                
    }

    @Test
    public void testFindByName() {
        userService.findByName("John Bedar");
        verify(userDao).findByName("John Bedar");
       
    }
    
    @Test
    public void testFindByBirthDate() {
       userService.findByBirthDate(johnBedarUser.getBirthDate());
       verify(userDao).findByBirthDate(johnBedarUser.getBirthDate());
    }
    
    @Test
    public void testFindByPersonalNumber() {
        userService.findByPersonalNumber(92072147l);
        verify(userDao).findByPersonalNumber(92072147l);
    }

    @Test
    public void testFindByMail() {
        userService.findByMail("john.bedar@comp.nz");
        verify(userDao).findByMail("john.bedar@comp.nz");
    }
    
    @Test
    public void testFindByPhoneNumber() {
       userService.findByPhoneNumber(Integer.valueOf("698425741"));
       verify(userDao).findByPhoneNumber(Integer.valueOf("698425741"));
    }

    @Test
    public void testFindByReservation() {
        userService.findByReservation(johnReservation);
        verify(userDao).findByReservation(johnReservation);
    }

    @Test
    public void testFindAll() {
        userService.findAll();
        verify(userDao).findAll();
    }
    
    @Test
    public void testIsUserAdmin() {
        assertThat(userService.isUserAdmin(johnBedarUser))
                .as("User without admin rights shouldn't has admin sign")
                .isFalse();
    }
    
    @Test
    public void testUserAuthenticate() {
        userService.createRegisteredUser(johnBedarUser, "password");
        assertThat(johnBedarUser)
                .as("user shouldn't be null")
                .isNotNull();
        assertThat(johnBedarUser.getPasswordHash())
                .as("user password hash shouldn't be null")
                .isNotNull();
        
        boolean authenticated = userService.userAuthenticate(johnBedarUser, "badPassword");
        assertThat(authenticated)
                .as("user cannot authenticate by incorret password!")
                .isFalse();
        
        authenticated = userService.userAuthenticate(johnBedarUser, "password");
        assertThat(authenticated)
                .as("user should authenticate by correct password!")
                .isTrue();
    }
    
    @Test(expected = DataAccessException.class)
    public void testDataAccessErrorCreateUser() {
        doThrow(new EntityExistsException()).when(userDao).create(any());
        userService.createRegisteredUser(johnBedarUser, "password");
    }

    @Test
    public void testDataAccessError() {
        doThrow(new EntityNotFoundException()).when(userDao).update(any());
        doThrow(new EntityNotFoundException()).when(userDao).delete(any());
        doThrow(new EntityNotFoundException()).when(userDao).findById(any());
        doThrow(new EntityNotFoundException()).when(userDao).findByName(any());
        doThrow(new EntityNotFoundException()).when(userDao).findByBirthDate(any());
        doThrow(new EntityNotFoundException()).when(userDao).findByMail(any());
        doThrow(new EntityNotFoundException()).when(userDao).findByPersonalNumber(any());
        doThrow(new EntityNotFoundException()).when(userDao).findByPhoneNumber(any());
        doThrow(new EntityNotFoundException()).when(userDao).findByReservation(any());

        assertThatThrownBy(() -> userService.update(any()))
                .as("update() should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> userService.delete(any()))
                .as("delete() should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);
        
        assertThatThrownBy(() -> userService.findById(any()))
                .as("findById() should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> userService.findByName(any()))
                .as("findByName() should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> userService.findByBirthDate(any()))
                .as("findByBirthDate() should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> userService.findByMail(any()))
                .as("findByMail() should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> userService.findByPersonalNumber(any()))
                .as("findByPersonalNumber() should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> userService.findByPhoneNumber(any()))
                .as("findByPhoneNumber() should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> userService.findByReservation(any()))
                .as("findByReservation() should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);
    }
    
    @Test
    public void testNullArguments() {
        doThrow(new NullPointerException()).when(userDao).create(null);
        doThrow(new NullPointerException()).when(userDao).update(null);
        doThrow(new NullPointerException()).when(userDao).delete(null);
        doThrow(new NullPointerException()).when(userDao).findById(null);
        doThrow(new NullPointerException()).when(userDao).findByName(null);
        doThrow(new NullPointerException()).when(userDao).findByBirthDate(isNull(Date.class));
        doThrow(new NullPointerException()).when(userDao).findByMail(null);
        doThrow(new NullPointerException()).when(userDao).findByPersonalNumber(null);
        doThrow(new NullPointerException()).when(userDao).findByPhoneNumber(null);
        doThrow(new NullPointerException()).when(userDao).findByReservation(null);

        assertThatThrownBy(() -> userService.createRegisteredUser(null, "password"))
                .as("createRegisteredUser(null, 'password') should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        

        assertThatThrownBy(() -> userService.createRegisteredUser(johnBedarUser, null))
                .as("createRegisteredUser(user, null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);        
        
        assertThatThrownBy(() -> userService.update(null))
                .as("update(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        
        assertThatThrownBy(() -> userService.delete(null))
                .as("delete(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> userService.findById(null))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> userService.findByName(null))
                .as("findByName(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> userService.findByBirthDate(null))
                .as("findByBirthDate(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> userService.findByMail(null))
                .as("findByMail(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> userService.findByPersonalNumber(null))
                .as("findByPersonalNumber(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> userService.findByPhoneNumber(null))
                .as("findByPhoneNumber(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> userService.findByReservation(null))
                .as("findByReservation(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        
        assertThatThrownBy(() -> userService.userAuthenticate(null, "password"))
                .as("userAuthenticate(null, \"password\") should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        
        assertThatThrownBy(() -> userService.userAuthenticate(johnBedarUser, null))
                .as("userAuthenticate(user, null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        
        assertThatThrownBy(() -> userService.isUserAdmin(null))
                .as("isUserAdmin(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        
    }
    
}
