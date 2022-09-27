package pl.soth.planer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.soth.planer.repository.AppUserRepository;
import pl.soth.planer.model.AppUser;
import pl.soth.planer.model.dto.ModifyAppUserDto;
import pl.soth.planer.model.dto.ModifyPasswordDto;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    public Optional<AppUser> findById(Long id) {
        return appUserRepository.findById(id);
    }

    public Optional<AppUser> modifyUser(ModifyAppUserDto dto) {
        Optional<AppUser> appUserOptional = appUserRepository.findById(dto.getUserId());

        if(appUserOptional.isPresent()){

            AppUser appUser = appUserOptional.get();

            if(!dto.getName().isEmpty()){
                appUser.setName(dto.getName());
            }

            if(!dto.getSurname().isEmpty()){
                appUser.setSurname(dto.getSurname());
            }

            if(!dto.getEmail().isEmpty()){
                appUser.setEmail(dto.getEmail());
            }

            appUser = appUserRepository.save(appUser);

            return Optional.of(appUser);
        }
        return Optional.empty();
    }

    public Optional<AppUser> modifyPassword(ModifyPasswordDto dto) {

        if(!dto.getNewPassword().isEmpty() || dto.getNewPassword().equals(dto.getNewPasswordConfirm())){
            AppUser appUser = appUserService.findById(dto.getUserId()).get();

            if(encoder.matches(dto.getPassword(), appUser.getPassword())) {
                appUser.setPassword(encoder.encode(dto.getNewPassword()));
            }

            appUser = appUserRepository.save(appUser);

            return Optional.of(appUser);
        }
        return Optional.empty();
    }

    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }
}
