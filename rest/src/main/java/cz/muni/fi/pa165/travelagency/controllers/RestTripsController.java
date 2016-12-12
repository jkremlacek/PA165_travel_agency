package cz.muni.fi.pa165.travelagency.controllers;

import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.rest.ApiUris;
import cz.muni.fi.pa165.travelagency.exceptions.AlreadyExistingException;
import cz.muni.fi.pa165.travelagency.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.travelagency.exceptions.NotFoundException;
import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.TripCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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
    /**
     * Get trips
     * @return list of trips
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findAll() {        
        return tripFacade.findAll();        
    }
    
    /**
     * Get trip with gived id
     * @param id id of trip
     * @return trip with gived id
     * @throws Exception if id is null
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TripDto findById(@PathVariable("id") Long id) throws Exception {
        TripDto tripDto = tripFacade.findById(id);
        if (tripDto == null) {
            throw new NotFoundException();
        }
        return tripDto;
    }
    
    /**
     * Delete trip with gived id
     * @param id id of trip
     * @throws Exception if there isn't trip with gived id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void delete(@PathVariable("id") Long id) throws Exception {   
        try {
            tripFacade.delete(tripFacade.findById(id));            
        }
        catch (Exception ex) {
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
        } catch (Exception ex) {
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
        } catch (Exception ex) {
            throw new InvalidParameterException(ex);
        }        
    }
    
    /**
     * Find trips with available capacity
     * @param capacity value of available capacity 
     * @return Trips with available capacity
     */
    @RequestMapping(value = "/capacity", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByAvailableCapacity(@RequestParam(value = "capacity", required = true) Integer capacity) {
        if (capacity == null) {
            return null;
        }
        return tripFacade.findByAvailableCapacity(capacity);
    }      
 
    /**
     * Find trips with date in choosen interval
     * @param from the earliest date of starting trip
     * @param to the latest date of starting trip
     * @return trips with date in choosen interval
     */
    @RequestMapping(value = "/date",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByDate(@RequestParam(value = "from", required = true) Date from,
                                            @RequestParam(value = "to", required = true) Date to) {
        if (from == null || to == null) {
            return null;
        }
        
        return tripFacade.findByDate(from,to);        
    }    
    
    /**
     * Find trips with choosen excursion
     * @param excursionId excursionId which is avalaible from returning trips
     * @return trips with choosen excursion
     */
    @RequestMapping(value = "/excursion",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByExcursion(@RequestParam(value = "excursionId", required = true) Long excursionId) {
        if (excursionId == null) {
            return null;
        }
        return tripFacade.findByExcursion(excursionFacade.findById(excursionId));
    }
      
    /**
     * Find trips with given name
     * @param name name of trip
     * @return trips with given name
     */ 
    @RequestMapping(value = "/name",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByName(@RequestParam(value = "name", required = true) String name) {
        if (name == null) {
            return null;
        }
        return tripFacade.findByName(name);
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
        if (min == null || max == null) {
            return null;
        }
        return tripFacade.findByPrice(min,max);
    }
     
    /**
     * Find trip's participants for given trip
     * @param tripId tripId for which we want to know its participants
     * @return trip's participants for given trip
     */
    @RequestMapping(value = "/trip",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDto> findTripParticipants(@RequestParam(value = "tripId", required = true) Long tripId) {
        
        return tripFacade.findTripParticipants(tripFacade.findById(tripId));
    }
   
    /**
     * Find trips in next n days
     * @param number number of days
     * @return trips in next n days
     */
    @RequestMapping(value = "/nextDays",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findTripsInNextDays(@RequestParam(value = "number", required = true) Integer number) {
        if (number == null) {
            return null;
        }
        return tripFacade.findTripsInNextDays(number);
    }
    
    /**
     * Find trips with free capacity
     * @return trips with free capacity
     */
    @RequestMapping(value = "/free",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findWithFreeCapacity() {
        
        return tripFacade.findWithFreeCapacity();
    }

    /**
     * Find trips with given destination
     * @param destination of trip
     * @return trips with given destination
     */
    @RequestMapping(value = "/destination",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByDestination(@RequestParam(value = "destination", required = true) String destination) {
        if (destination == null) {
            return null;
        }
        return tripFacade.findByDestination(destination);
    }
    
    
}