package cz.muni.fi.pa165.travelagency.service;

import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.service.config.ServiceConfig;
import org.assertj.core.internal.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
        c1.setPersonalNumber(12345);
        c1.setPasswordHash("password");
        c1.setIsAdmin(false);

        c2 = new User();
        c2.setName("Pepa");
        c2.setPersonalNumber(54321);
        c2.setPasswordHash("password");
        c2.setIsAdmin(false);

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
    }

    @Test
    public void testMappingUsers() {
        List<User> userSet = Arrays.asList(c1,c2);
        List<UserDto> usrDtos = mappingService.mapTo(userSet,UserDto.class);
        assertThat(usrDtos)
                .as("Size should be equal to size of userSet")
                .hasSize(userSet.size());

        assertThat(usrDtos.get(0).getName())
                .as("Should contain name")
                .isIn(Arrays.asList(c1.getName(),c2.getName()));
    }

    @Test
    public void testMappingTrip() {
        TripDto trip = mappingService.mapTo(t1, TripDto.class);

        //TODO: wait for TripDto implementation
        assertThat(trip)
                .as("Should not be null")
                .isNotNull();
    }
}