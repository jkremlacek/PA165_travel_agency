
package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import java.util.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import cz.muni.fi.pa165.travelagency.web.converter.ListWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * SpringMVC Controller for reservations
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@Controller
@RequestMapping(value = "/reservation")
public class ReservationController {

    @Inject
    private ReservationFacade reservationFacade;

    @Inject
    private TripFacade tripFacade;

    @Inject
    private ExcursionFacade excursionFacade;
    
    final static Logger log = LoggerFactory.getLogger(ReservationController.class);

    private static final String REDIRECT_RESERVATION_LIST = "redirect:/reservation/list";

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listAll(
            Model model, 
            HttpServletRequest req, 
            RedirectAttributes redirectAttributes) {

        log.debug("reservation/list");
        
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");

        List<ReservationDto> reservations;
        Map reservationPrice = new HashMap<>();

        try {
            if (authUser.getIsAdmin()) {
                reservations = reservationFacade.findAll();
            } else {
                reservations = reservationFacade.findByUser(authUser);
            }

            for (ReservationDto res : reservations) {
                reservationPrice.put(res.getId(), reservationFacade.getTotalPrice(res.getId()));
            }

        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Reservation list isn't accessible, due to " + ex.getMessage());
            return "";
        }

        model.addAttribute("reservationPrice", reservationPrice);
        model.addAttribute("reservations", reservations);

        return "reservation/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(
            @PathVariable Long id,
            Model model, 
            RedirectAttributes redirectAttributes,
            HttpServletRequest req) {
        
        log.debug("reservation/detail/"+id);
        
        ReservationDto reservationDto;
        UserDto authUser;
        
        try {        
            reservationDto = reservationFacade.findById(id);
            authUser = (UserDto) req.getSession().getAttribute("authUser");

            if (reservationDto == null) {
                redirectAttributes.addFlashAttribute("alert_danger", "Reservation no. " + id + " doesn't exist");
                return REDIRECT_RESERVATION_LIST;
            }

            if (!authUser.getIsAdmin() && !authUser.getId().equals(reservationDto.getUser().getId())) {
                    redirectAttributes.addFlashAttribute("alert_danger", "You don't have permission to show other people's reservation");
                    return REDIRECT_RESERVATION_LIST;
            } 
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Reservation no. "+id+" isn't accessible, due to " + ex.getMessage());
            return REDIRECT_RESERVATION_LIST;
        }
        
        model.addAttribute("reservation", reservationDto);
        model.addAttribute("reservationPrice", reservationFacade.getTotalPrice(reservationDto.getId()));
        
        return "reservation/detail";
    }

    @RequestMapping(value = "/create/{id}", method = RequestMethod.GET)
    public String create(
            @PathVariable Long id, 
            Model model, 
            HttpServletRequest request, 
            RedirectAttributes redirectAttributes) {
        
        log.debug("reservation/create/"+id);
        
                
        TripDto tripDto;

        try {
            tripDto = tripFacade.findById(id);
            
        if (tripDto==null)  {
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Trip no. " + id + " doesn't exist");
            return "redirect:/trip/list";
        }
        
        if (!tripFacade.hasTripAvailableCapacity(tripDto)) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Trip no. " + id + " doesn't have available capacity");
            return "redirect:/trip/list";
            }

        for (ExcursionDto exc : excursionFacade.findByTrip(tripDto)) {
            tripDto.addExcursion(exc);
        }
        
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Reservation for trip no. " + id + 
                            " is not possible create now, due to "+ex.getMessage());
            return "redirect:/trip/list";
        }
        model.addAttribute("chosenExcursions", new ListWrapper());
        model.addAttribute("trip", tripDto);
        
        return "reservation/create";

    }

    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    public String create(
            @PathVariable Long id,
            @ModelAttribute("chosenExcursions") ListWrapper chosenExcursions,
            @ModelAttribute("trip") TripDto trip,
            Model model,
            RedirectAttributes redirectAttributes, HttpServletRequest request) {
        
        UserDto authUser = (UserDto) request.getSession().getAttribute("authUser");
        ReservationCreateDto newReservation = new ReservationCreateDto();
        Long reservationId;
        try {
            newReservation.setTrip(tripFacade.findById(trip.getId()));
            newReservation.setUser(authUser);
            reservationId = reservationFacade.create(newReservation);
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Cannot create reservation for trip no. " +
                    newReservation.getTrip().getName() + " due to " +ex.getMessage());
            return "redirect:/trip/list";
        }

        try {
            for (ExcursionDto excursion : getExcursionsFromList(chosenExcursions.getFunctionList())) {
                reservationFacade.addExcursion(reservationId, excursion);
            }
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Problem occurred during creating reservation for trip no."+trip.getId());
            return "redirect:/trip/list";
        }


        redirectAttributes.addFlashAttribute("alert_success", "Reservation no. "
                +  reservationId + " successfully created");

        return "redirect:/reservation/detail/" + reservationId;

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(
            @PathVariable Long id, 
            Model model, 
            RedirectAttributes redirectAttributes) {

        return "redirect:/reservation/delete/"+id;
    }
    
    private Set<ExcursionDto> getExcursionsFromList(List<String> excursions) {
        Set<ExcursionDto> dtos = new HashSet<>();
        if (excursions != null) {
            for (String excursionId:excursions) {
                dtos.add(excursionFacade.findById(Long.parseLong(excursionId)));
            }
        }
        return dtos;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(
            @PathVariable Long id, 
            Model model, 
            HttpServletRequest req, 
            RedirectAttributes redirectAttributes) {

        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");

        ReservationDto reservationDto;
        
        try {
            reservationDto = reservationFacade.findById(id);
            
            if (reservationDto == null) {
                redirectAttributes.addFlashAttribute(
                        "alert_danger", "Reservation with no. "+id+" doesn't exist");
                return "redirect:/reservation/list";
            }

            if (!authUser.getIsAdmin() && !authUser.getId().equals(reservationDto.getUser().getId())) {
                redirectAttributes.addFlashAttribute(
                        "alert_danger", "You don't have permission to delete reservation of other users.");
                return "redirect:/reservation/list";
            }
            reservationFacade.delete(reservationDto);
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Reservation no. "+ id +
                            " couldn't be deleted due to " + ex.getMessage());
            return REDIRECT_RESERVATION_LIST;
            
        }
        redirectAttributes.addFlashAttribute(
                    "alert_success", "Reservation no. "+ id +
                            " was deleted.");
        return REDIRECT_RESERVATION_LIST;
    }

}
