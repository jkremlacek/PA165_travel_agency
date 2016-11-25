package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.ReservationDao;
import cz.muni.fi.pa165.travelagency.persistence.dao.TripDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import org.hibernate.service.spi.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;


/**
 * Created on 17.11.2016.
 *
 * @author Martin Salata
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class TripServiceTest {

    @Mock
    private TripDao tripDao;

    @Mock
    private ReservationDao reservationDao;

    @InjectMocks
    private TripService tripService = new TripServiceImpl();

    private User u;
    private Reservation r;
    private Trip t1, t2;
    private Excursion e1;

    @Before
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        Calendar cal = Calendar.getInstance();
        cal.set(2017, 8, 15);
        Date dFrom = cal.getTime();
        cal.set(2017, 8, 25);
        Date dTo = cal.getTime();

        u = new User("Igor", dFrom, 1234l, "no@mail.com", 123);

        t1 = new Trip();
        t1.setName("Dovolenka v Egypte");
        t1.setDateFrom(dFrom);
        t1.setDateTo(dTo);
        t1.setDestination("Egypt");
        t1.setCapacity(100);
        t1.setPrice(BigDecimal.valueOf(10000));

        t2 = new Trip();
        t2.setName("Dovolenka v Cernobyle");
        t2.setDateFrom(dFrom);
        t2.setDateTo(dTo);
        t2.setDestination("Ukrajina");
        t2.setCapacity(1);
        t2.setPrice(BigDecimal.valueOf(10000));


        cal.set(2017, 8, 20, 10, 0, 0);
        Date excursionDate = cal.getTime();
        cal.set(0, 0, 0, 5, 0, 0);
        Date excursionDuration = cal.getTime();

        e1 = new Excursion();
        e1.setName("Egypt- pyramidy");
        e1.setDate(excursionDate);
        e1.setDuration(excursionDuration);
        e1.setDestination("Kahira");
        e1.setPrice(BigDecimal.valueOf(800));

        t2.addExcursion(e1);
        r = new Reservation(u, new HashSet<>(), t1);

    }

    @Test
    public void testCreate() throws Exception {
        tripService.create(t1);
        verify(tripDao).create(t1);
    }

    @Test
    public void testDelete() throws Exception {
        tripService.delete(t1);
        verify(tripDao).delete(t1);
    }

    @Test
    public void testFindById() throws Exception {
        tripService.findById(t1.getId());
        verify(tripDao).findById(t1.getId());
    }

    @Test
    public void testFindAll() throws Exception {
        tripService.findAll();
        verify(tripDao).findAll();
    }

    @Test
    public void testFindByName() throws Exception {
        tripService.findByName(t1.getName());
        verify(tripDao).findByName(t1.getName());
    }

    @Test
    public void testFindByDate() throws Exception {
        tripService.findByDate(t1.getDateFrom(), t1.getDateTo());
        verify(tripDao).findByDate(t1.getDateFrom(), t1.getDateTo());
    }

    @Test
    public void testFindByDestination() throws Exception {
        tripService.findByDestination(t1.getDestination());
        verify(tripDao).findByDestination(t1.getDestination());
    }

    @Test
    public void testFindByPrice() throws Exception {
        tripService.findByPrice(t1.getPrice(), t1.getPrice());
        verify(tripDao).findByPrice(t1.getPrice(), t1.getPrice());
    }

    @Test
    public void testFindByAvailableCapacity() throws Exception {
        Integer i = 100;
        when(tripDao.findByTotalCapacity(i)).thenReturn(new LinkedList<>(Arrays.asList(t1, t2)));
        when(reservationDao.findByTrip(t1)).thenReturn(new LinkedList<>());
        when(reservationDao.findByTrip(t2)).thenReturn(new LinkedList<>(Arrays.asList(r)));

        assertThat(tripService.findByAvailableCapacity(i))
                .as("Only 1 item should be returned")
                .hasSize(1)
                .contains(t1);

        verify(tripDao).findByTotalCapacity(i);
    }

    @Test
    public void testFindByExcursion() throws Exception {
        tripService.findByExcursion(e1);
        verify(tripDao).findByExcursion(e1);
    }

    @Test
    public void testFindWithFreeCapacity() throws Exception {
        Integer i = 1;
        when(tripDao.findByTotalCapacity(i)).thenReturn(new LinkedList<>(Arrays.asList(t1, t2)));
        when(reservationDao.findByTrip(t1)).thenReturn(new LinkedList<>());
        when(reservationDao.findByTrip(t2)).thenReturn(new LinkedList<>(Arrays.asList(r)));

        assertThat(tripService.findByAvailableCapacity(i))
                .as("Only 1 item should be returned")
                .hasSize(1)
                .contains(t1);

        verify(tripDao).findByTotalCapacity(i);

    }

    @Test
    public void testFindTripsInNextDays() throws Exception {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);

        Date dateFrom = cal.getTime();
        cal.add(Calendar.DATE, 5);
        Date dateTo = cal.getTime();

        tripService.findTripsInNextDays(5);
        verify(tripDao).findByDate(dateFrom, dateTo);

        cal.add(Calendar.DATE, 5);
        dateTo = cal.getTime();

        tripService.findTripsInNextDays(10);
        verify(tripDao).findByDate(dateFrom, dateTo);
    }

    @Test
    public void testFindTripParticipants() throws Exception {
        when(reservationDao.findByTrip(t1)).thenReturn(Arrays.asList(r));
        assertThat(tripService.findTripParticipants(t1))
                .as("found participant should be u")
                .hasSize(1)
                .contains(u);
    }

    @Test
    public void testHasTripAvailableCapacity() {
        when(reservationDao.findByTrip(t1)).thenReturn(new LinkedList<>());
        when(reservationDao.findByTrip(t2)).thenReturn(new LinkedList<>(Arrays.asList(r)));

        assertThat(tripService.hasTripAvailableCapacity(t1))
                .as("This trip has free slots")
                .isTrue();

        assertThat(tripService.hasTripAvailableCapacity(t2))
                .as("This trip has no free slots")
                .isFalse();

        verify(reservationDao).findByTrip(t1);
        verify(reservationDao).findByTrip(t2);
    }


    @Test
    public void testNullArguments() {
        doThrow(new NullPointerException()).when(tripDao).create(null);
        doThrow(new NullPointerException()).when(tripDao).update(null);
        doThrow(new NullPointerException()).when(tripDao).delete(null);
        doThrow(new NullPointerException()).when(tripDao).findById(null);
        doThrow(new NullPointerException()).when(tripDao).findByName(null);
        doThrow(new NullPointerException()).when(tripDao).findByDate(isNull(Date.class), any());
        doThrow(new NullPointerException()).when(tripDao).findByDate(any(), isNull(Date.class));
        doThrow(new NullPointerException()).when(tripDao).findByDestination(null);
        doThrow(new NullPointerException()).when(tripDao).findByPrice(any(), isNull(BigDecimal.class));
        doThrow(new NullPointerException()).when(tripDao).findByPrice(isNull(BigDecimal.class), any());
        doThrow(new NullPointerException()).when(tripDao).findByTotalCapacity(null);
        doThrow(new NullPointerException()).when(tripDao).findByExcursion(null);

        assertThatThrownBy(() -> tripService.create(null))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.delete(null))
                .as("delete(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findById(null))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByName(null))
                .as("findByName(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByDate(null, t1.getDateFrom()))
                .as("findByDate(null, any()) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByDate(t1.getDateFrom(), null))
                .as("findByDate(any(), null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByDestination(null))
                .as("findByDestination(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByPrice(null, t1.getPrice()))
                .as("findByPrice(null, any()) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByPrice(t1.getPrice(), null))
                .as("findByPrice(any(), null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByAvailableCapacity(null))
                .as("findByAvailableCapacity(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> tripService.findByExcursion(null))
                .as("findByExcursion(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
    }

    /**
     * This is to check whether Service layer masks all exceptions from Dao
     */
    @Test
    public void testDataAccessError() {
        doThrow(new EntityExistsException()).when(tripDao).create(any());
        doThrow(new EntityNotFoundException()).when(tripDao).update(any());
        doThrow(new EntityNotFoundException()).when(tripDao).delete(any());
        doThrow(new EntityNotFoundException()).when(tripDao).findById(any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByName(any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByDate(any(), any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByDestination(any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByPrice(any(), any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByTotalCapacity(any());
        doThrow(new EntityNotFoundException()).when(tripDao).findByExcursion(any());

        assertThatThrownBy(() -> tripService.create(any()))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.delete(any()))
                .as("delete(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByName(any()))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByDate(any(), any()))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByDestination(any()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByPrice(any(), any()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByAvailableCapacity(any()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> tripService.findByExcursion(any()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(DataAccessException.class);
    }


}