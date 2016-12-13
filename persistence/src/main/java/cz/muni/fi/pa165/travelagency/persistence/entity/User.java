package cz.muni.fi.pa165.travelagency.persistence.entity;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * Represents a user of travel agency
 * 
 * @author Josef Pavelec, jospavelec@gmail.com
 */

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"personalNumber"})
)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private String passwordHash;
    
    @NotNull
    private Boolean isAdmin;
    
    @NotBlank
    private String name;
    
    @Past     
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthDate;
    
    @Column(nullable = false, unique = true)
    private Long personalNumber;
    
    @Email
    @NotNull
    @Column(unique = true)
    private String mail;
    
    @Column(nullable = true)
    private Integer phoneNumber;
    
    @OneToMany
    private Set<Reservation> reservations = new HashSet<Reservation>();

    public User(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 7;        
        hash = 47 * hash + Objects.hashCode(this.getPersonalNumber());        
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
        if (!(obj instanceof User)) {
            return false;
        }
        final User other = (User) obj;
       
        if (!Objects.equals(this.getPersonalNumber(), other.getPersonalNumber())) {
            return false;
        }
        return true;
    }

    public User() {
    }

    public User(String name, Date birthDate, Long personalNumber, String mail, Integer phoneNumber) {
        this.name = name;
        this.birthDate = birthDate;
        this.personalNumber = personalNumber;
        this.mail = mail;
        this.phoneNumber = phoneNumber;
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

    public Long getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(Long personalNumber) {
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

    public Set<Reservation> getReservations() {
        return Collections.unmodifiableSet(reservations);
    }

   public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
    
    public void deleteReservation(Reservation reservation) {
        this.reservations.remove(reservation);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
}
