package pl.soth.planer.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pl.soth.planer.service.RegisterService;
import pl.soth.planer.model.AppUser;
import pl.soth.planer.model.dto.RegisterDto;

import java.util.Optional;

@Lazy
@Component
public class DefaultUserCreator {

    @Autowired
    public DefaultUserCreator(RegisterService registerService) {
        Optional<AppUser> clientOptional = registerService.findByEmail("4dm1n@localhost");
        if (!clientOptional.isPresent()) {


            RegisterDto dto = new RegisterDto();

            dto.setName("Admin");
            dto.setSurname("Admin");
            dto.setPassword("admin");
            dto.setPasswordConfirm("admin");
            dto.setEmail("admin@email.com");

            registerService.registerAppUser(dto);
        }
    }
}
