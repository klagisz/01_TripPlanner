package pl.soth.planer.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.soth.planer.repository.PlaceRepository;
import pl.soth.planer.repository.TripRepository;
import pl.soth.planer.model.Place;
import pl.soth.planer.model.Trip;
import pl.soth.planer.model.TypeOfPlace;
import pl.soth.planer.model.dto.AddPlaceToTripDto;
import pl.soth.planer.model.dto.ModifyPlaceDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private LoginService loginService;

    public Optional<Trip> addPlaceToTrip(AddPlaceToTripDto dto) {
        return addPlaceToTrip(dto.getName(), dto.getAdress(),
                dto.getNote(), dto.getTripId(), dto.getType());
    }

    public Optional<Trip> addPlaceToTrip(String name, String adress,
                                         String note, Long tripId,
                                         String placeType) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (optionalTrip.isPresent()) {
            Trip trip = optionalTrip.get();

            Place place = new Place();

            place.setName(name);
            place.setAdress(adress);
            place.setNote(note);
            place.setTrip(trip);
            place.setTypeOfPlace(TypeOfPlace.valueOf(placeType));
            place.setVisited(false);

            place = placeRepository.save(place);

            trip.getPlaceList().add(place);

            trip = tripRepository.save(trip);

            return Optional.of(trip);
        }
        return Optional.empty();
    }


    public List<Place> getPlaceByTrip(Long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (optionalTrip.isPresent()) {
            List<Place> placeList = optionalTrip.get().getPlaceList();
            return placeList;
        }
        return new ArrayList<>();
    }

    public Optional<Place> getPlaceById(Long placeId) {
        return placeRepository.findById(placeId);
    }

    public Optional<Place> modifyPlace(ModifyPlaceDto dto) {
        return modifyPlace(dto.getName(), dto.getAdress(), dto.getNote(), dto.getTypeOfPlace(),
                dto.getPlaceId());

    }

    public Optional<Place> modifyPlace(String name, String adress,
                                       String note, String placeType, Long placeId) {
        Optional<Place> placeOptional = placeRepository.findById(placeId);
        if (placeOptional.isPresent()) {
            Place place = placeOptional.get();

            place.setName(name);
            place.setAdress(adress);
            place.setTypeOfPlace(TypeOfPlace.valueOf(placeType));
            place.setNote(note);

            place = placeRepository.save(place);

            return Optional.of(place);
        }
        return Optional.empty();
    }

    public boolean deletePlaceById(Long placeId) {
        if (placeRepository.existsById(placeId)) {
            placeRepository.deleteById(placeId);
            return true;
        }
        return false;
    }

    public Optional<Place> setVisitedStatus(Place place) {
        return setVisitedStatus(place.getId());

    }

    public Optional<Place> setVisitedStatus(Long placeId) {
        Optional<Place> placeOptional = placeRepository.findById(placeId);
        if (placeOptional.isPresent()) {
            Place place = placeOptional.get();

            if (place.isVisited()) {
                place.setVisited(false);
            } else {
                place.setVisited(true);
            }
            place = placeRepository.save(place);

            return Optional.of(place);
        }
        return Optional.empty();
    }
}