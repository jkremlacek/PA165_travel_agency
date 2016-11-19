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
 * Represents reservation of trip for specific user with choice of excursions. 
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
	private User user;
	
	@OneToMany
	private Set<Excursion> excursionSet = new HashSet<>();
	
	@ManyToOne
	private Trip trip;
	
	public Reservation () {}
	
	public Reservation (User user, Set<Excursion> excursionSet, Trip trip) {
		this.user = user;
		this.excursionSet = excursionSet;
		this.trip = trip;
	}
	
	public Long getId() {
		return id;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
        hash = 59 * hash + Objects.hashCode(this.user) * 43 + Objects.hashCode(this.trip);
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
        if (!Objects.equals(this.user, other.user) || !Objects.equals(this.trip, other.trip)) {
            return false;
        }
        return true;
    }

    public void setId(long id) {
        this.id = id;
    }
}
