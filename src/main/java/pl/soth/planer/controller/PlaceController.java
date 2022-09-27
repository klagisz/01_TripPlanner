package pl.soth.planer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.soth.planer.model.AppUser;
import pl.soth.planer.model.Place;
import pl.soth.planer.model.Trip;
import pl.soth.planer.model.TypeOfPlace;
import pl.soth.planer.model.dto.AddPlaceToTripDto;
import pl.soth.planer.model.dto.ModifyPlaceDto;
import pl.soth.planer.service.LoginService;
import pl.soth.planer.service.PlaceService;
import pl.soth.planer.service.TripService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/place")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private TripService tripService;


    @GetMapping("/add/{tripId}")
    public String addPlace(Model model, @PathVariable(name = "tripId") Long tripId) {

        Optional<AppUser> optionalAppUser = loginService.getLoggedInUser();
        if (!optionalAppUser.isPresent()) {
            return "redirect:/login";
        }

        AddPlaceToTripDto addPlaceToTripDto = new AddPlaceToTripDto();
        addPlaceToTripDto.setTripId(tripId);

        model.addAttribute("added_place", addPlaceToTripDto);
        model.addAttribute("types_of_places", Arrays.asList(TypeOfPlace.values()));

        return "place/add";
    }

    @PostMapping("/add")
    public String addPlace(AddPlaceToTripDto dto) {

        Optional<Trip> optionalTrip = placeService.addPlaceToTrip(dto);
        if (optionalTrip.isPresent()) {
            return "redirect:/place/list/" + dto.getTripId();
        }

        return "redirect:/trip/tripList/" + optionalTrip.get().getTripState();
    }

    @GetMapping("/list/{tripId}")
    public String getList(Model model, @PathVariable(name = "tripId") Long tripId) {

        Optional<Trip> optionalTrip = tripService.findTripById(tripId);
        if (optionalTrip.isPresent()) {
            Trip trip = optionalTrip.get();
            List<Place> places = placeService.getPlaceByTrip(tripId);

            model.addAttribute("trip", trip);
            model.addAttribute("placeList", places);

            return "trip/tripprofile";
        }
        return "redirect:/";
    }

    @GetMapping("/details/{placeId}")
    public String getPlace(Model model, @PathVariable(name = "placeId") Long placeId) {
        Optional<Place> optionalPlace = placeService.getPlaceById(placeId);
        if (optionalPlace.isPresent()) {
            Place place = optionalPlace.get();

            model.addAttribute("single_place", place);
            try {
                model.addAttribute("address_country", place.getAdress().split(",")[0]);
                model.addAttribute("address_city", place.getAdress().split(",")[1]);
                model.addAttribute("address_street", place.getAdress().split(",")[2]);
                model.addAttribute("address_number", place.getAdress().split(",")[3]);
            } catch (IndexOutOfBoundsException ioobe) {
                //todo:
            }
            return "place/details";
        }
        return "redirect:/trip/places?error_message=Given supplier does not exist.";
    }

    @GetMapping("/modify/{placeId}")
    public String modify(Model model, @PathVariable(name = "placeId") Long placeId) {

        Optional<Place> placeOptional = placeService.getPlaceById(placeId);
        if (!placeOptional.isPresent()) {
            return "redirect:/place/list";
        }

        ModifyPlaceDto modifyPlaceDto = new ModifyPlaceDto();
        modifyPlaceDto.setPlaceId(placeId);

        model.addAttribute("modify_place", modifyPlaceDto);
        model.addAttribute("types_of_places", Arrays.asList(TypeOfPlace.values()));

        return "place/modify";
    }

    @PostMapping("/modify")
    public String modify(ModifyPlaceDto dto) {

        Optional<Place> optionalPlace = placeService.modifyPlace(dto);
        Place place = optionalPlace.get();

        return "redirect:/place/list/" + place.getTrip().getId();
    }

    @GetMapping("/delete/{placeId}")
    public String deletePlace(@PathVariable(name = "placeId") Long placeId) {
        Long tripId = placeService.getPlaceById(placeId).get().getTrip().getId();

        placeService.deletePlaceById(placeId);

        return "redirect:/place/list/" + tripId;
    }

    @GetMapping("visit/{placeId}")
    public String checkboxChange(@PathVariable(name = "placeId") Long placeId) {

        Long tripId = placeService.getPlaceById(placeId).get().getTrip().getId();

        Optional<Place> placeOptional = placeService.getPlaceById(placeId);
        if (!placeOptional.isPresent()) {
            return "redirect:/place/list" + tripId;
        }

        placeService.setVisitedStatus(placeId);

        return "redirect:/place/list/" + tripId;
    }
}
