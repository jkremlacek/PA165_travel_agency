package cz.muni.fi.pa165.travelagency.facade.dto;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Temporal;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
/**
 *
 * @author Kateřina Caletková
 */
public class CustomerDto {
    
    @NotNull    
    private Long id;
    
    @NotBlank 
    private String name;
    
    @Temporal(javax.persistence.TemporalType.DATE) 
    private Date birthDate;
    
    @Column(nullable = false, unique = true)
    private Integer personalNumber;
    
    @Email
    @Column(length = 50, nullable = true)
    private String mail;
    
    @Column(nullable = true)
    private Integer phoneNumber;
    
    private Set<ReservationDto> reservations = new HashSet<ReservationDto>();

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.getName());
        hash = 47 * hash + Objects.hashCode(this.getPersonalNumber());
        hash = 47 * hash + Objects.hashCode(this.getMail());
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
        if (!(obj instanceof CustomerDto)) {
            return false;
        }
        final CustomerDto other = (CustomerDto) obj;
        if (!Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        if (!Objects.equals(this.getMail(), other.getMail())) {
            return false;
        }
        if (!Objects.equals(this.getPersonalNumber(), other.getPersonalNumber())) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(Integer personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<ReservationDto> getReservations() {
        return Collections.unmodifiableSet(reservations);
    }

    public void setReservations(Set<ReservationDto> reservations) {
        this.reservations = reservations;
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

