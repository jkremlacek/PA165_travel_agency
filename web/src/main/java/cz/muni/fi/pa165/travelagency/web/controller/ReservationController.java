
package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    
    private static final String DEFAULT_REDIRECT = "redirect:/reservation/list";
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listAll(Model model) {
        List<ReservationDto> reservations = reservationFacade.findAll();
        model.addAttribute("reservations", reservations);
        //model.addAttribute("filter", "none");

        return "reservation/list";
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        ReservationDto reservationDto = reservationFacade.findById(id);

        if (reservationDto == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Reservation with id" + id + " doesn't exist");
            return DEFAULT_REDIRECT;
        }

        model.addAttribute("reservation", reservationDto);

        return "reservation/detail";
    }

}
