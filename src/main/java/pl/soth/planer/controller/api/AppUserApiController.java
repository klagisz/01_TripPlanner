package pl.soth.planer.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.soth.planer.model.AppUser;
import pl.soth.planer.model.dto.AppUserDto;
import pl.soth.planer.model.dto.RegisterDto;
import pl.soth.planer.service.AppUserService;
import pl.soth.planer.service.RegisterService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin //ogólnie niebezpieczne
@RequestMapping("/api/employees")
public class AppUserApiController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RegisterService registerService;

    @GetMapping("/")
    public ResponseEntity<List<AppUserDto>> getAppUser() {
        return ResponseEntity.ok(appUserService.getAll()
                .stream()
                .map(appUser -> new AppUserDto(
                        appUser.getId(),
                        appUser.getName(),
                        appUser.getSurname(),
                        appUser.getEmail()))
                .collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<Optional<AppUser>> addAppUser(@RequestBody RegisterDto dto) {

        return ResponseEntity.ok(registerService.registerAppUser(dto));
}

}
