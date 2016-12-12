package cz.muni.fi.pa165.travelagency.facade.dto;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Objects;
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
        return excursionSet;
    }
    
    public void setExcursionSet(Set<ExcursionDto> excursions) {
        this.excursionSet = excursions;
    }

    public void addExcursion(ExcursionDto excursion) {
        this.excursionSet.add(excursion);
    }

      @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.getUser()) * 43 + Objects.hashCode(this.getTrip());
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
        if (!(obj instanceof ReservationCreateDto)) {
            return false;
        }
        final ReservationCreateDto other = (ReservationCreateDto) obj;
        if (!Objects.equals(this.getUser(), other.getUser()) || !Objects.equals(this.getTrip(), other.getTrip())) {
            return false;
        }
        return true;
    }

   
}
