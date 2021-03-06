package cz.muni.fi.pa165.travelagency.facade.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Jakub Kremláček
 */
public class ExcursionCreateDto {

    private String name;

    private Date date;

    private Integer duration;

    private String destination;

    private BigDecimal price;

    private TripDto trip;

    private String description;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public TripDto getTrip() {
        return trip;
    }

    public void setTrip(TripDto trip) {
        this.trip = trip;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof ExcursionCreateDto)) return false;

        ExcursionCreateDto excursion = (ExcursionCreateDto) o;

        if (!getName().equals(excursion.getName())) return false;
        if (!getDate().equals(excursion.getDate())) return false;
        if (!getDuration().equals(excursion.getDuration())) return false;
        return getDestination().equals(excursion.getDestination());

    }
	
    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDate().hashCode();
        result = 31 * result + getDuration().hashCode();
        result = 31 * result + getDestination().hashCode();
        return result;
    }
	
   
}
