package pl.soth.planer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.soth.planer.model.TripState;
import pl.soth.planer.repository.AppUserRepository;
import pl.soth.planer.repository.TripRepository;
import pl.soth.planer.model.AppUser;
import pl.soth.planer.model.Trip;
import pl.soth.planer.model.dto.AddTripDto;
import pl.soth.planer.model.dto.ModifyTripDto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    public Optional<Trip> addTrip(AddTripDto dto) {

        Optional<AppUser> clientOptional = appUserRepository.findById(dto.getAppuserId());
        if (clientOptional.isPresent()) {
            AppUser appUser = clientOptional.get();

            Trip trip = new Trip();

//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            trip.setName(dto.getName());
            trip.setBeginDate(LocalDate.parse(dto.getBeginDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            trip.setEndDate(LocalDate.parse(dto.getEndDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            trip.setComments(dto.getComments());
            trip.setTripState(TripState.PLANED);

            trip.setAppuser(appUser);

            trip = tripRepository.save(trip);

            return Optional.of(trip);
        }

        return Optional.empty();
    }

    public List<Trip> getAll() {
        return tripRepository.findAll();
    }

    public Optional<Trip> findTripById(Long tripId) {
        return tripRepository.findById(tripId);
    }

    public Optional<Trip> modifyTrip(ModifyTripDto dto) {
        Optional<Trip> tripOptional = tripRepository.findById(dto.getTripId());

        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            trip.setName(dto.getName());
            trip.setBeginDate(LocalDate.parse(dto.getBeginDate().toString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            trip.setEndDate(LocalDate.parse(dto.getEndDate().toString(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")));

//            trip.setBeginDate(LocalDate.parse(dto.getBeginDate(), formatter));
//            trip.setEndDate(LocalDate.parse(dto.getEndDate(), formatter));
//
            trip.setComments(dto.getComments());

            trip = tripRepository.save(trip);

            return Optional.of(trip);
        }
        return Optional.empty();
    }

    public boolean deleteTripById(Long tripId) {
        if (tripRepository.existsById(tripId)) {
            tripRepository.deleteById(tripId);
            return true;
        }
        return false;
    }

    public Optional<Trip> archveTripById(Long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (optionalTrip.isPresent()) {
            Trip trip = optionalTrip.get();

            trip.setTripState(TripState.ARCHIVE);

            trip = tripRepository.save(trip);
            return Optional.of(trip);
        }

        return Optional.empty();
    }

    public Optional<Trip> postponedTripById(Long tripId) {
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (optionalTrip.isPresent()) {
            Trip trip = optionalTrip.get();

            trip.setTripState(TripState.POSTPONED);

            trip = tripRepository.save(trip);
            return Optional.of(trip);
        }

        return Optional.empty();
    }

    public Optional<Trip> planedTripById(Long tripId) {
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        if (tripOptional.isPresent()) {
            Trip trip = tripOptional.get();

            trip.setTripState(TripState.PLANED);

            trip = tripRepository.save(trip);
            return Optional.of(trip);
        }

        return Optional.empty();
    }


    //        return Optional.empty();
    //        }
    //            return Optional.of(place);
    //
    //            place = placeRepository.save(place);
    //            }
    //                place.setVisited(true);
    //            } else {
    //                place.setVisited(false);
    //            if (place.isVisited()) {
    //
    //            Place place = placeOptional.get();
    //        if (placeOptional.isPresent()) {
    //        Optional<Place> placeOptional = placeRepository.findById(placeId);
// public Optional<Place> setVisitedStatus(boolean checkboxValue, Long placeId) {

//    }

    public List<Trip> listUserTrip(Long id) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);
        if (optionalAppUser.isPresent()) {
            AppUser appUser = optionalAppUser.get();

//            List<Trip> tripList = tripRepository.findAllByAppuser(appUser);

            return tripRepository.findAllByAppuser(appUser);
        }
        return new ArrayList<>();
    }

    public List<Trip> getTripByAppuserId(Long appuserId) {
        Optional<AppUser> optionalAppUser = appUserRepository.findById(appuserId);
        if (optionalAppUser.isPresent()) {
            return optionalAppUser.get().getTripList();
        }

        return new ArrayList<>();
    }

    public String returnTemplateDirect(String tripStatus) {

        if (TripState.valueOf(tripStatus).equals(TripState.PLANED)) {
            return "trip/list";
        } else if (TripState.valueOf(tripStatus).equals(TripState.ARCHIVE)) {
            return "trip/archived";
        } else {
            return "trip/postponed";
        }
    }
}
//    public Optional<Trip> addTrip(AddTripDto dto) {
//
//        Optional<AppUser> clientOptional = appUserRepository.findById(dto.getAppuserId());
//        if (clientOptional.isPresent()) {
//            AppUser appUser = clientOptional.get();
//
//            Trip trip = new Trip();
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//            trip.setName(dto.getName());
//            trip.setBeginDate(LocalDate.parse(dto.getBeginDate(), formatter));
//            trip.setEndDate(LocalDate.parse(dto.getEndDate(), formatter));
//            trip.setComments(dto.getComments());
//
//            trip.setAppuser(appUser);
//
//            trip = tripRepository.save(trip);
//
////            appUser.getTripList().add(trip);
////            appUser = appUserRepository.save(appUser);
//
//            return Optional.of(trip);
//        }
//
//        return Optional.empty();
//    }


//    public Optional<Trip> modifyTrip(ModifyTripDto dto) {
//        Optional<Trip> tripOptional = tripRepository.findById(dto.getTripId());
//
//        if (tripOptional.isPresent()) {
//            Trip trip = tripOptional.get();
//
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//            trip.setName(dto.getName());
//            trip.setBeginDate(LocalDate.parse(dto.getBeginDate(), formatter));
//            trip.setEndDate(LocalDate.parse(dto.getEndDate(), formatter));
//            trip.setComments(dto.getComments());
//
//            trip = tripRepository.save(trip);
//
//            return Optional.of(trip);
//        }
//        return Optional.empty();
//    }