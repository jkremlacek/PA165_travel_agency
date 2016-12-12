package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Katerina Caletkova
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    
    @Inject
    TripFacade tripFacade;

    @Inject
    ExcursionFacade excursionFacade;

    @Inject
    ReservationFacade reservationFacade;
    
    @Inject
    UserFacade userFacade;
    
    private String DEFAULT_REDIRECT = "redirect:/user/list";

    //userFacade.create(ucd); NE
    //userFacade.delete(ud); NE
    //userFacade.update(ud); NE
    //userFacade.userAuthenticate(uad); NE
    
    //userFacade.findAll(); ANO
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model){   
        List<UserDto> users = userFacade.findAll();
        model.addAttribute("users", users);
        model.addAttribute("filter", "none");
        return "/user/list";
    }
    
    //userFacade.findById(Long.MIN_VALUE); ANO
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        UserDto userDto = userFacade.findById(id);
        if (userDto == null) {
            redirectAttributes.addFlashAttribute("error", "User with id" + id + " doesn't exist");
            return DEFAULT_REDIRECT;
        }
        model.addAttribute("user", userDto);       
        List<ReservationDto> reservations = reservationFacade.findByUser(userDto);
        if(reservations == null){
            reservations = new ArrayList<>();
        }         
        model.addAttribute("reservations", reservations);       
        return "/user/detail";
        
    }
    //userFacade.findByMail(string); ANO
    //userFacade.findByBirthDate(date); ANO
    //userFacade.findByName(string); ANO
    //userFacade.findByPersonalNumber(Long.MIN_VALUE); ANO 
    //userFacade.findByPhoneNumber(Integer.BYTES); ANO 
    //userFacade.findByReservation(rd); ANO
    //userFacade.isUserAdmin(ud); ANO
    
}