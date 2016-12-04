package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.MappingService;
import cz.muni.fi.pa165.travelagency.service.TripService;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import cz.muni.fi.pa165.travelagency.service.facade.TripFacadeImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created on 24.11.2016.
 *
 * @author Martin Salata
 */
@ContextConfiguration(classes = ServiceConfig.class)
public class TripFacadeTest {

    @Mock
    private TripService tripService;

    @Mock
    private MappingService mappingService;

    @InjectMocks
    private TripFacade tripFacade = new TripFacadeImpl();

    private Trip trip;
    private TripCreateDto tripCreateDto;
    private TripDto tripDto;

    private Excursion excursion;
    private ExcursionDto excursionDto;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        Long id = 1L;
        String dst = "Fukusima";
        String name = "Vylet";
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017,1,20);
        Date dateFrom = calendar.getTime();
        calendar.clear();
        calendar.set(2017,1,28);
        Date dateTo = calendar.getTime();
        calendar.clear();
        calendar.set(0, 0, 0, 5, 0, 0);
        Date excurDur = calendar.getTime();
        String excurname = "Elektraren";

        BigDecimal price = BigDecimal.valueOf(500);

        trip = new Trip(id);
        trip.setCapacity(20);
        trip.setDateFrom(dateFrom);
        trip.setDateTo(dateTo);
        trip.setDestination(dst);
        trip.setName(name);
        trip.setPrice(price);

        tripCreateDto = new TripCreateDto();
        tripCreateDto.setCapacity(20);
        tripCreateDto.setDateFrom(dateFrom);
        tripCreateDto.setDateTo(dateTo);
        tripCreateDto.setDestination(dst);
        tripCreateDto.setName(name);
        tripCreateDto.setPrice(price);

        tripDto = new TripDto();
        tripDto.setId(id);
        tripDto.setCapacity(20);
        tripDto.setDateFrom(dateFrom);
        tripDto.setDateTo(dateTo);
        tripDto.setDestination(dst);
        tripDto.setName(name);
        tripDto.setPrice(price);

        when(mappingService.mapTo(tripCreateDto, Trip.class)).thenReturn(trip);
        when(mappingService.mapTo(tripDto, Trip.class)).thenReturn(trip);

        excursion = new Excursion();
        excursion.setName(excurname);
        excursion.setDate(dateFrom);
        excursion.setDuration(excurDur);
        excursion.setDestination(excurname);
        excursion.setPrice(price);

        excursionDto = new ExcursionDto();
        excursionDto.setName(excurname);
        excursionDto.setDate(dateFrom);
        excursionDto.setDuration(excurDur);
        excursionDto.setDestination(excurname);
        excursionDto.setPrice(price);

        when(mappingService.mapTo(excursionDto, Excursion.class)).thenReturn(excursion);

    }

    @Test
    public void testCreate() throws Exception {
        tripFacade.create(tripCreateDto);
        verify(tripService).create(trip);
    }

    @Test
    public void testUpdate() throws Exception {
        tripFacade.update(tripDto);
        verify(tripService).update(trip);
    }

    @Test
    public void testDelete() throws Exception {
        tripFacade.delete(tripDto);
        verify(tripService).delete(trip);
    }

    @Test
    public void testFindById() throws Exception {
        tripFacade.findById(5L);
        verify(tripService).findById(5L);
    }

    @Test
    public void testFindAll() throws Exception {
        tripFacade.findAll();
        verify(tripService).findAll();
    }

    @Test
    public void testFindByName() throws Exception {
        tripFacade.findByName("name");
        verify(tripService).findByName("name");
    }

    @Test
    public void testFindByDate() throws Exception {
        tripFacade.findByDate(trip.getDateFrom(),trip.getDateTo());
        verify(tripService).findByDate(trip.getDateFrom(),trip.getDateTo());
    }

    @Test
    public void testFindByDestination() throws Exception {
        tripFacade.findByDestination("dst");
        verify(tripService).findByDestination("dst");
    }

    @Test
    public void testFindByPrice() throws Exception {
        tripFacade.findByPrice(trip.getPrice(),trip.getPrice());
        verify(tripService).findByPrice(trip.getPrice(),trip.getPrice());
    }

    @Test
    public void testFindByAvailableCapacity() throws Exception {
        tripFacade.findByAvailableCapacity(5);
        verify(tripService).findByAvailableCapacity(5);
    }

    @Test
    public void testFindByExcursion() throws Exception {
        tripFacade.findByExcursion(excursionDto);
        verify(tripService).findByExcursion(excursion);
    }

    @Test
    public void testFindWithFreeCapacity() throws Exception {
        tripFacade.findWithFreeCapacity();
        verify(tripService).findWithFreeCapacity();
    }

    @Test
    public void testFindTripsInNextDays() throws Exception {
        tripFacade.findTripsInNextDays(5);
        verify(tripService).findTripsInNextDays(5);
    }

    @Test
    public void testFindTripParticipants() throws Exception {
        tripFacade.findTripParticipants(tripDto);
        verify(tripService).findTripParticipants(trip);
    }

    @Test
    public void testHasTripAvailableCapacity() throws Exception {
        tripFacade.hasTripAvailableCapacity(tripDto);
        verify(tripService).hasTripAvailableCapacity(trip);
    }

}