/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.persistance.entity;

import java.util.Set;

/**
 *
 * @author jkrem
 * 
 * Represents reservation of trip for specific customer with choice of excursions. 
 */
public class Reservation {
	private int id; // TO DO it should be Integer/Long(better) type because null posibility
	private Customer customer;
	private Set<Excursion> excursionSet;
	private Trip trip;
	
	public Reservation (int id, Customer customer, Set<Excursion> excursionSet, Trip trip) {
		this.id = id;
		this.customer = customer;
		this.excursionSet = excursionSet;
		this.trip = trip;
	}
	
	public int getId() {
		return id;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<Excursion> getExcursionSet() {
		return excursionSet;
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
}
