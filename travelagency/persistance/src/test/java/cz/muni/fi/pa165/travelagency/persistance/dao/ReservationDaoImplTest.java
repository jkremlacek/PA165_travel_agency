package cz.muni.fi.pa165.travelagency.persistance.dao;

import cz.muni.fi.pa165.travelagency.persistance.config.InMemorySpring;
import cz.muni.fi.pa165.travelagency.persistance.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistance.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistance.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistance.entity.Trip;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import org.junit.Before;
import org.junit.Test;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//import static org.testng.Assert.*;

/**
 * Created on 21.10.2016.
 *
 * @author Martin Salata
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemorySpring.class)
@Transactional
public class ReservationDaoImplTest {

    @Inject
    private ReservationDao reservationDaoImpl;

    @PersistenceContext
    private EntityManager em;

    private Customer c1;
    private Trip t1;
    private Excursion e1;
    private Reservation r1;

    @Before
    public void setup() {
        c1 = new Customer();
        c1.setName("Martin");
        c1.setPersonalNumber(12345);

        Calendar cal = Calendar.getInstance();
        cal.set(2017,8,15);
        Date dFrom = cal.getTime();
        cal.set(2017,8,25);
        Date dTo = cal.getTime();

        t1 = new Trip();
        t1.setName("Dovolenka v Egypte");
        t1.setDateFrom(dFrom);
        t1.setDateTo(dTo);
        t1.setDestination("Egypt");
        t1.setCapacity(100);
        t1.setPrice(BigDecimal.valueOf(10000));

        cal.set(2017,8,20,10,0,0);
        Date excursionDate = cal.getTime();
        cal.set(0,0,0,5,0,0);
        Date excursionDuration = cal.getTime();

        e1 = new Excursion();
        e1.setName("Egypt- pyramidy");
        e1.setDate(excursionDate);
        e1.setDuration(excursionDuration);
        e1.setDestination("Kahira");
        e1.setPrice(BigDecimal.valueOf(800));

        t1.addExcursion(e1);

        r1 = new Reservation();
        r1.setCustomer(c1);
        r1.setTrip(t1);
        r1.addExcursion(e1);
    }

    @Test
    public void create() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void findById() throws Exception {

    }

    @Test
    @Transactional
    public void findAll() throws Exception {
        em.persist(c1);
        em.persist(t1);
        em.persist(e1);
        em.persist(r1);
        List<Reservation> all = reservationDaoImpl.findAll();
        Assert.assertEquals(1, all.size());
        Assert.assertEquals(r1.getId(), all.get(0).getId());
    }

    @Test
    @Transactional
    public void testFindByCustomer() throws Exception {
        List<Reservation> all = reservationDaoImpl.findAll();
        Assert.assertEquals(0, all.size());
    }

    @Test
    public void testFindByTrip() throws Exception {

    }

}