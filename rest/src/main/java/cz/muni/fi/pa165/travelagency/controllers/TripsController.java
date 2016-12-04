package cz.muni.fi.pa165.travelagency.controllers;

import cz.muni.fi.pa165.travelagency.rest.ApiUris;
import cz.muni.fi.pa165.travelagency.dto.TripCreateDTO;
import cz.muni.fi.pa165.travelagency.dto.TripDTO;
import cz.muni.fi.pa165.travelagency.dto.TripUpdateDTO;
import cz.muni.fi.pa165.travelagency.exceptions.AlreadyExistingException;
import cz.muni.fi.pa165.travelagency.exceptions.InvalidParameterException;
import cz.muni.fi.pa165.travelagency.exceptions.NotFoundException;
import javax.inject.Inject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
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
     * @param userId id of user, not necessarily required
     * @return list of trios
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final List<TripDTO> getTrips(@RequestParam(value = "userId", required = false) Long userId) {
        if (userId == null) {
            return tripFacade.getAllTrips();
        }
        return tripFacade.getTripsByUser(userId);                
    }
    
    /**
     * Get trip with gived id
     * @param id id of trip
     * @return trip with gived id
     * @throws Exception if id is null
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public final TripDTO findById(@PathVariable("id") Long id) throws Exception {
        TripDTO tripDTO = tripFacade.getTripById(id);
        if (tripDTO == null) {
            throw new NullPointerException();
        }
        return tripDTO;
    }
    
    /**
     * Delete trip with gived id
     * @param id id of trip
     * @throws Exception if there isn't trip with gived id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public final void deleteTrip(@PathVariable("id") Long id) throws Exception {   
        try {
            tripFacade.deleteTrip(id);      
        }
        catch (Exception ex) {
            throw new NotFoundException(ex);
        }
    }
    
    /**
     * Create a trip
     * @param tripCreateDTO trip which should be created
     * @throws Exception ig trip already exist
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void createTrip(@RequestBody TripCreateDTO tripCreateDTO) throws Exception {

        try {
            tripFacade.createTrip(tripCreateDTO);
        } catch (Exception ex) {
            throw new AlreadyExistingException(ex);
        }
    }
    
    /**
     * Update trip
     * @param tripUpdateDTO which will be update
     * @throws Exception if tripUpdateDTO contains invalid parameter
     */
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final void updateTrip(@RequestBody TripUpdateDTO tripUpdateDTO) throws Exception {
        try {
            tripFacade.updateTrip(tripUpdateDTO);            
        } catch (Exception ex) {
            throw new InvalidParameterException(ex);
        }
    }
}
