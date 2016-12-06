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
import cz.muni.fi.pa165.travelagency.facade.dto.TripCreateDto;
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
     * @param destination destination of trip, not necessarily required
     * @return list of trips
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDto> getTrips(@RequestParam(value = "destination", required = false) String destination) {
        if (destination == null) {
            return tripFacade.findAll();
        }
        return tripFacade.findByDestination(destination);
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
    public final void deleteTrip(@PathVariable("id") Long id) throws Exception {   
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
    public final void createTrip(@RequestBody TripCreateDto tripCreateDto) throws Exception {

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
    public final void updateTrip(@RequestBody TripDto tripDto) throws Exception {
        try {
            tripFacade.update(tripDto);            
        } catch (Exception ex) {
            throw new InvalidParameterException(ex);
        }
    }
}
