package cz.muni.fi.pa165.travelagency.facade.dto;
import java.util.HashSet;
import java.util.Set;

/**
 * DTO for user entity
 * @author Kateřina Caletková
 */
public class UserDto extends UserCreateDto{
    
    private Long id;
    
    private Set<ReservationDto> reservations = new HashSet<>();

    public Set<ReservationDto> getReservations() {
        return reservations;
    }
   
    public void addReservation(ReservationDto reservation) {
        this.reservations.add(reservation);
    }
    
    public void deleteReservation(ReservationDto reservation) {
        this.reservations.remove(reservation);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }    
    
}

