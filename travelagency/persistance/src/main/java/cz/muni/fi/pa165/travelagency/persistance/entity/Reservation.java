/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.persistance.entity;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

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
	private long id;
	
	@NotNull
	private Customer customer;
	
	@NotNull
	private Set<Excursion> excursionSet;
	
	@NotNull
	private Trip trip;
	
	public Reservation () {}
	
	public Reservation (long id, Customer customer, Set<Excursion> excursionSet, Trip trip) {
		this.id = id;
		this.customer = customer;
		this.excursionSet = excursionSet;
		this.trip = trip;
	}
	
	public long getId() {
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

	public void setExcursionSet(Set<Excursion> excursionSet) {
		this.excursionSet = excursionSet;
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
