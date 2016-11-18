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
    private UserDto user;

    @NotNull
    private TripDto trip;

    private Set<ExcursionDto> excursionSet = new HashSet<>();

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
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

        if (!getUser().equals(that.getUser())) return false;
        return getTrip().equals(that.getTrip());

    }

    @Override
    public int hashCode() {
        int result = getUser().hashCode();
        result = 31 * result + getTrip().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ReservationCreateDto{" +
                "user=" + user +
                ", trip=" + trip +
                ", excursionSet=" + excursionSet +
                '}';
    }
}
