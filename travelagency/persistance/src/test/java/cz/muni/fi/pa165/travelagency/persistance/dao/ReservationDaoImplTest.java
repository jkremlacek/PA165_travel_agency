package cz.muni.fi.pa165.travelagency.persistance.dao;

import cz.muni.fi.pa165.travelagency.persistance.config.InMemorySpring;
import cz.muni.fi.pa165.travelagency.persistance.entity.Customer;
import cz.muni.fi.pa165.travelagency.persistance.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistance.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistance.entity.Trip;
import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;

/**
 * Created on 21.10.2016.
 *
 * @author Martin Salata
 */
@ContextConfiguration(classes = InMemorySpring.class)
public class ReservationDaoImplTest extends AbstractTestNGSpringContextTests {
    @PersistenceContext
    private EntityManager em;

    @PersistenceUnit(name = "default")
    private EntityManagerFactory emf;

    private Customer c1;
    private Trip t1;
    private Excursion e1;
    private Reservation r1;

    @BeforeClass
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

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        em.persist(c1);
        em.persist(t1);
        em.persist(e1);
        em.persist(r1);

        em.getTransaction().commit();
        em.close();


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
    public void findAll() throws Exception {
        EntityManager em = emf.createEntityManager();
        List<Reservation> all = em.createQuery("SELECT r FROM Reservation r", Reservation.class).getResultList();
        Assert.assertEquals(1, all.size());
        Assert.assertEquals(r1.getId(), all.get(0).getId());
    }

    @Test
    public void testFindByCustomer() throws Exception {

    }

    @Test
    public void testFindByTrip() throws Exception {

    }

}