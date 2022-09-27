package pl.soth.planer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.soth.planer.model.AppUser;
import pl.soth.planer.model.dto.RegisterDto;
import pl.soth.planer.service.RegisterService;

import java.util.Optional;

@Controller("/")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("new_user", new RegisterDto());
        return "appuser/register_form";
    }

    @PostMapping("/register")
    public String registerUserPost(Model model, RegisterDto dto) {

        if (dto.getName().isEmpty() || dto.getSurname().isEmpty()) {
            model.addAttribute("new_user", dto);
            model.addAttribute("error_message", "Name or surname are empty");
            return "appuser/register_form";
        }

        if (dto.getEmail().isEmpty()) {
            model.addAttribute("new_user", dto);
            model.addAttribute("error_message", "Email is empty");
            return "appuser/register_form";
        }

        if (dto.getPassword().isEmpty() || dto.getPassword().length() < 5) {
            model.addAttribute("new_user", dto);
            model.addAttribute("error_message", "Password is too weak");
            return "appuser/register_form";
        }

        if (!dto.getPassword().equals(dto.getPasswordConfirm())) {
            model.addAttribute("new_user", dto);
            model.addAttribute("error_message", "Password and confirmation are diffirent");
            return "appuser/register_form";
        }

        Optional<AppUser> appUserOptional = registerService.registerAppUser(dto);

        if (!appUserOptional.isPresent()) {
            model.addAttribute("new_user", dto);
            model.addAttribute("error_message", "This email already exists");
            return "appuser/register_form";
        }

        return "redirect:/login";
    }

}
