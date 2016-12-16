package cz.muni.fi.pa165.travelagency.controllers;

import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.rest.ApiUris;
import cz.muni.fi.pa165.travelagency.exceptions.AlreadyExistingException;
import cz.muni.fi.pa165.travelagency.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.travelagency.exceptions.NotAllowToDeleteException;
import cz.muni.fi.pa165.travelagency.exceptions.NotFoundException;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.TripCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import cz.muni.fi.pa165.travelagency.service.exception.PersistenceException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.validation.ValidationException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * Rest controller for trips
 * @author Katerina Caletkova
 */

@RestController
@RequestMapping(ApiUris.TRIPS)
public class RestTripsController {
    
    @Inject
    private TripFacade tripFacade;
    
    @Inject
    private ExcursionFacade excursionFacade;
    
    @Inject
    private ReservationFacade reservationFacade;
    /**
     * Get trips
     * @return list of trips
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findAll() { 
        try {
            return tripFacade.findAll();   
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);        
        }  
    }
    
    /**
     * Get trip with gived id
     * @param id id of trip
     * @return trip with gived id
     * @throws Exception if id is null
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TripDto findById(@PathVariable("id") Long id) throws Exception {
       try {
            return tripFacade.findById(id);
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);        
        }               
    }
    
    /**
     * Delete trip with gived id
     * @param id id of trip
     * @throws Exception if there isn't trip with gived id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void delete(@PathVariable("id") Long id) throws Exception {   
        try {            
            if (reservationFacade.findByTrip(tripFacade.findById(id)).isEmpty()) {
                tripFacade.delete(tripFacade.findById(id));   
            } else {
                throw new NotAllowToDeleteException("Trip with id " + id + "hase raservations");
            }
        }
        catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);
        
        }
            
    }
    
    /**
     * Create a trip
     * @param tripCreateDto trip which should be created
     * @return id of created trip
     * @throws Exception ig trip already exist
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final Long create(@RequestBody TripCreateDto tripCreateDto) throws Exception {

        try {
            return tripFacade.create(tripCreateDto);
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new InvalidParameterException(ex);
        } catch (PersistenceException ex) {
            throw new AlreadyExistingException(ex);
        }
    }
    
    /**
     * Update trip
     * @param tripDto which will be update
     * @throws Exception if tripUpdateDto contains invalid parameter
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void update(@RequestBody TripDto tripDto) throws Exception {
        try {
            tripFacade.update(tripDto);            
        } catch (NullPointerException | IllegalArgumentException | ValidationException ex) {
            throw new NotFoundException(ex);
        }        
    }
    
    /**
     * Find trips with available capacity
     * @param capacity value of available capacity 
     * @return Trips with available capacity
     */
    @RequestMapping(value = "/capacity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByAvailableCapacity(@RequestParam(value = "capacity", required = true) Integer capacity) {
        try {
            return tripFacade.findByAvailableCapacity(capacity);
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);
        } 
    }      
 
    /**
     * Find trips with date in choosen interval
     * @param from the earliest date of starting trip
     * @param to the latest date of starting trip
     * @return trips with date in choosen interval
     * @throws java.text.ParseException
     */
    @RequestMapping(value = "/date",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByDate(@RequestParam(value = "from", required = true) String from,
                                            @RequestParam(value = "to", required = true) String to) throws ParseException {
        
        try {                   
            DateFormat format = new SimpleDateFormat("YYYY-MM-DD", Locale.ENGLISH);
            Date dateFrom = format.parse(from);
            Date dateTo = format.parse(to);
            return tripFacade.findByDate(dateFrom,dateTo);     
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);
        } 
    }    
    
    /**
     * Find trips with choosen excursion
     * @param excursionId excursionId which is avalaible from returning trips
     * @return trips with choosen excursion
     */
    @RequestMapping(value = "/excursion",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByExcursion(@RequestParam(value = "excursionId", required = true) Long excursionId) {
        try {
            return tripFacade.findByExcursion(excursionFacade.findById(excursionId));
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);
        } 
    }
      
    /**
     * Find trips with given name
     * @param name name of trip
     * @return trips with given name
     */ 
    @RequestMapping(value = "/name",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByName(@RequestParam(value = "name", required = true) String name) {
        try {
            return tripFacade.findByName(name);
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);
        } 
    }
    
    /**
     * Find trips with price in choosen interval
     * @param min min value of price for trip
     * @param max max value of price for trip
     * @return trips with price in choosen interval
     */
    @RequestMapping(value = "/price",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByPrice(@RequestParam(value = "min", required = true) BigDecimal min,
                                            @RequestParam(value = "max", required = true) BigDecimal max) {
        try {
            return tripFacade.findByPrice(min,max);
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);
        } 
    }
     
    /**
     * Find trip's participants for given trip
     * @param tripId tripId for which we want to know its participants
     * @return trip's participants for given trip
     */
    @RequestMapping(value = "/trip",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDto> findTripParticipants(@RequestParam(value = "tripId", required = true) Long tripId) {
        try {
            return tripFacade.findTripParticipants(tripFacade.findById(tripId));
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);
        } 
    }
   
    /**
     * Find trips in next n days
     * @param number number of days
     * @return trips in next n days
     */
    @RequestMapping(value = "/nextDays",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findTripsInNextDays(@RequestParam(value = "number", required = true) Integer number) {
        try {
            return tripFacade.findTripsInNextDays(number);
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);
        } 
    }
    
    /**
     * Find trips with free capacity
     * @return trips with free capacity
     */
    @RequestMapping(value = "/free",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findWithFreeCapacity() {
        try {
            return tripFacade.findWithFreeCapacity();
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);
        } 
    }

    /**
     * Find trips with given destination
     * @param destination of trip
     * @return trips with given destination
     */
    @RequestMapping(value = "/destination",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByDestination(@RequestParam(value = "destination", required = true) String destination) {
        try {
            return tripFacade.findByDestination(destination);
        } catch (NullPointerException | IllegalArgumentException ex) {
            throw new NotFoundException(ex);
        } 
    }
    
    
}