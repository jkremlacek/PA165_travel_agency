package cz.muni.fi.pa165.travelagency.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 06.12.2016.
 *
 * @author Martin Salata
 */
@Controller
@RequestMapping(value = {"/", ""})
public class DefaultController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String defaultView() {
        // TODO: "/" should be redirected to some default list page
        return "redirect:/login";
    }

}
