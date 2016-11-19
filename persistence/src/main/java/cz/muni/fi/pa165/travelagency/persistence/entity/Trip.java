package cz.muni.fi.pa165.travelagency.persistence.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Entity trip in travel agency
 * @author Katerina Caletkova
 */
@Entity
public class Trip {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id; 
   
   @NotBlank
   private String name;
   
   @NotNull
   @Temporal(TemporalType.TIMESTAMP)
   private Date dateFrom;
   
   @NotNull
   @Temporal(TemporalType.TIMESTAMP)
   private Date dateTo;
   
   @NotBlank
   private String destination;
   
   private String description;
   
   @Min(1)
   @NotNull
   private Integer capacity;
   
   @Min(0)
   @NotNull
   private BigDecimal price;
   
   @OneToMany(mappedBy="trip")
   private Set<Excursion> excursions = new HashSet<Excursion>();
   
   @OneToMany(mappedBy="trip")
   private Set<Reservation> reservations = new HashSet<Reservation>();

    public Trip() {
    }

    public Trip(String name, Date dateFrom, Date dateTo, String destination, Integer capacity, BigDecimal price) {
        this.name = name;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.destination = destination;
        this.capacity = capacity;
        this.price = price;
    }

    public Trip(long id) {
        this.id = id;
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the dateFrom
     */
    public Date getDateFrom() {
        return dateFrom;
    }

    /**
     * @param dateFrom the dateFrom to set
     */
    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    /**
     * @return the dateTo
     */
    public Date getDateTo() {
        return dateTo;
    }

    /**
     * @param dateTo the dateTo to set
     */
    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the capacity
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }
    
    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the excursions
     */
    public Set<Excursion> getExcursions() {
        return Collections.unmodifiableSet(excursions);
    }
    
    /**
     * @param e the excursion to add
     */
    public void addExcursion(Excursion e) {
        excursions.add(e);
    }
    
    /**
     * @param e the excursion to delete
     */
    public void deleteExcursion(Excursion e){
        excursions.remove(e);
    }
    
    /**
     * @return the reservations
     */
    public Set<Reservation> getReservations() {
        return Collections.unmodifiableSet(reservations);
    }
    
    /**
     * @param r the reservation to add
     */
    public void addReservation(Reservation r) {
        reservations.add(r);
    }
    
    /**
     * @param r the reservation to delete
     */
    public void deleteReservation(Reservation r){
        reservations.remove(r);
    }

    @Override
    public String toString() {
        return "Trip{" + "id=" + getId() + ", name=" + getName() + ", dateFrom=" + getDateFrom() + ", dateTo=" + getDateTo() + ", destination=" + getDestination() + ", description=" + getDescription() + ", capacity=" + getCapacity() + ", price=" + getPrice() + ", excursions=" + getExcursions() + ", reservations=" + getReservations() + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.getName());
        hash = 23 * hash + Objects.hashCode(this.getDateFrom());
        hash = 23 * hash + Objects.hashCode(this.getDateTo());
        hash = 23 * hash + Objects.hashCode(this.getDestination());
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
        if(!(obj instanceof Trip)) {
            return false;
        }
        final Trip other = (Trip) obj;
        if (!Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        if (!Objects.equals(this.getDestination(), other.getDestination())) {
            return false;
        }
        if (!Objects.equals(this.getDateFrom(), other.getDateFrom())) {
            return false;
        }
        if (!Objects.equals(this.getDateTo(), other.getDateTo())) {
            return false;
        }
        return true;
    }
}