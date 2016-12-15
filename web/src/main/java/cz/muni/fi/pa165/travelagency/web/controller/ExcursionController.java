package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created on 07.12.2016.
 * SpringMVC Controller for excursions
 * @author Martin Salata
 */
@Controller
@RequestMapping(value = "/excursion")
public class ExcursionController {

    @Inject
    private ExcursionFacade excursionFacade;

    @Inject
    private TripFacade tripFacade;


    private String DEFAULT_REDIRECT = "redirect:/excursion/list";

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listAll(Model model) {
        List<ExcursionDto> excursions = excursionFacade.findAll();
        model.addAttribute("excursions", excursions);
        model.addAttribute("filter", "none");

        return "excursion/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        ExcursionDto excursionDto = excursionFacade.findById(id);

        if (excursionDto == null) {
            redirectAttributes.addFlashAttribute("error", "Excursion with id" + id + " doesn't exist");
            return DEFAULT_REDIRECT;
        }

        model.addAttribute("excursion", excursionDto);

        return "excursion/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        ExcursionDto excursionDto = excursionFacade.findById(id);
        if (excursionDto == null) {
            redirectAttributes.addFlashAttribute("error", "Excursion " + id + " does not exist");
            return DEFAULT_REDIRECT;
        }

        try {
            excursionFacade.delete(excursionFacade.findById(id));
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("error", "Excursion " + id + " could not be deleted");
            return DEFAULT_REDIRECT;
        }
        redirectAttributes.addFlashAttribute("success", "Excursion " + id + " has been successfully deleted");

        return DEFAULT_REDIRECT;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        ExcursionDto toUpdate = excursionFacade.findById(id);

        model.addAttribute("toUpdate", toUpdate);
        model.addAttribute("trips", tripFacade.findAll());


        return "excursion/edit";

    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable Long id, @ModelAttribute("toUpdate") ExcursionDto excursionDto, Model model, RedirectAttributes redirectAttributes) {

        if (excursionDto == null) {
            redirectAttributes.addFlashAttribute("error", "Excursion " + id + " does not exist");
            return "redirect:/excursion/create";
        }

        try {
            excursionFacade.update(excursionDto);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/excursion/list";
        }


        redirectAttributes.addFlashAttribute("success", "Excursion with " + id
                + " successfuly updated.");
        return "redirect:/excursion/detail/" + id;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newExcursion(Model model) {

        model.addAttribute("newExcursion", new ExcursionCreateDto());
        model.addAttribute("trips", tripFacade.findAll());
        return "excursion/create";


    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("newExcursion") ExcursionCreateDto excursionCreateDto, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes) {

        //TODO: check binding errors

        Long id;
        try {
            id = excursionFacade.create(excursionCreateDto);
        } catch (DataAccessException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/excursion/new";
        }

        redirectAttributes.addFlashAttribute("success", "Excursion " + excursionCreateDto.getName()
                + " (id=" + id + ")successfully created");

        return "redirect:/excursion/detail/" + id;
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
