package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ReservationFacade;
import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ReservationDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * SpringMVC Controller for users
 * @author Katerina Caletkova
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
    
    @Inject
    private ReservationFacade reservationFacade;
    
    @Inject
    private UserFacade userFacade;
    
    private final String DEFAULT_REDIRECT = "redirect:/user/list";
        
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest req, RedirectAttributes redAttr) {   
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        List<UserDto> users;
        if (authUser.getIsAdmin()) {
            users = userFacade.findAll(); 
        } else {
            users = Arrays.asList(userFacade.findById(authUser.getId()));            
        }  
        if (users == null) {
            redAttr.addFlashAttribute("alert_danger", "Users don't exist.");
            return DEFAULT_REDIRECT;
        }
        model.addAttribute("users", users);
        model.addAttribute("authUser", authUser);
        model.addAttribute("filter", "none");
        return "/user/list";
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redAttr, HttpServletRequest req) {
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin() && !authUser.getId().equals(id)) {
                redAttr.addFlashAttribute("alert_danger", "Only admin can see other user's detail.");
                return DEFAULT_REDIRECT;
        }       
        UserDto user = userFacade.findById(id);
        if (user == null) {
            redAttr.addFlashAttribute("alert_danger", "User doesn't exist.");
            return DEFAULT_REDIRECT;
        }
        model.addAttribute("user", user);       
        List<ReservationDto> reservations = reservationFacade.findByUser(user);
        if(reservations == null){
            reservations = new ArrayList<>();
        }         
        model.addAttribute("reservations", reservations);       
        return "/user/detail";        
    }
    
    @RequestMapping(value = {"/changeRole/{id}"}, method = RequestMethod.GET)
    public String changeRole(@PathVariable Long id, Model model, RedirectAttributes redAttr,HttpServletRequest req,
            HttpServletResponse res) {
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
                redAttr.addFlashAttribute("alert_danger", "Only admin can change role.");
                return DEFAULT_REDIRECT;
        }
        if (authUser.getId().equals(id)) {
                redAttr.addFlashAttribute("alert_danger", "You can't change role to yourself.");
                return DEFAULT_REDIRECT;
        }
        UserDto user = userFacade.findById(id);
        if (user == null) {
            redAttr.addFlashAttribute("alert_danger", "User doesn't exist.");
            return DEFAULT_REDIRECT;
        }
        if (user.getIsAdmin()) {
             user.setIsAdmin(Boolean.FALSE);
        } else {
            user.setIsAdmin(Boolean.TRUE);
        }
        userFacade.update(user); 
        model.addAttribute("user", user);
        redAttr.addFlashAttribute("alert_info", "Role of " + user.getName() + " was successfuly changes.");
        return DEFAULT_REDIRECT;
    }
    
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model,RedirectAttributes redAttr,HttpServletResponse res, HttpServletRequest req) {
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getId().equals(id)) {
                redAttr.addFlashAttribute("alert_danger", "You can edit only yourself.");
                return DEFAULT_REDIRECT;
        }
        UserDto updatingUser = userFacade.findById(id);
        if (updatingUser == null) {
            redAttr.addFlashAttribute("alert_danger", "User doesn't exist");
            return DEFAULT_REDIRECT;
        } 
        model.addAttribute("updatingUser", updatingUser);
        return "/user/edit";

    }
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable Long id, @ModelAttribute("updatingUser") UserDto updatingUser
            , Model model, RedirectAttributes redAttr, HttpServletRequest req) {

        if (updatingUser == null) {
            redAttr.addFlashAttribute("alert_danger", "User does not exist.");
            return DEFAULT_REDIRECT;
        }
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getId().equals(id)) {
            redAttr.addFlashAttribute("alert_danger", "You can update only yourself.");
                return DEFAULT_REDIRECT;
        }        
        UserDto user = userFacade.findById(id);
        if (user == null) {
            redAttr.addFlashAttribute("alert_danger", "User does not exist.");
            return DEFAULT_REDIRECT;
        }
        if (updatingUser.getName() == null || updatingUser.getName().length() < 1) {
            redAttr.addFlashAttribute(
                    "alert_danger", "Name can't be empty.");
            return "redirect:/user/edit/"+ id;
        }
        /*try {updatingUser.getName();
        } catch (Exception ex) {
            redAttr.addFlashAttribute(
                    "alert_danger", "Wrong format of name " + ex);
            return "redirect:/user/edit/"+ id;
        }*/
        user.setName(updatingUser.getName());        
        user.setPhoneNumber(updatingUser.getPhoneNumber());
        user.setPersonalNumber(updatingUser.getPersonalNumber());
        /*try {updatingUser.getBirthDate();
        } catch (Exception ex) {
            redAttr.addFlashAttribute(
                    "alert_danger", "Wrong format of date " + ex);
            return "redirect:/user/edit/"+ id;
        }*/
        user.setBirthDate(updatingUser.getBirthDate());
        userFacade.update(user);
        redAttr.addFlashAttribute("alert_info", "User " + user.getName() + " was successfuly updated.");
        return "redirect:/user/detail/"+ id;
    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }
}