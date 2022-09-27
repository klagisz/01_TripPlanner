package pl.soth.planer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.soth.planer.model.Place;
import pl.soth.planer.model.Trip;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findAllByTripIn(List<Trip> tripList);


}
