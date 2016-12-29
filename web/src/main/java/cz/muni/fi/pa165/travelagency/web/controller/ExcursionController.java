package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.TripFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionCreateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.RollbackException;
import javax.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionSystemException;

/**
 * Created on 07.12.2016.
 * SpringMVC Controller for excursions
 *
 * @author Martin Salata
 */
@Controller
@RequestMapping(value = "/excursion")
public class ExcursionController {

    @Inject
    private ExcursionFacade excursionFacade;

    @Inject
    private TripFacade tripFacade;

    final static Logger log = LoggerFactory.getLogger(ExcursionController.class);

    private String DEFAULT_REDIRECT = "redirect:/excursion/list";

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listAll(Model model) {
        log.info("request: GET /excursion/list");
        List<ExcursionDto> excursions = excursionFacade.findAll();
        model.addAttribute("excursions", excursions);
        model.addAttribute("filter", "none");

        return "excursion/list";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        log.info("request: GET /excursion/detai/{}", id);
        ExcursionDto excursionDto = excursionFacade.findById(id);

        if (excursionDto == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Excursion no. " + id + " doesn't exist");
            return DEFAULT_REDIRECT;
        }

        model.addAttribute("excursion", excursionDto);

        return "excursion/detail";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {

        log.info("request: POST excursion/delete/{}",id);
        
        ExcursionDto excursionDto = excursionFacade.findById(id);
        if (excursionDto == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Excursion no. " + id + " does not exist");
            return DEFAULT_REDIRECT;
        }

        try {
            excursionFacade.delete(excursionFacade.findById(id));
        } catch (Exception ex) {
            log.error("request: POST /delete/{}", id, ex);
            redirectAttributes.addFlashAttribute("alert_danger", "Excursion no. " + id + " could not be deleted");
            return DEFAULT_REDIRECT;
        }
        redirectAttributes.addFlashAttribute("alert_success", "Excursion " + excursionDto.getName() + " has been successfully deleted");

        return DEFAULT_REDIRECT;
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model, RedirectAttributes redAttr, HttpServletResponse res, HttpServletRequest req) {
        log.info("request: GET /edit/{}",id);
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        if (!authUser.getIsAdmin()) {
            redAttr.addFlashAttribute("alert_danger", "You cannot edit Excursions");
            return DEFAULT_REDIRECT;
        }

        ExcursionDto toUpdate = excursionFacade.findById(id);

        model.addAttribute("toUpdate", toUpdate);
        model.addAttribute("trips", tripFacade.findAll());


        return "excursion/edit";

    }


    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("toUpdate") ExcursionDto toUpdate,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        
        log.info("request: POST /update/{}",id);

        if (toUpdate == null) {
            redirectAttributes.addFlashAttribute("alert_danger", "Excursion no." + id + " does not exist");
            return "redirect:/excursion/create";
        }

        try {
            excursionFacade.update(toUpdate);
        } catch (TransactionSystemException ex) {
        log.error("request: POST /update/{}", id, ex);            
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("alert_danger", "Excursion could not be in past");
            return "redirect:/excursion/edit/{id}";
        } catch (Exception ex) {
            log.error("request: POST /update/{}", id, ex);
            ex.printStackTrace();
            redirectAttributes.addFlashAttribute("alert_danger", ex.getMessage());
            return "redirect:/excursion/edit/{id}";
        }


        redirectAttributes.addFlashAttribute("alert_success", "Excursion no. " + id
                + " successfuly updated.");
        return "redirect:/excursion/detail/" + id;
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newExcursion(Model model, 
                               RedirectAttributes redAttr,
                               HttpServletRequest req) {
        
        UserDto authUser = (UserDto) req.getSession().getAttribute("authUser");
        
        if (!authUser.getIsAdmin()) {
            redAttr.addFlashAttribute("alert_danger", "You don't have permission to create new excursion");
            return DEFAULT_REDIRECT;
        }
        
        log.info("request: GET /excursion/new/");

        model.addAttribute("newExcursion", new ExcursionCreateDto());
        model.addAttribute("trips", tripFacade.findAll());
        return "excursion/create";


    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("newExcursion") ExcursionCreateDto excursionCreateDto, BindingResult bindingResult,
                         Model model, RedirectAttributes redirectAttributes) {
        log.info("request: POST /excursion/create/");

        Long id;
        try {
            id = excursionFacade.create(excursionCreateDto);
        } catch (ValidationException | RollbackException ex) {
            log.error("request: POST /excursion/create/", ex);
            redirectAttributes.addFlashAttribute("alert_danger", "Excursion could not be in past");
            return "redirect:/excursion/new";
        } catch (Exception ex) {
            log.error("request: POST /excursion/create/", ex);
            redirectAttributes.addFlashAttribute("alert_danger", ex.getMessage());
            return "redirect:/excursion/new";
        }

        redirectAttributes.addFlashAttribute("alert_success", "Excursion " + excursionCreateDto.getName()
                 + " successfully created");

        return "redirect:/excursion/detail/" + id;
    }


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

}
