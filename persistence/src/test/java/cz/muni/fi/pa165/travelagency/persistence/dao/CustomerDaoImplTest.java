package cz.muni.fi.pa165.travelagency.persistence.dao;

import cz.muni.fi.pa165.travelagency.persistence.config.InMemorySpring;
import cz.muni.fi.pa165.travelagency.persistence.entity.Customer;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 *
 * Testing implementation of Customer
 * 
 * @author Jakub Kremláček
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = InMemorySpring.class)
@Transactional
public class CustomerDaoImplTest {
	@Inject
    private CustomerDao customerDaoImpl;
	
	@PersistenceContext
    private EntityManager em;
	
	private Customer c1, c2;
	private Reservation r1, r2;	
	
	@Before
    public void setup() {
		Calendar calendar = Calendar.getInstance();
		
        calendar.set(1991,4,20);
		
        c1 = new Customer();
        c1.setName("Bob");
        c1.setPersonalNumber(12345);
		c1.setBirthDate(calendar.getTime());
		c1.setPhoneNumber(123456789);
		c1.setMail("bob@bobspage.com");

        calendar.set(1992,6,2);
				
        c2 = new Customer();
        c2.setName("Eva");
        c2.setPersonalNumber(54321);
		c2.setBirthDate(calendar.getTime());
		c2.setPhoneNumber(987654321);
		c2.setMail("eva@evaspage.com");
	
		r1 = new Reservation();
        r1.setCustomer(c1);
		c1.addReservation(r1);
		
		r2 = new Reservation();
        r2.setCustomer(c2);
		c2.addReservation(r2);
	}
	
	@Test
    @Transactional
    public void create() throws Exception {
        customerDaoImpl.create(c1);

        assertThat(c1.getId())
                .as("Persisted customer should have id assigned")
                .isNotNull();

        assertThat(em.find(Customer.class,c1.getId()).getId())
                .as("Persisted customer should be accessible by entity manager")
                .isEqualTo(c1.getId());
    }
	
	@Test
    @Transactional
    public void update() throws Exception {
        em.persist(c1);
		
        c1.setName("Bobek");

        customerDaoImpl.update(c1);

        assertThat(em.find(Customer.class, c1.getId()).getName())
                .as("Updated customer should have new name")
                .isEqualTo(c1.getName());
    }
	
	@Test
    @Transactional
    public void delete() throws Exception {
        em.persist(c1);
		em.persist(r1);

        customerDaoImpl.delete(c1);

        assertThat(em.find(Customer.class,c1.getId()))
                .as("Deleted customer should not be accessible by entity manager")
                .isNull();
    }
	
	@Test
    @Transactional
    public void findById() throws Exception {
		em.persist(c1);
		em.persist(r1);
		
        assertThat(customerDaoImpl.findById(c1.getId()+1))
                .as("No customer should be found")
                .isNull();

        Customer found = customerDaoImpl.findById(c1.getId());

        assertThat(found)
                .as("Found Customer should be accessible by findById")
                .isNotNull();

        assertThat(found.getId())
                .as("Found Customer should have the same id as the persisted one")
                .isEqualTo(c1.getId());
    }
	
	@Test
    @Transactional
    public void findAll() throws Exception {
        List<Customer> all = customerDaoImpl.findAll();
        assertThat(all.size())
                .as("No customer should be found")
                .isEqualTo(0);

        em.persist(c1);
        em.persist(r1);
		
        all = customerDaoImpl.findAll();

        assertThat(all.size())
                .as("Exactly 1 object should be found by dao")
                .isEqualTo(1);

        assertThat(all.get(0).getId())
                .as("The only customer found should have ID of customer persisted earlier")
                .isEqualTo(c1.getId());
    }
	
	@Test
    public void nullParameters(){
        assertThatThrownBy(() -> customerDaoImpl.create(null))
                .as("create(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> customerDaoImpl.update(null))
                .as("update(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> customerDaoImpl.delete(null))
                .as("delete(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> customerDaoImpl.findByReservation(null))
                .as("findByReservation(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> customerDaoImpl.findByName(null))
                .as("findByName(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
		assertThatThrownBy(() -> customerDaoImpl.findByMail(null))
                .as("findByMail(null) should throw NullPointerException")
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> customerDaoImpl.findById(null))
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

        List<Customer> found = customerDaoImpl.findByName(c1.getName());

        assertThat(found)
                .as("Found list of customers should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found customer should be the one with name Bob")
                .isEqualTo(c1.getId());
    }
	
	@Test
    @Transactional
    public void testFindByBirthDate() throws Exception {
        em.persist(c1);
		em.persist(c2);
		
		em.persist(r1);
		em.persist(r2);

        List<Customer> found = customerDaoImpl.findByBirthDate(c1.getBirthDate());

        assertThat(found)
                .as("Found list of customers should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found customer should be the one with Bobs birthdate")
                .isEqualTo(c1.getId());
    }
	
	@Test
    @Transactional
    public void testFindByPersonalNumber() throws Exception {
        em.persist(c1);
		em.persist(c2);
		
		em.persist(r1);
		em.persist(r2);

        List<Customer> found = customerDaoImpl.findByPersonalNumber(c1.getPersonalNumber());

        assertThat(found)
                .as("Found list of customers should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found customer should be the one with Bobs PN")
                .isEqualTo(c1.getId());
    }
	
	@Test
    @Transactional
    public void testFindByMail() throws Exception {
        em.persist(c1);
		em.persist(c2);
		
		em.persist(r1);
		em.persist(r2);

        List<Customer> found = customerDaoImpl.findByMail(c1.getMail());

        assertThat(found)
                .as("Found list of customers should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found customer should be the one with Bobs mail")
                .isEqualTo(c1.getId());
    }
	
	@Test
    @Transactional
    public void testFindByPhoneNumber() throws Exception {
        em.persist(c1);
		em.persist(c2);

		em.persist(r1);
		em.persist(r2);
		
        List<Customer> found = customerDaoImpl.findByPhoneNumber(c1.getPhoneNumber());

        assertThat(found)
                .as("Found list of customers should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found customer should be the one with Bobs phone number")
                .isEqualTo(c1.getId());
    }
	
	@Test
    @Transactional
    public void testFindByReservation() throws Exception {
        em.persist(c1);
		em.persist(c2);
		
		em.persist(r1);
		em.persist(r2);

        List<Customer> found = customerDaoImpl.findByReservation(r1);

        assertThat(found)
                .as("Found list of customers should have exactly 1 item")
                .hasSize(1);

        assertThat(found.get(0).getId())
                .as("The only found customer should be the one with Bobs reservation")
                .isEqualTo(c1.getId());
    }
}
