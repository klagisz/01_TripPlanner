package pl.soth.planer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.soth.planer.model.AppUser;

import java.util.Optional;

@Repository
public interface RegisterRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
}
