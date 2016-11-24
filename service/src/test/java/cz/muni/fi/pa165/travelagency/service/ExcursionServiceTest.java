package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.persistence.dao.ExcursionDao;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.hibernate.service.spi.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.*;
import org.springframework.dao.DataAccessException;

/**
 *
 * @author Jakub Kremláček
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class ExcursionServiceTest {
	
    @Mock
    private ExcursionDao excursionDao;

    @InjectMocks
    private ExcursionService excursionService = new ExcursionServiceImpl();

    private Trip t;
    private Excursion e;

    @Before
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);

        Calendar cal = Calendar.getInstance();
        cal.set(2016, 12, 24);
        Date dateFrom = cal.getTime();
        cal.set(2017,1, 1);
        Date dateTo = cal.getTime();

        t = new Trip();
        t.setName("Christmas Trip");
        t.setDateFrom(dateFrom);
        t.setDateTo(dateTo);
        t.setDestination("Austria");
        t.setCapacity(50);
        t.setPrice(BigDecimal.valueOf(2000));

        cal.set(2016, 12, 25);
        Date excursionDate = cal.getTime();
        cal.set(0, 0, 0, 10, 0);
        Date excursionDuration = cal.getTime();

        e = new Excursion();
        e.setName("Skiing at Dachstein");
        e.setDate(excursionDate);
        e.setDuration(excursionDuration);
        e.setDestination("Dachstein");
        e.setPrice(BigDecimal.valueOf(100));

        t.addExcursion(e);
    }

    @Test
    public void testCreate() throws Exception {
        excursionService.create(e);
        verify(excursionDao).create(e);
    }

    @Test
    public void testDelete() throws Exception {
        excursionService.create(e);
        excursionService.delete(e);
        verify(excursionDao).delete(e);
    }

    @Test
    public void testGetById() throws Exception {
        excursionService.findById(e.getId());
        verify(excursionDao).findById(e.getId());
    }

    @Test
    public void testGetAll() throws Exception {
        excursionService.findAll();
        verify(excursionDao).findAll();
    }

    @Test
    public void testFindByName() throws Exception {
        excursionService.findByName(e.getName());
        verify(excursionDao).findByName(e.getName());
    }

    @Test
    public void testFindByPrice() throws Exception {
        excursionService.findByPrice(e.getPrice(), e.getPrice());
        verify(excursionDao).findByPrice(e.getPrice(), e.getPrice());
    }

    @Test
    public void testFindByDate() throws Exception {
        excursionService.findByDate(e.getDate(), e.getDate());
        verify(excursionDao).findByDate(e.getDate(), e.getDate());
    }

    @Test
    public void testFindByDestination() throws Exception {
        excursionService.findByDestination(e.getDestination());
        verify(excursionDao).findByDestination(e.getDestination());
    }

    @Test
    public void testFindByDuration() throws Exception {
        excursionService.findByDuration(e.getDuration(), e.getDuration());
        verify(excursionDao).findByDuration(e.getDuration(), e.getDuration());
    }

    @Test
    public void testFindByTrip() throws Exception {
        excursionService.findByTrip(e.getTrip());
        verify(excursionDao).findByTrip(e.getTrip());
    }

    @Test
    public void testNullArguments() {
        doThrow(new NullPointerException()).when(excursionDao).create(null);
        doThrow(new NullPointerException()).when(excursionDao).update(null);
        doThrow(new NullPointerException()).when(excursionDao).delete(null);
        doThrow(new NullPointerException()).when(excursionDao).findById(null);
        doThrow(new NullPointerException()).when(excursionDao).findByName(null);
        doThrow(new NullPointerException()).when(excursionDao).findByDate(isNull(Date.class), any());
        doThrow(new NullPointerException()).when(excursionDao).findByDate(any(), isNull(Date.class));
        doThrow(new NullPointerException()).when(excursionDao).findByDestination(null);
        doThrow(new NullPointerException()).when(excursionDao).findByPrice(any(), isNull(BigDecimal.class));
        doThrow(new NullPointerException()).when(excursionDao).findByPrice(isNull(BigDecimal.class), any());
        doThrow(new NullPointerException()).when(excursionDao).findByDuration(isNull(Date.class), any());
        doThrow(new NullPointerException()).when(excursionDao).findByDuration(any(), isNull(Date.class));
        doThrow(new NullPointerException()).when(excursionDao).findByTrip(null);

        assertThatThrownBy(() -> excursionService.create(null))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> excursionService.delete(null))
                .as("delete(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> excursionService.findById(null))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> excursionService.findByName(null))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> excursionService.findByDate(null, e.getDate()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> excursionService.findByDate(e.getDate(), null))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> excursionService.findByDestination(null))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> excursionService.findByPrice(null, e.getPrice()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> excursionService.findByPrice(e.getPrice(), null))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> excursionService.findByDuration(null, e.getDuration()))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> excursionService.findByDuration(e.getDuration(), null))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> excursionService.findByTrip(null))
                .as("findByCustomer(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    public void testDataAccessError() {
        doThrow(new EntityExistsException()).when(excursionDao).create(any());
        doThrow(new EntityNotFoundException()).when(excursionDao).update(any());
        doThrow(new EntityNotFoundException()).when(excursionDao).delete(any());
        doThrow(new EntityNotFoundException()).when(excursionDao).findById(any());
        doThrow(new EntityNotFoundException()).when(excursionDao).findByName(any());
        doThrow(new EntityNotFoundException()).when(excursionDao).findByDate(any(), any());
        doThrow(new EntityNotFoundException()).when(excursionDao).findByDestination(any());
        doThrow(new EntityNotFoundException()).when(excursionDao).findByPrice(any(), any());
        doThrow(new EntityNotFoundException()).when(excursionDao).findByDuration(any(), any());
        doThrow(new EntityNotFoundException()).when(excursionDao).findByTrip(any());

        assertThatThrownBy(() -> excursionService.create(any()))
                .as("create(null) should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> excursionService.delete(any()))
                .as("delete(null) should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> excursionService.findByName(any()))
                .as("findByName(null) should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> excursionService.findByDate(any(), any()))
                .as("findByDate(null) should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> excursionService.findByDestination(any()))
                .as("findByDestination(null) should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> excursionService.findByPrice(any(), any()))
                .as("findByPrice(null) should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> excursionService.findByDuration(any(), any()))
                .as("findByDuration(null) should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);

        assertThatThrownBy(() -> excursionService.findByTrip(any()))
                .as("findByTrip(null) should throw DataAccessException")
                .isInstanceOf(DataAccessException.class);
    }
	
	
	
}
