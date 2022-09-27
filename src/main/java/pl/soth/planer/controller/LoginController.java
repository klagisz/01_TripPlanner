package pl.soth.planer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.soth.planer.model.AppUser;
import pl.soth.planer.service.LoginService;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/")
    public String getIndex(Model model) {

        Optional<AppUser> appUserOptional = loginService.getLoggedInUser();

        if (appUserOptional.isPresent()) {
            AppUser appUser = appUserOptional.get();
            model.addAttribute("logged", appUser.getEmail());
            model.addAttribute("loggedId", appUser.getId());

        }
        return "index";
    }

    //required = false - może tego nie być
    @GetMapping("/login")
    public String loginView(@RequestParam(name = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error_message", "Inncorect username and/or password.");
        }
        return "login/login_form";
    }
}
