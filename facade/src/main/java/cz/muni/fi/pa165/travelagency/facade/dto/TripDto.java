package cz.muni.fi.pa165.travelagency.facade.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/**
 * DTO for entity Trip
 * 
 * @author Josef Pavelec, jospavelec@gmail.com
 */
public class TripDto {
    
    private Long id;
    private String name;
    private Date dateFrom;
    private Date dateTo;
    private String destination;
    private String description;
    private Integer capacity;
    private BigDecimal price;
    private Set<ExcursionDto> excursions = new HashSet<>();
    private Set<ReservationDto> reservations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Set<ExcursionDto> getExcursions() {
        return excursions;
    }

    public void addExcursion(ExcursionDto excursion) {
        this.excursions.add(excursion);
    }
    
    public void removeExcursion(ExcursionDto excursion) {
        this.excursions.remove(excursion);
    }

    public Set<ReservationDto> getReservations() {
        return reservations;
    }

    public void addReservation(ReservationDto reservation) {
        this.reservations.add(reservation);
    }
    
    public void removeReservation(ReservationDto reservation) {
        this.reservations.remove(reservation);
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
        if(!(obj instanceof TripDto)) {
            return false;
        }
        final TripDto other = (TripDto) obj;
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

    @Override
    public String toString() {
        return  "TripDto{" + 
                "id=" + id + 
                ", name=" + name + 
                ", dateFrom=" + dateFrom + 
                ", dateTo=" + dateTo + 
                ", destination=" + destination + 
                ", description=" + description + 
                ", capacity=" + capacity + 
                ", price=" + price + 
                ", excursions=" + excursions + 
                ", reservations=" + reservations + 
                '}';
    }
    
    
    
}
