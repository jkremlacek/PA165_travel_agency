
package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import java.math.BigDecimal;
import java.util.*;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.muni.fi.pa165.travelagency.web.converter.ListWrapper;
import org.springframework.cglib.core.Converter;
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

    private static final String DEFAULT_REDIRECT = "redirect:/reservation/list";

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listAll(Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {

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
            return DEFAULT_REDIRECT;
        }

        model.addAttribute("reservationPrice", reservationPrice);
        model.addAttribute("reservations", reservations);

        return "reservation/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes,HttpServletRequest req) {
        ReservationDto reservationDto = reservationFacade.findById(id);
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        
        if (!authUser.getIsAdmin() && !authUser.getId().equals(reservationDto.getUser().getId())) {
                redirectAttributes.addFlashAttribute("alert_danger", "Only admin can see other user's reservations.");
                return DEFAULT_REDIRECT;
        }  
        if (reservationDto == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Reservation no. " + id + " doesn't exist");
            return DEFAULT_REDIRECT;
        }

        model.addAttribute("reservation", reservationDto);

        return "reservation/detail";
    }

    @RequestMapping(value = "/create/{id}", method = RequestMethod.GET)
    public String create(@PathVariable Long id, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        TripDto tripDto = tripFacade.findById(id);
        if (!tripFacade.hasTripAvailableCapacity(tripDto)) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Trip no. " + id + " doesn't have available capacity");
            return "redirect:/trip/list";
        }
        
        model.addAttribute("tripExcursions", excursionFacade.findByTrip(tripDto));
        model.addAttribute("checkedExcursions", new ListWrapper());
        model.addAttribute("trip", tripDto);
        
        return "reservation/create";

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

    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST)
    public String create(@PathVariable Long id,
                         @ModelAttribute("checkedExcursions") ListWrapper checkedExcursions,
                         @ModelAttribute("trip") TripDto trip,
                         Model model,
                         RedirectAttributes redirectAttributes, HttpServletRequest request
    ) {
        UserDto authUser = (UserDto) request.getSession().getAttribute("authUser");
        ReservationCreateDto newReservation = new ReservationCreateDto();
        newReservation.setTrip(tripFacade.findById(trip.getId()));

//        newReservation.setExcursionSet(getExcursionsFromList(checkedExcursions.getFunctionList()));

        newReservation.setUser(authUser);
        Long reservationId;
        try {
            reservationId = reservationFacade.create(newReservation);
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Cannot create reservation for trip " +
                    newReservation.getTrip().getName() + " due to " +ex.getMessage());
            return "redirect:/trip/list";
        }

        try {
            for (ExcursionDto excursion : getExcursionsFromList(checkedExcursions.getFunctionList())) {
                reservationFacade.addExcursion(reservationId, excursion);
            }
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Problem occurred during excursion additions.");
            return "redirect:/trip/list";
        }


        redirectAttributes.addFlashAttribute("alert_success", "Reservation no. "
                +  reservationId + " successfully created");

        return "redirect:/reservation/detail/" + reservationId;

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        /*ReservationDto reservationDto = reservationFacade.findById(id);
        if (reservationDto == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Reservation no. " + id + " does not exist");
            return DEFAULT_REDIRECT;
        }

        try {
            reservationFacade.delete(reservationDto);
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Reservation no. " + id + 
                    " could not be delete due to " + ex.getMessage());
            return DEFAULT_REDIRECT;
        }
        redirectAttributes.addFlashAttribute("alert_success", "Reservation for trip " + reservationDto.getTrip().getName() + " has been successfully deleted");

        return DEFAULT_REDIRECT;*/

        return "redirect:/reservation/delete/"+id;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id, Model model, HttpServletRequest req, RedirectAttributes redirectAttributes) {

        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");

        /*if (authUser.getIsAdmin()) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Administrator cannot delete reservations");
            return "redirect:/reservation/list";
        }*/

        ReservationDto reservationDto = reservationFacade.findById(id);

        if (authUser.getId() != reservationDto.getUser().getId()) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "You don't have permission to delete reservation of other users.");
            return "redirect:/reservation/list";
        }

        try {
            reservationFacade.delete(reservationDto);
            redirectAttributes.addFlashAttribute("alert_success", "Reservation for trip " +
                    reservationDto.getTrip().getName() + " was deleted.");
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Reservation for trip "+reservationDto.getTrip().getName() +
                            " couldn't be deleted due to " + ex.getMessage());
            return "redirect:/reservation/list";
        }

        return "redirect:/reservation/list";
    }

}
