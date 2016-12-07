package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.ExcursionFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * Created on 07.12.2016.
 *
 * @author Martin Salata
 */
@Controller
@RequestMapping(value = "/excursions")
public class ExcursionController {
    // TODO: uncomment when DataConfig is provided
//    @Inject
//    private ExcursionFacade excursionFacade;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {

        //TODO: this is only until I resolve issues with @Inject and until some DataCOnfiguration is provided
        ExcursionDto excursionDto = new ExcursionDto();
        excursionDto.setName("first name");
        excursionDto.setDescription("first description");
        excursionDto.setDestination("first destination");
        excursionDto.setDate(Calendar.getInstance().getTime());
        excursionDto.setDuration(Calendar.getInstance().getTime());
        ExcursionDto excursionDto2 = new ExcursionDto();
        excursionDto2.setName("second name");
        excursionDto2.setDescription("second description");
        excursionDto2.setDestination("second destination");
        excursionDto2.setDate(Calendar.getInstance().getTime());
        excursionDto2.setDuration(Calendar.getInstance().getTime());


        List<ExcursionDto> excursions = new ArrayList<>(Arrays.asList(excursionDto, excursionDto2));
        //TODO: List<ExcursionDto> excursions = excursionFacade.findAll();
        model.addAttribute("excursions", excursions);

        return "excursion/list";
    }
}
