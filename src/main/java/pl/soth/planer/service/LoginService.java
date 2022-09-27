package pl.soth.planer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.soth.planer.component.DefaultUserCreator;
import pl.soth.planer.model.AppUser;

import java.util.Optional;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private DefaultUserCreator defaultUserCreator;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> appUserOptional = registerService.findByEmail(email);

        if (appUserOptional.isPresent()) {
            AppUser appUser = appUserOptional.get();

            if (email.equals("admin@email.com")) {
                return User.withUsername(appUser.getEmail()).password(appUser.getPassword())
                        .roles("ADMIN", "USER").build();
            }

            return User.withUsername(appUser.getEmail()).password(appUser.getPassword())
                    .roles("USER").build();
        }
        throw new UsernameNotFoundException("User not found by name: " + email);
    }

    public Optional<AppUser> getLoggedInUser() {

        if (SecurityContextHolder.getContext().getAuthentication() == null ||
                !SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            return Optional.empty();
        }

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof  User){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return registerService.findByEmail(user.getUsername());
        }
        return Optional.empty();
    }


}
