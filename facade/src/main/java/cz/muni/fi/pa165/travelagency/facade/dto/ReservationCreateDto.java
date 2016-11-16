package cz.muni.fi.pa165.travelagency.facade.dto;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created on 16.11.2016.
 *
 * @author Martin Salata
 */
public class ReservationCreateDto {

    @NotNull
    private CustomerDto customer;

    @NotNull
    private TripDto trip;

    private Set<ExcursionDto> excursionSet = new HashSet<>();

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public TripDto getTrip() {
        return trip;
    }

    public void setTrip(TripDto trip) {
        this.trip = trip;
    }

    public Set<ExcursionDto> getExcursionSet() {
        return Collections.unmodifiableSet(excursionSet);
    }

    public void addExcursion(ExcursionDto excursion) {
        this.excursionSet.add(excursion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationCreateDto that = (ReservationCreateDto) o;

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
        return "ReservationCreateDto{" +
                "customer=" + customer +
                ", trip=" + trip +
                ", excursionSet=" + excursionSet +
                '}';
    }
}
