package pl.soth.planer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.soth.planer.repository.RegisterRepository;
import pl.soth.planer.model.AppUser;
import pl.soth.planer.model.dto.RegisterDto;

import java.util.Optional;

@Service
public class RegisterService {

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Optional<AppUser> registerAppUser(RegisterDto dto) {

        if(dto.getPassword().equals(dto.getPasswordConfirm()) &&
        !registerRepository.findByEmail(dto.getEmail()).isPresent()){

            AppUser appUser = new AppUser();

            appUser.setName(dto.getName());
            appUser.setSurname(dto.getSurname());
            appUser.setPassword(encoder.encode(dto.getPassword()));
            appUser.setEmail(dto.getEmail());

            appUser = registerRepository.save(appUser);

            return Optional.of(appUser);
        }
        return Optional.empty();
    }

    public Optional<AppUser> findByEmail(String email) {
        return registerRepository.findByEmail(email);
    }
}
