package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.config.InMemorySpring;
import cz.muni.fi.pa165.travelagency.persistence.entity.User;
import cz.muni.fi.pa165.travelagency.persistence.entity.Reservation;

import java.util.Calendar;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * Testing implementation of User
 * 
 * @author Jakub Kremláček
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemorySpring.class)
@Transactional
public class UserDaoImplTest {
	
    @Inject
    private UserDao userDao;
	
    @PersistenceContext
    private EntityManager em;
	
    private User c1, c2;
    private Reservation r1, r2;	
	
    @Before
    public void setup() {
        Calendar calendar = Calendar.getInstance();
		
        calendar.set(1991,4,20);
		
        c1 = new User();
        c1.setName("Bob");
        c1.setPersonalNumber(12345l);
        c1.setBirthDate(calendar.getTime());
	c1.setPhoneNumber(123456789);
	c1.setMail("bob@bobspage.com");
        c1.setPasswordHash("password");
        c1.setIsAdmin(Boolean.FALSE);

        calendar.set(1992,6,2);
				
        c2 = new User();
        c2.setName("Eva");
        c2.setPersonalNumber(54321l);
	c2.setBirthDate(calendar.getTime());
	c2.setPhoneNumber(987654321);
	c2.setMail("eva@evaspage.com");
        c2.setPasswordHash("password");
        c2.setIsAdmin(Boolean.FALSE);
	
	r1 = new Reservation();
        r1.setUser(c1);
	c1.addReservation(r1);
		
	r2 = new Reservation();
        r2.setUser(c2);
	c2.addReservation(r2);
    }
	
    @Test
    @Transactional
    public void create() throws Exception {
        userDao.create(c1);

        assertThat(c1.getId())
                .as("Persisted user should have id assigned")
                .isNotNull();

        assertThat(em.find(User.class,c1.getId()).getId())
                .as("Persisted user should be accessible by entity manager")
                .isEqualTo(c1.getId());
    }
	
    @Test
    @Transactional
    public void update() throws Exception {
        em.persist(c1);
		
        c1.setName("Bobek");

        userDao.update(c1);

        assertThat(em.find(User.class, c1.getId()).getName())
                .as("Updated user should have new name")
                .isEqualTo(c1.getName());
    }
	
    @Test
    @Transactional
    public void delete() throws Exception {
        em.persist(c1);
        em.persist(r1);

        userDao.delete(c1);

        assertThat(em.find(User.class,c1.getId()))
                .as("Deleted user should not be accessible by entity manager")
                .isNull();
    }
	
    @Test
    @Transactional
    public void findById() throws Exception {
        em.persist(c1);
        em.persist(r1);
		
        assertThat(userDao.findById(c1.getId()+1))
                .as("No user should be found")
                .isNull();

        User found = userDao.findById(c1.getId());

        assertThat(found)
                .as("Found User should be accessible by findById")
                .isNotNull();

        assertThat(found.getId())
                .as("Found User should have the same id as the persisted one")
                .isEqualTo(c1.getId());
    }
	
    @Test
    @Transactional
    public void findAll() throws Exception {
        List<User> all = userDao.findAll();
        assertThat(all.size())
                .as("No user should be found")
                .isEqualTo(0);

        em.persist(c1);
        em.persist(r1);
		
        all = userDao.findAll();

        assertThat(all.size())
                .as("Exactly 1 object should be found by dao")
                .isEqualTo(1);

        assertThat(all.get(0).getId())
                .as("The only user found should have ID of user persisted earlier")
                .isEqualTo(c1.getId());
    }
	
    @Test
    public void nullParameters(){
        assertThatThrownBy(() -> userDao.create(null))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> userDao.update(null))
                .as("update(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> userDao.delete(null))
                .as("delete(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> userDao.findByReservation(null))
                .as("findByReservation(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> userDao.findByName(null))
                .as("findByName(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
		assertThatThrownBy(() -> userDao.findByMail(null))
                .as("findByMail(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> userDao.findById(null))
                .as("findById(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);

    }
	
    @Test
    @Transactional
    public void testFindByName() throws Exception {
        em.persist(c1);
        em.persist(c2);
		
        em.persist(r1);
        em.persist(r2);

        List<User> found = userDao.findByName(c1.getName());

        assertThat(found)
                .as("Found list of users should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found user should be the one with name Bob")
                .isEqualTo(c1.getId());
    }
	
    @Test
    @Transactional
    public void testFindByBirthDate() throws Exception {
        em.persist(c1);
        em.persist(c2);

        em.persist(r1);
        em.persist(r2);

        List<User> found = userDao.findByBirthDate(c1.getBirthDate());

        assertThat(found)
                .as("Found list of users should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found user should be the one with Bobs birthdate")
                .isEqualTo(c1.getId());
    }
	
    @Test
    @Transactional
    public void testFindByPersonalNumber() throws Exception {
        em.persist(c1);
        em.persist(c2);

        em.persist(r1);
        em.persist(r2);

        List<User> found = userDao.findByPersonalNumber(c1.getPersonalNumber());

        assertThat(found)
                .as("Found list of users should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found user should be the one with Bobs PN")
                .isEqualTo(c1.getId());
    }
	
    @Test
    @Transactional
    public void testFindByMail() throws Exception {
        em.persist(c1);
        em.persist(c2);

        em.persist(r1);
        em.persist(r2);

        List<User> found = userDao.findByMail(c1.getMail());

        assertThat(found)
                .as("Found list of users should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found user should be the one with Bobs mail")
                .isEqualTo(c1.getId());
    }
	
    @Test
    @Transactional
    public void testFindByPhoneNumber() throws Exception {
        em.persist(c1);
        em.persist(c2);

        em.persist(r1);
        em.persist(r2);
		
        List<User> found = userDao.findByPhoneNumber(c1.getPhoneNumber());

        assertThat(found)
                .as("Found list of users should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found user should be the one with Bobs phone number")
                .isEqualTo(c1.getId());
    }
	
    @Test
    @Transactional
    public void testFindByReservation() throws Exception {
        em.persist(c1);
        em.persist(c2);

        em.persist(r1);
        em.persist(r2);

        List<User> found = userDao.findByReservation(r1);

        assertThat(found)
                .as("Found list of users should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found user should be the one with Bobs reservation")
                .isEqualTo(c1.getId());
    }
}
