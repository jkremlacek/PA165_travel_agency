package cz.muni.fi.pa165.travelagency.web.controller;

import cz.muni.fi.pa165.travelagency.facade.UserFacade;
import cz.muni.fi.pa165.travelagency.facade.dto.UserAuthenticateDto;
import cz.muni.fi.pa165.travelagency.facade.dto.UserDto;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * SpringMVC Controller for user authentication
 * @author Josef Pavelec, jospavelec@gmail.com
 */
@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Inject
    private UserFacade userFacade;
    
    final static Logger log = LoggerFactory.getLogger(AuthController.class);
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String authForm(
            Model model,
            HttpServletRequest req,
            HttpServletResponse res) {
        
        log.info("request: GET /auth/login");
        
        if (req.getSession().getAttribute("authUser") != null) {
            return "redirect:/";
        }
        return "auth/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String authenticate(
            @RequestParam String mail,
            @RequestParam String password,
            Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req,
            HttpServletResponse res) {
        
        log.info("requset: POST /auth/login");

        UserAuthenticateDto userAuthDto = new UserAuthenticateDto();
        userAuthDto.setMail(mail);
        userAuthDto.setPassword(password);
        
        UserDto userDto = userFacade.userAuthenticate(userAuthDto);
        
        if (userDto == null) {
            log.warn("request: POST /auth/login; wrong login information, entered mail={}", mail);
            redirectAttributes.addFlashAttribute(
                    "alert_danger", "Wrong mail or password of user");
            return "redirect:/auth/login";
        }
        userDto.setIsAdmin(userFacade.isUserAdmin(userDto));
        req.getSession().setAttribute("authUser", userDto);
        log.info("request: POST /auth/login; user with id {} has been logged in", userDto.getId());
        redirectAttributes.addFlashAttribute("alert_info",
                "User with name " + userDto.getName() +" has been logged in.");
        return "redirect:/";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model,
            RedirectAttributes redirectAttributes,
            HttpServletRequest req) {
        log.info("request: GET /logout");
        HttpSession session = req.getSession(true);
        session.removeAttribute("authUser");
        redirectAttributes.addFlashAttribute("alert_info", "You have been successfully logged out.");
        return "redirect:/auth/login";
    }

}
