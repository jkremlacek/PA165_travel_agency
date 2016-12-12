
package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@Controller
@RequestMapping(value = "/reservation")
public class ReservationController {
    
    @Inject
    private ReservationFacade reservationFacade;
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listAll(Model model) {
        List<ReservationDto> reservations = reservationFacade.findAll();
        model.addAttribute("reservations", reservations);
        //model.addAttribute("filter", "none");

        return "reservation/list";
    }

}
