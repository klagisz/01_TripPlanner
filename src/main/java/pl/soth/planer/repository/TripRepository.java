package pl.soth.planer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.soth.planer.model.AppUser;
import pl.soth.planer.model.Trip;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findAllByAppuser(AppUser appUser);
}
