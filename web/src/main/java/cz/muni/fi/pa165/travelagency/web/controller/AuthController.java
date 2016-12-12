package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.UserAuthenticateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Inject
    UserFacade userFacade;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String authForm(
            Model model,
            HttpServletRequest req,
            HttpServletResponse res) {
        HttpSession session = req.getSession(true);
        if (session.getAttribute("authUser") != null) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authenticate(
            @RequestParam Long id,
            @RequestParam String password,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req,
            HttpServletResponse res) {
        if (id==null) {
            redirectAttributes.addFlashAttribute(
                    "alert_info", "Empty id");
            return "redirect:/auth/login";
        }
        UserAuthenticateDto userAuthDto = new UserAuthenticateDto();
        userAuthDto.setId(id);
        userAuthDto.setPassword(password);
        
        UserDto userDto = userFacade.userAuthenticate(userAuthDto);
        
        if (userDto == null) {
            redirectAttributes.addFlashAttribute(
                    "alert_info", "Wrong id or password of user");
            return "redirect:/auth/login";
        }
        userDto.setIsAdmin(userFacade.isUserAdmin(userDto));
        req.getSession().setAttribute("authUser", userDto);
        redirectAttributes.addFlashAttribute("alert_info",
                "User with name " + userDto.getName() +" has been logged in.");
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        session.removeAttribute("authUser");
        redirectAttributes.addFlashAttribute("alert_info", "You have been successfully logged out.");
        return "/auth/login";
    }

}
