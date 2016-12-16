package cz.muni.fi.pa165.travelagency.persistence.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.Future;

/**
 * Created on 19.10.2016.
 * Represents an excursion during a trip
 *
 * @author Martin Salata
 */
@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "date", "duration","destination"})
)
public class Excursion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @Future
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @NotNull
    private Integer duration;

    @NotNull
    private String destination;

    @NotNull
    private BigDecimal price;

    @ManyToOne
    private Trip trip;

    @ManyToMany
    private Set<Reservation> reservations = new HashSet<>();

    private String description;

    /**
     * Non-parametric constructor for Excursion entity
     */
    public Excursion() {
    }

    public Excursion(String name, Date date, Integer duration, String destination, BigDecimal price) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.destination = destination;
        this.price = price;
    }

    public Excursion(Long id) {
        this.id = id;
    }


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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Excursion)) return false;

        Excursion excursion = (Excursion) o;

        if (!getName().equals(excursion.getName())) return false;
        if (!getDate().equals(excursion.getDate())) return false;
        if (!getDuration().equals(excursion.getDuration())) return false;
        return getDestination().equals(excursion.getDestination());

    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDate(), getDuration(), getDestination());
    }

}
