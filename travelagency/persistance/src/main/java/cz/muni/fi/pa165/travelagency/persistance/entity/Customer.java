package cz.muni.fi.pa165.travelagency.persistance.entity;

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
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;

/**
 *
 * Represents a customer of travel agency
 * 
 * @author Josef Pavelec, jospavelec@gmail.com
 */

@Entity
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 50, nullable = false)
    private String name;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthDate;
    
    @Column(nullable = false)
    private Integer personalNumber;
    
    @Column(length = 50, nullable = true)
    private String mail;
    
    @Column(nullable = true)
    private Integer phoneNumber;
    
    @ManyToMany//TO DO I'm not sure about mapping
    private Set<Reservation> reservations = new HashSet<Reservation>();

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.personalNumber);
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
        if (!(obj instanceof Customer)) {
            return false;
        }
        final Customer other = (Customer) obj;
        if (!Objects.equals(this.personalNumber, other.personalNumber)) {
            return false;
        }
        return true;
    }

    public Customer() {
    }

    public Customer(String name, Date birthDate, Integer personalNumber, String mail, Integer phoneNumber) {
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

    public Set<Reservation> getReservations() {
        return Collections.unmodifiableSet(reservations);
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
