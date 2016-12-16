package cz.muni.fi.pa165.travelagency.facade;

import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.service.ExcursionService;
import cz.muni.fi.pa165.travelagency.service.MappingService;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import cz.muni.fi.pa165.travelagency.service.facade.ExcursionFacadeImpl;
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
 * @author Jakub Kremláček
 */

@ContextConfiguration(classes = ServiceConfig.class)
public class ExcursionFacadeTest {
	
    @Mock
    private ExcursionService excursionService;

    @Mock
    private MappingService mappingService;
	
    @InjectMocks
    private ExcursionFacade excursionFacade = new ExcursionFacadeImpl();
	
    private Trip t;
    private TripDto tripDto;

    private Excursion e;
    private ExcursionDto excursionDto;
    private ExcursionCreateDto excursionCreateDto;
	
    @Before
    public void setup() {
    MockitoAnnotations.initMocks(this);

        Long tripId = 12L;
        Calendar cal = Calendar.getInstance();
        cal.set(2017, 12, 24);
        Date dateFrom = cal.getTime();
        cal.set(2018,1, 1);
        Date dateTo = cal.getTime();
        String tripDestination = "Austria";
        String tripName = "Christmas Trip";
        BigDecimal tripPrice = BigDecimal.valueOf(2000);
        Integer tripCapacity = 50;

        t = new Trip(tripId);
        t.setName(tripName);
        t.setDateFrom(dateFrom);
        t.setDateTo(dateTo);
        t.setDestination(tripDestination);
        t.setCapacity(tripCapacity);
        t.setPrice(tripPrice);

        tripDto = new TripDto();
        tripDto.setId(tripId);
        tripDto.setCapacity(tripCapacity);
        tripDto.setDateFrom(dateFrom);
        tripDto.setDateTo(dateTo);
        tripDto.setDestination(tripDestination);
        tripDto.setName(tripName);
        tripDto.setPrice(tripPrice);
		
        when(mappingService.mapTo(tripDto, Trip.class)).thenReturn(t);

        cal.set(2017, 12, 25);
        Date excursionDate = cal.getTime();
        cal.set(0, 0, 0, 10, 0);
        Integer excursionDuration = 8;

        String excursionName = "Skiing at Dachstein";
        String excursionDestination = "Dachstein";
        BigDecimal excursionPrice = BigDecimal.valueOf(100);

        e = new Excursion();
        e.setName(excursionName);
        e.setDate(excursionDate);
        e.setDuration(excursionDuration);
        e.setDestination(excursionDestination);
        e.setPrice(excursionPrice);

        excursionCreateDto = new ExcursionCreateDto();
        excursionCreateDto.setName(excursionName);
        excursionCreateDto.setDate(dateFrom);
        excursionCreateDto.setDuration(excursionDuration);
        excursionCreateDto.setDestination(excursionName);
        excursionCreateDto.setPrice(excursionPrice);
		
        excursionDto = new ExcursionDto();
        excursionDto.setName(excursionName);
        excursionDto.setDate(dateFrom);
        excursionDto.setDuration(excursionDuration);
        excursionDto.setDestination(excursionName);
        excursionDto.setPrice(excursionPrice);

        when(mappingService.mapTo(excursionDto, Excursion.class)).thenReturn(e);
        when(mappingService.mapTo(excursionCreateDto, Excursion.class)).thenReturn(e);
    }
	
    @Test
    public void testCreate() throws Exception {
        excursionFacade.create(excursionCreateDto);
        verify(excursionService).create(e);
    }

    @Test
    public void testUpdate() throws Exception {
        excursionFacade.update(excursionDto);
        verify(excursionService).update(e);
    }
    
    @Test
    public void testDelete() throws Exception {
        excursionFacade.delete(excursionDto);
        verify(excursionService).delete(e);
    }

    @Test
    public void testFindById() throws Exception {
        excursionFacade.findById(12L);
        verify(excursionService).findById(12L);
    }

    @Test
    public void testFindAll() throws Exception {
        excursionFacade.findAll();
        verify(excursionService).findAll();
    }

    @Test
    public void testFindByName() throws Exception {
        excursionFacade.findByName("name");
        verify(excursionService).findByName("name");
    }

    @Test
    public void testFindByDate() throws Exception {
        excursionFacade.findByDate(e.getDate(),e.getDate());
        verify(excursionService).findByDate(e.getDate(),e.getDate());
    }
    
//    @Test
//    public void testFindByDuration() throws Exception {
//        excursionFacade.findByDuration(e.getDuration(), e.getDuration());
//        verify(excursionService).findByDuration(e.getDuration(), e.getDuration());
//    }

    @Test
    public void testFindByDestination() throws Exception {
        excursionFacade.findByDestination("dst");
        verify(excursionService).findByDestination("dst");
    }

    @Test
    public void testFindByPrice() throws Exception {
        excursionFacade.findByPrice(e.getPrice(),e.getPrice());
        verify(excursionService).findByPrice(e.getPrice(),e.getPrice());
    }
    
    @Test
    public void testFindByTrip() throws Exception {
        excursionFacade.findByTrip(excursionDto.getTrip());
        verify(excursionService).findByTrip(e.getTrip());
    }
}
