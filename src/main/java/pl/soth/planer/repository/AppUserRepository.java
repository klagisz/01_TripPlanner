package pl.soth.planer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.soth.planer.model.AppUser;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
//    List<AppUser> findAllByNameContainingOrEmailContainingOrSurnameContaining(String name, String email, String surname);

    Optional<AppUser> findByEmail(String email);
}
