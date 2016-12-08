package cz.muni.fi.pa165.travelagency.controllers;

import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.rest.ApiUris;
import cz.muni.fi.pa165.travelagency.exceptions.AlreadyExistingException;
import cz.muni.fi.pa165.travelagency.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.travelagency.exceptions.NotFoundException;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
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
public class TripsController {
    
    @Inject
    private TripFacade tripFacade;
    
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
            throw new NullPointerException();
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
     * @throws Exception ig trip already exist
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void create(@RequestBody TripCreateDto tripCreateDto) throws Exception {

        try {
            tripFacade.create(tripCreateDto);
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
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByAvailableCapacity(@RequestParam(value = "capacity", required = true) Integer capacity) {
        if (capacity == null) {
            return null;
        }
        return tripFacade.findByAvailableCapacity(capacity);
    }      
 
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByDate(@RequestParam(value = "from", required = true) Date from,@RequestParam(value = "to", required = true) Date to) {
        if (from == null || to == null) {
            return null;
        }
        return tripFacade.findByDate(from,to);
    }    
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByExcursion(@RequestParam(value = "excursion", required = true) ExcursionDto excursion) {
        if (excursion == null) {
            return null;
        }
        return tripFacade.findByExcursion(excursion);
    }
      
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByName(@RequestParam(value = "name", required = true) String name) {
        if (name == null) {
            return null;
        }
        return tripFacade.findByName(name);
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByPrice(@RequestParam(value = "min", required = true) BigDecimal min, @RequestParam(value = "max", required = true) BigDecimal max) {
        if (min == null || max == null) {
            return null;
        }
        return tripFacade.findByPrice(min,max);
    }
     
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<UserDto> findTripParticipants(@RequestParam(value = "tripDto", required = true) TripDto tripDto) {
        if (tripDto == null) {
            return null;
        }
        return tripFacade.findTripParticipants(tripDto);
    }
   
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findTripsInNextDays(@RequestParam(value = "number", required = true) Integer number) {
        if (number == null) {
            return null;
        }
        return tripFacade.findTripsInNextDays(number);
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findWithFreeCapacity() {
        
        return tripFacade.findWithFreeCapacity();
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> findByDestination(@RequestParam(value = "destination", required = true) String destination) {
        if (destination == null) {
            return null;
        }
        return tripFacade.findByDestination(destination);
    }
    
    
}