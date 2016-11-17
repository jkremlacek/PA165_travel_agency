package cz.muni.fi.pa165.travelagency.persistence.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Represents reservation of trip for specific customer with choice of excursions. 
 * 
 * @author Jakub Kremláček 
 * 
 */

@Entity
public class Reservation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Customer customer;
	
	@OneToMany
	private Set<Excursion> excursionSet = new HashSet<>();
	
	@ManyToOne
	private Trip trip;
	
	public Reservation () {}
	
	public Reservation (Customer customer, Set<Excursion> excursionSet, Trip trip) {
		this.customer = customer;
		this.excursionSet = excursionSet;
		this.trip = trip;
	}
	
	public Long getId() {
		return id;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<Excursion> getExcursionSet() {
		return Collections.unmodifiableSet(excursionSet);
	}

	public void addExcursion(Excursion excursion) {
		excursionSet.add(excursion);
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	
	@Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.customer) * 43 + Objects.hashCode(this.trip);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Reservation)) {
            return false;
        }
        final Reservation other = (Reservation) obj;
        if (!Objects.equals(this.customer, other.customer) || !Objects.equals(this.trip, other.trip)) {
            return false;
        }
        return true;
    }
}
