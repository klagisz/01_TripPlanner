package pl.soth.planer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.soth.planer.model.AppUser;
import pl.soth.planer.model.Trip;
import pl.soth.planer.model.dto.ModifyAppUserDto;
import pl.soth.planer.model.dto.ModifyPasswordDto;
import pl.soth.planer.service.*;
import pl.soth.planer.service.AppUserService;
import pl.soth.planer.service.LoginService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/appuser/")
public class AppUserController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/profile")
    public String profile(Model model) {

        Optional<AppUser> appUserOptional = loginService.getLoggedInUser();

        if (appUserOptional.isPresent()) {
            AppUser appuser = appUserOptional.get();
            List<Trip> tripList = appuser.getTripList();


            model.addAttribute("appuser", appuser);
            model.addAttribute("tripList", tripList);

            return "appuser/profile";
        }
        return "/login";
    }

    @GetMapping("/modify/{id}")
    public String modify(Model model, @PathVariable(name = "id") Long id) {
        Optional<AppUser> appUserOptional = appUserService.findById(id);

        if (!appUserOptional.isPresent()) {
            return "redirect:/appuser/profile";
        }

        ModifyAppUserDto dto = new ModifyAppUserDto();
        dto.setUserId(id);

        model.addAttribute("modify_user", dto);

        return "appuser/modify_form";
    }

    @PostMapping("/modify")
    public String modify(ModifyAppUserDto dto) {
        Optional<AppUser> optionalAppUser = appUserService.modifyUser(dto);
        if (optionalAppUser.isPresent()){
            return "redirect:/appuser/profile";
        }
        return "redirect:/appuser/profile";
    }

    @GetMapping("/password/{userId}")
    public String changePassword(Model model, @PathVariable(name = "userId") Long id) {
        Optional<AppUser> appUserOptional = appUserService.findById(id);

        if (!appUserOptional.isPresent()) {
            return "redirect:/login";
        }

        ModifyPasswordDto dto = new ModifyPasswordDto();
        dto.setUserId(id);

        model.addAttribute("change_password", dto);
        return "appuser/password_form";
    }

    @PostMapping("/password")
    public String changePassword(Model model, ModifyPasswordDto dto) {

        AppUser appUser = appUserService.findById(dto.getUserId()).get();

        if (!encoder.matches(dto.getPassword(), appUser.getPassword())) {
            model.addAttribute("change_password", dto);
            model.addAttribute("error_message", "Password doesn't match");
            return "appuser/password_form";
        }

        if (dto.getNewPassword().isEmpty() || dto.getNewPassword().length() < 5) {
            model.addAttribute("change_password", dto);
            model.addAttribute("error_message", "Password is to short");
            return "appuser/password_form";
        }

        if (!dto.getNewPassword().equals(dto.getNewPasswordConfirm())) {
            model.addAttribute("change_password", dto);
            model.addAttribute("error_message", "Password is not confirmed");
            return "appuser/password_form";
        }

        Optional<AppUser> optionalAppUser = appUserService.modifyPassword(dto);

        return "redirect:/appuser/profile";
    }
}
