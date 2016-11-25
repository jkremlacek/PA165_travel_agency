package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.facade.dto.*;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Created on 20.11.2016.
 *
 * @author Martin Salata
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class MappingServiceTest {

    @Inject
    private MappingService mappingService;

    private User c1, c2;
    private Trip t1;
    private Excursion e1;
    private Reservation r1, r2;
    private Set<Reservation> reservationSet;

    @Before
    public void setup() {
        c1 = new User();
        c1.setName("Martin");
        c1.setPersonalNumber(12345l);
        c1.setPasswordHash("password");
        c1.setIsAdmin(Boolean.FALSE);

        c2 = new User();
        c2.setName("Pepa");
        c2.setPersonalNumber(54321l);
        c2.setPasswordHash("password");
        c2.setIsAdmin(Boolean.FALSE);

        Calendar cal = Calendar.getInstance();
        cal.set(2017, 8, 15);
        Date dFrom = cal.getTime();
        cal.set(2017, 8, 25);
        Date dTo = cal.getTime();

        t1 = new Trip();
        t1.setName("Dovolenka v Egypte");
        t1.setDateFrom(dFrom);
        t1.setDateTo(dTo);
        t1.setDestination("Egypt");
        t1.setCapacity(100);
        t1.setPrice(BigDecimal.valueOf(10000));

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
        e1.setTrip(t1);
    }


    @Test
    public void testMappingUserstoDto() {
        List<User> userSet = Arrays.asList(c1,c2);
        List<UserDto> usrDtos = mappingService.mapTo(userSet,UserDto.class);
        assertThat(usrDtos)
                .as("Size should be equal to size of userSet")
                .hasSize(userSet.size());

        assertThat(usrDtos.get(0).getName())
                .as("Should contain name")
                .isIn(Arrays.asList(c1.getName(),c2.getName()));

        assertThat(usrDtos.get(1).getName())
                .as("Should contain name")
                .isIn(Arrays.asList(c1.getName(),c2.getName()));
    }

    @Test
    public void testMappingTripToDto() {
        TripDto trip = mappingService.mapTo(t1, TripDto.class);

        //TODO: wait for TripDto implementation
        assertThat(trip)
                .as("Should not be null")
                .isNotNull();
    }

    @Test
    public void testMappingExcursionToDto() {
        ExcursionDto excursionDto = mappingService.mapTo(e1, ExcursionDto.class);

        //TODO: wait for TripDto implementation
        assertThat(excursionDto)
                .as("Should not be null")
                .isNotNull();

        assertThat(excursionDto.getDestination())
                .as("Should contain destination")
                .isEqualTo(e1.getDestination());

        //TODO: if not compilable, comment and wait until TripDto is implemented
        assertThat(excursionDto.getTrip().getName())
                .as("Trip should me mapped properly to TripDto")
                .isEqualTo(t1.getName());
    }

    @Test
    public void testMappingExcursionFromCreateDto() {
        ExcursionCreateDto excursionDto = new ExcursionCreateDto();
        excursionDto.setName("Exkurzia");
        excursionDto.setDate(e1.getDate());
        excursionDto.setPrice(e1.getPrice());
        excursionDto.setDuration(e1.getDuration());
        excursionDto.setDestination(e1.getDestination());
        excursionDto.setDescription(e1.getDescription());

        Excursion ex = mappingService.mapTo(excursionDto, Excursion.class);

        //TODO: wait for TripDto implementation
        assertThat(ex)
                .as("Should not be null")
                .isNotNull();

        assertThat(ex.getName())
                .as("Names should match")
                .isEqualTo("Exkurzia");

        assertThat(ex.getDate())
                .as("Dates should match")
                .isEqualTo(e1.getDate());
    }



}