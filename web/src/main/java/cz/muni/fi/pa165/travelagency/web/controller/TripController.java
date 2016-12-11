/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
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
        //TODO: List<ExcursionDto> excursions = excursionFacade.findAll();
        model.addAttribute("trips", trips);
        model.addAttribute("filter", "none");

        return "trip/list";
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        TripDto tripDto = tripFacade.findById(id);

        if (tripDto == null) {
            redirectAttributes.addFlashAttribute("error", "Trip with id" + id + " doesn't exist");
            return DEFAULT_REDIRECT;
        }

        model.addAttribute("trip", tripDto);

        return "trip/detail";
    }
}
