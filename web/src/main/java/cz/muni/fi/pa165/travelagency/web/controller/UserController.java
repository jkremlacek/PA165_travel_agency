package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import javax.inject.Inject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Katerina Caletkova
 */
@Controller
@RequestMapping(value = {"/admin/users"})
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
    //userFacade.findAll(); ANO
    //userFacade.findByBirthDate(date); ANO
    //userFacade.findById(Long.MIN_VALUE); ANO
    //userFacade.findByMail(string); ANO
    //userFacade.findByName(string); ANO
    //userFacade.findByPersonalNumber(Long.MIN_VALUE); ANO 
    //userFacade.findByPhoneNumber(Integer.BYTES); ANO 
    //userFacade.findByReservation(rd); ANO
    //userFacade.isUserAdmin(ud); ANO
    
}