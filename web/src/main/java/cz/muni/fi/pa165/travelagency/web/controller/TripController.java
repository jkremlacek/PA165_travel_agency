/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.dto.ExcursionDto;
import cz.muni.fi.pa165.travelagency.facade.dto.TripDto;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Jakub Kremláček
 */

@Controller
@RequestMapping(value = "/trips")
public class TripController {
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        
        //TODO temporary
        TripDto tripDto = new TripDto();
        
        tripDto.setName("first trip");
        tripDto.setCapacity(10);
        tripDto.setDescription("first desc");
        tripDto.setDestination("first dest");
        tripDto.setPrice(BigDecimal.valueOf(1000));
        
        TripDto tripDto2 = new TripDto();
        
        tripDto2.setName("second trip");
        tripDto2.setCapacity(20);
        tripDto2.setDescription("second desc");
        tripDto2.setDestination("second dest");
        tripDto2.setPrice(BigDecimal.valueOf(1500));
        
        ExcursionDto excursionDto = new ExcursionDto();
        
        excursionDto.setName("first excursion");
        excursionDto.setDescription("first exc desc");
        excursionDto.setDestination("first exc dest");
        excursionDto.setPrice(BigDecimal.TEN);
        
        tripDto.addExcursion(excursionDto);
        excursionDto.setTrip(tripDto);

        ExcursionDto excursionDto2 = new ExcursionDto();
        
        excursionDto2.setName("second excursion");
        excursionDto2.setDescription("second exc desc");
        excursionDto2.setDestination("second exc dest");
        excursionDto2.setPrice(BigDecimal.TEN);
        
        tripDto.addExcursion(excursionDto);
        excursionDto.setTrip(tripDto);
        
        tripDto2.addExcursion(excursionDto2);
        excursionDto2.setTrip(tripDto2);
        
        List<TripDto> trips = new ArrayList<>(Arrays.asList(tripDto, tripDto2));
        //TODO: List<ExcursionDto> excursions = excursionFacade.findAll();
        model.addAttribute("trips", trips);

        return "trip/list";
    }
}
