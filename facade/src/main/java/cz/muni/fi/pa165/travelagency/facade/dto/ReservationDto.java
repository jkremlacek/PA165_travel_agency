package cz.muni.fi.pa165.travelagency.facade.dto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 13.11.2016.
 *
 * @author Martin Salata
 */
public class ReservationDto {

    private Long id;

    private CustomerDto customer;

    private Set<ExcursionDto> excursionSet = new HashSet<>();

    private TripDto trip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public Set<ExcursionDto> getExcursionSet() {
        return Collections.unmodifiableSet(excursionSet);
    }

    public void addExcursion(ExcursionDto excursionDto) {
        this.excursionSet.add(excursionDto);
    }

    public TripDto getTrip() {
        return trip;
    }

    public void setTrip(TripDto trip) {
        this.trip = trip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationDto that = (ReservationDto) o;

        if (!getCustomer().equals(that.getCustomer())) return false;
        return getTrip().equals(that.getTrip());

    }

    @Override
    public int hashCode() {
        int result = getCustomer().hashCode();
        result = 31 * result + getTrip().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "id=" + id +
                ", customer=" + customer +
                ", excursionSet=" + excursionSet +
                ", trip=" + trip +
                '}';
    }
}
