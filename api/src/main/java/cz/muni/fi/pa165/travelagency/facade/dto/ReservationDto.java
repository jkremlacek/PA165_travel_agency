package cz.muni.fi.pa165.travelagency.facade.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created on 13.11.2016.
 *
 * @author Martin Salata
 */
public class ReservationDto {

    private Long id;

    private UserDto user;

    private Set<ExcursionDto> excursionSet = new HashSet<>();

    private TripDto trip;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
    
    public void setExcursionSet(Set<ExcursionDto> excursions) {
        this.excursionSet = excursions;
    }

    public Set<ExcursionDto> getExcursionSet() {
        return excursionSet;
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
        if (!(obj instanceof ReservationDto)) {
            return false;
        }
        final ReservationDto other = (ReservationDto) obj;
        if (!Objects.equals(this.getUser(), other.getUser()) || !Objects.equals(this.getTrip(), other.getTrip())) {
            return false;
        }
        return true;
    }

   
}
