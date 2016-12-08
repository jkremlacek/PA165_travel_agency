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

/**
 *
 * @author Katerina Caletkova
 */
@Controller
@RequestMapping(value = {"/admin_user"})
public class UserController {
    
    @Inject
    TripFacade tripFacade;

    @Inject
    ExcursionFacade excursionFacade;

    @Inject
    ReservationFacade reservationFacade;
    
    @Inject
    UserFacade userFacade;
    //userFacade.create(ucd); NE
    //userFacade.delete(ud); NE
    //userFacade.update(ud); NE
    //userFacade.userAuthenticate(uad); NE
    
    //userFacade.findAll(); ANO
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listUsers(Model model){        
        model.addAttribute("users", userFacade.findAll());
        return "/admin_user/list";
    }
 
     //userFacade.findByBirthDate(date); ANO
    
    //userFacade.findById(Long.MIN_VALUE); ANO
    @RequestMapping(value = "/index/{id}", method = RequestMethod.GET)
    public String userDetail(@PathVariable("id") long id, Model model){
        
       UserDto user = userFacade.findById(id);
        if(user == null){
            return "/admin_user/list";
        }
        
        List<ReservationDto> reservations = reservationFacade.findByUser(user);
        if(reservations == null){
            reservations = new ArrayList<>();
        }
        
        model.addAttribute("user", user);
        model.addAttribute("reservations", reservations);
        return "/admin_user/index";
    }
    //userFacade.findByMail(string); ANO
    //userFacade.findByName(string); ANO
    //userFacade.findByPersonalNumber(Long.MIN_VALUE); ANO 
    //userFacade.findByPhoneNumber(Integer.BYTES); ANO 
    //userFacade.findByReservation(rd); ANO
    //userFacade.isUserAdmin(ud); ANO
    
}