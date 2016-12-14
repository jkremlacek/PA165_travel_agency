package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    //userFacade.update(ud); NE
        
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest req,
            HttpServletResponse res){   
        UserDto loggedUser = (UserDto) req.getSession().getAttribute("authUser");
        
        if (loggedUser.getIsAdmin()) {
            List<UserDto> users = userFacade.findAll();
            model.addAttribute("users", users);
            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("filter", "none");
            return "/user/list";
        } else {
            List<UserDto> users = Arrays.asList(userFacade.findById(loggedUser.getId()));            
            model.addAttribute("users", users);
            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("filter", "none");
            return "/user/list";
        }
        
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, HttpServletRequest req) {
        UserDto loggedUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!loggedUser.getIsAdmin() && !loggedUser.getId().equals(id)) {
                redirectAttributes.addFlashAttribute("error", "Only admin can see other user's detail");
                return DEFAULT_REDIRECT;
        }
       
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
    
    @RequestMapping(value = {"/changeRole/{id}"}, method = RequestMethod.GET)
    public String changeRole(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes,HttpServletRequest req,
            HttpServletResponse res) {
        UserDto loggedUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!loggedUser.getIsAdmin() || loggedUser.getId().equals(id)) {
                redirectAttributes.addFlashAttribute("error", "Only admin can change role and olny to others");
                return DEFAULT_REDIRECT;
        }
        UserDto updatingUser = userFacade.findById(id);
        if (userFacade.isUserAdmin(updatingUser)) {
             updatingUser.setIsAdmin(Boolean.FALSE);
        } else {
            updatingUser.setIsAdmin(Boolean.TRUE);
        }
        userFacade.update(updatingUser); 
        model.addAttribute("updatingUser", updatingUser);
         return DEFAULT_REDIRECT;
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model,RedirectAttributes redirectAttributes,HttpServletResponse res, HttpServletRequest req) {
        UserDto loggedUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!loggedUser.getId().equals(id)) {
                redirectAttributes.addFlashAttribute("error", "You can edit only yourself");
                return DEFAULT_REDIRECT;
        }
        UserDto updatingUser = userFacade.findById(id);
        model.addAttribute("updatingUser", updatingUser);
        return "/user/edit";

    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable Long id, @ModelAttribute("updatingUser") UserDto userDto
            , Model model, RedirectAttributes redirectAttributes,HttpServletRequest req) {

        if (userDto == null) {
            redirectAttributes.addFlashAttribute("error", "User " + id + " does not exist");
            return DEFAULT_REDIRECT;
        }
        UserDto loggedUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!loggedUser.getId().equals(id)) {
            redirectAttributes.addFlashAttribute("error", "You can update only yourself");
                return DEFAULT_REDIRECT;
        }
        try {
            UserDto user = userFacade.findById(id);
            user.setName(userDto.getName());
            user.setPhoneNumber(userDto.getPhoneNumber());
            userFacade.update(user);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return DEFAULT_REDIRECT;
        }

        redirectAttributes.addFlashAttribute("success", "User with " + id
                + " successfuly updated.");
        return "redirect:/user/detail/" + id;
    }

}