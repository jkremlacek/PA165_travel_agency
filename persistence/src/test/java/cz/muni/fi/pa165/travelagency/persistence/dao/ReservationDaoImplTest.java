package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.config.InMemorySpring;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.persistence.entity.Excursion;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;
import cz.muni.fi.pa165.travelagency.persistence.entity.Trip;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


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

    private User c1, c2;
    private Trip t1;
    private Excursion e1;
    private Reservation r1;

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

        t1.addExcursion(e1);

        r1 = new Reservation();
        r1.setUser(c1);
        r1.setTrip(t1);
        r1.addExcursion(e1);
    }

    @Test
    @Transactional
    public void testCreate() throws Exception {
        reservationDaoImpl.create(r1);

        assertThat(r1.getId())
                .as("Persisted reservation should have id assigned")
                .isNotNull();

        assertThat(em.find(Reservation.class, r1.getId()).getId())
                .as("Persisted reservation should be accessible by entity manager")
                .isEqualTo(r1.getId());
    }

    @Test
    @Transactional
    public void testUpdate() throws Exception {
        em.persist(r1);
        em.persist(c2);
        em.persist(e1);
        r1.setUser(c2);

        reservationDaoImpl.update(r1);

        assertThat(em.find(Reservation.class, r1.getId()).getUser().getId())
                .as("Updated reservation should have new user")
                .isEqualTo(c2.getId());
    }

    @Test
    @Transactional
    public void testDelete() throws Exception {
        reservationDaoImpl.delete(r1);

        assertThatThrownBy(() -> em.find(Reservation.class, r1.getId()))
                .as("Deleted reservation should not be accessible by entity manager")
                .isInstanceOf(IllegalArgumentException.class);

        em.persist(r1);

        reservationDaoImpl.delete(r1);

        assertThat(em.find(Reservation.class, r1.getId()))
                .as("Deleted reservation should not be accessible by entity manager")
                .isNull();
    }

    @Test
    @Transactional
    public void testFindById() throws Exception {
        em.persist(r1);

        Reservation found = reservationDaoImpl.findById(r1.getId());

        assertThat(found)
                .as("Found Reservation should be accessible by findById")
                .isNotNull();

        assertThat(found.getId())
                .as("Found Reservation should have the same id as the persisted one")
                .isEqualTo(r1.getId());
    }

    @Test
    @Transactional
    public void testFindAll() throws Exception {
        List<Reservation> all = reservationDaoImpl.findAll();
        assertThat(all.size())
                .as("No reservation should be found")
                .isEqualTo(0);

        em.persist(c1);
        em.persist(t1);
        em.persist(e1);
        em.persist(r1);
        all = reservationDaoImpl.findAll();

        assertThat(all.size())
                .as("Exactly 1 object should be found by dao")
                .isEqualTo(1);

        assertThat(all.get(0).getId())
                .as("The only reservation found should have ID of reservation persisted earlier")
                .isEqualTo(r1.getId());
    }

    @Test
    @Transactional
    public void testFindByUser() throws Exception {
        em.persist(c1);
        em.persist(t1);
        em.persist(e1);
        em.persist(r1);

        List<Reservation> found = reservationDaoImpl.findByUser(c1);

        assertThat(found)
                .as("Found list of reservations should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found reservation should be the one we persisted")
                .isEqualTo(r1.getId());
    }

    @Test
    @Transactional
    public void testFindByTrip() throws Exception {
        em.persist(c1);
        em.persist(t1);
        em.persist(e1);
        em.persist(r1);

        List<Reservation> found = reservationDaoImpl.findByTrip(t1);

        assertThat(found)
                .as("Found list of reservations should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found reservation should be the one we persisted")
                .isEqualTo(r1.getId());
    }

    @Test
    public void testCreateWithNullUser() {
        r1.setUser(null);
        assertThatThrownBy(() -> reservationDaoImpl.create(r1))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void testCreateWithNullTrip() {
        r1.setTrip(null);
        assertThatThrownBy(() -> reservationDaoImpl.create(r1))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(ValidationException.class);
    }

    @Test
    public void testNullArguments() {
        assertThatThrownBy(() -> reservationDaoImpl.create(null))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> reservationDaoImpl.update(null))
                .as("update(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> reservationDaoImpl.delete(null))
                .as("delete(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> reservationDaoImpl.findByUser(null))
                .as("findByUser(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> reservationDaoImpl.findByTrip(null))
                .as("findByTrip(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> reservationDaoImpl.findById(null))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @Transactional
    public void testInvalidUpdate() throws Exception {
        em.persist(c2);
        em.persist(e1);
        r1.setUser(c2);

        reservationDaoImpl.update(r1);

        assertThatThrownBy(() -> em.find(Reservation.class, r1.getId()))
                .as("Reservation should not be persisted by update without previous create")
                .isInstanceOf(IllegalArgumentException.class);
    }


}