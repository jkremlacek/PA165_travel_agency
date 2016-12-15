/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.TripCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * SpringMVC Controller for trips
 * @author Jakub Kremláček
 */

@Controller
@RequestMapping(value = "/trip")
public class TripController {
    
    private String DEFAULT_REDIRECT = "redirect:/trip/list";
    @Inject
    private TripFacade tripFacade;
    
    
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {        
        List<TripDto> trips = tripFacade.findAll();
        model.addAttribute("trips", trips);
        model.addAttribute("filter", "none");
        return "trip/list";
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        TripDto tripDto = tripFacade.findById(id);

        if (tripDto == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Trip with id" + id + " doesn't exist");
            return DEFAULT_REDIRECT;
        }

        model.addAttribute("trip", tripDto);
        model.addAttribute("availableCapacity", tripDto.getCapacity() - tripDto.getReservations().size());

        return "trip/detail";
    }
    
     @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, HttpServletResponse res, HttpServletRequest req) {

        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
                redirectAttributes.addFlashAttribute("alert_danger", "Only admin can create new trips.");
                return DEFAULT_REDIRECT;
        }
        
        TripDto tripDto = tripFacade.findById(id);
        if (tripDto == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Trip " + id + " does not exist");
            return DEFAULT_REDIRECT;
        }

        try {
            tripFacade.delete(tripFacade.findById(id));
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", "Trip " + id + " could not be deleted");
            return DEFAULT_REDIRECT;
        }
        redirectAttributes.addFlashAttribute("alert_success", "Trip " + id + " has been successfully deleted");

        return DEFAULT_REDIRECT;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes, HttpServletResponse res, HttpServletRequest req) {
        
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
                redirectAttributes.addFlashAttribute("alert_danger", "Only admin can create new trips.");
                return DEFAULT_REDIRECT;
        }
        
        TripDto toUpdate = tripFacade.findById(id);

        model.addAttribute("toUpdate", toUpdate);
        model.addAttribute("trips", tripFacade.findAll());


        return "trip/edit";

    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable Long id, @ModelAttribute("toUpdate") TripDto tripDto, Model model, RedirectAttributes redirectAttributes, HttpServletResponse res, HttpServletRequest req) {

        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
                redirectAttributes.addFlashAttribute("alert_danger", "Only admin can create new trips.");
                return DEFAULT_REDIRECT;
        }
        
        TripDto dbTripDto = tripFacade.findById(id);
        
        if (dbTripDto == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Trip " + id + " does not exist");
            return "redirect:/trip/create";
        }

        try {
            dbTripDto.setName(tripDto.getName());
            dbTripDto.setDestination(tripDto.getDestination());
            dbTripDto.setDescription(tripDto.getDescription());
            dbTripDto.setCapacity(tripDto.getCapacity());
            dbTripDto.setDateFrom(tripDto.getDateFrom());
            dbTripDto.setDateTo(tripDto.getDateTo());
            dbTripDto.setPrice(tripDto.getPrice());
            
            tripFacade.update(dbTripDto);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("alert_danger", ex.getMessage());
            return "redirect:/trip/edit/" + id;
        }

        redirectAttributes.addFlashAttribute("alert_success", "Trip with id " + id
                + " successfuly updated.");
        return "redirect:/trip/detail/" + id;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newTrip(Model model , RedirectAttributes redirectAttributes, HttpServletResponse res, HttpServletRequest req) {

        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
                redirectAttributes.addFlashAttribute("alert_danger", "Only admin can create new trips.");
                return DEFAULT_REDIRECT;
        }
        
        model.addAttribute("newTrip", new TripCreateDto());
        return "trip/create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("newTrip") TripCreateDto tripCreateDto, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes, HttpServletResponse res, HttpServletRequest req) {

        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
                redirectAttributes.addFlashAttribute("alert_danger", "Only admin can create new trips.");
                return DEFAULT_REDIRECT;
        }

        Long id;
        try {
            id = tripFacade.create(tripCreateDto);
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("alert_danger", ex.getMessage());
            return "redirect:/trip/new";
        }

        redirectAttributes.addFlashAttribute("alert_success", "Trip " + tripCreateDto.getName()
                + " successfully created");

        return "redirect:/trip/detail/" + id;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
//        SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
//        stf.setLenient(true);
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(stf, true));

    }
}
