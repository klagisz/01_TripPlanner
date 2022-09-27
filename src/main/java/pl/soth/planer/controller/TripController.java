package pl.soth.planer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.soth.planer.model.TripState;
import pl.soth.planer.repository.TripRepository;
import pl.soth.planer.model.AppUser;
import pl.soth.planer.model.Trip;
import pl.soth.planer.model.dto.AddTripDto;
import pl.soth.planer.model.dto.ModifyTripDto;
import pl.soth.planer.service.LoginService;
import pl.soth.planer.service.PlaceService;
import pl.soth.planer.service.TripService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/trip/")
public class TripController {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripService tripService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/add")
    public String add(Model model) {
        Optional<AppUser> optionalAppUser = loginService.getLoggedInUser();
        if (!optionalAppUser.isPresent()) {
            return "redirect:/login";
        }
        AddTripDto addTripToAppUserDto = new AddTripDto();
        addTripToAppUserDto.setAppuserId(optionalAppUser.get().getId());

        model.addAttribute("added_trip", addTripToAppUserDto);

        return "trip/add";
    }

    @PostMapping("/add")
    public String add(Model model, AddTripDto dto) {

        if (dto.getName().isEmpty()) {
            model.addAttribute("added_trip", dto);
            model.addAttribute("error_message", "Name of trip is empty!");
            return "trip/add";
        }
        if (dto.getBeginDate().isEmpty() || dto.getEndDate().isEmpty()) {
            model.addAttribute("added_trip", dto);
            model.addAttribute("error_message", "Begin or end date is empty!");
            return "trip/add";
        }

        Optional<Trip> tripOptional = tripService.addTrip(dto);
        Long tripId = tripOptional.get().getId();
        return "redirect:/place/list/" + tripId;

//        TODO: PRZEKIEROWAĆ NA LISTĘ PLANED PLACE TO SEE
    }

//    @GetMapping("/list")
//    public String getList(Model model) {
//
//        Optional<AppUser> optionalAppUser = loginService.getLoggedInUser();
//        if (optionalAppUser.isPresent()) {
//            List<Trip> tripList = tripService.getTripByAppuserId(optionalAppUser.get().getId());
//
//            model.addAttribute("tripList", tripList);
//            return "trip/list";
//        }
//
//        return "redirect:/login";
//    }

    @GetMapping("/tripList/{tripStatus}")
    public String getList(Model model,
                          @PathVariable(name = "tripStatus", required = false) String tripStatus) {

//        String direct = tripService.returnTemplateDirect(tripStatus);
        Optional<AppUser> optionalAppUser = loginService.getLoggedInUser();
        if (optionalAppUser.isPresent()) {
            List<Trip> tripList = tripService.getTripByAppuserId(optionalAppUser.get().getId());

            model.addAttribute("tripList", tripList.stream()
                    .filter(x -> x.getTripState().equals(TripState.valueOf(tripStatus)))
                    .collect(Collectors.toList()));
            model.addAttribute("tripStatus", tripStatus.toLowerCase());

            if (TripState.valueOf(tripStatus).equals(TripState.PLANED)) {
                return "trip/list";
            } else if (TripState.valueOf(tripStatus).equals(TripState.ARCHIVE)) {
                return "trip/archived";
            } else {
                return "trip/postponed";
            }
        }

        return "redirect:/login";
    }


    @GetMapping("/modify/{tripId}")
    public String modify(Model model, @PathVariable(name = "tripId") Long id) {

        Optional<AppUser> optionalAppUser = loginService.getLoggedInUser();
        if (!optionalAppUser.isPresent()) {
            return "redirect:/login";
        }

        ModifyTripDto modifyTripDto = new ModifyTripDto();
        modifyTripDto.setTripId(id);

        model.addAttribute("modify_trip", modifyTripDto);

        return "trip/modify";
    }

    @PostMapping("/modify")
    public String modify(Model model, ModifyTripDto modifyTripDto) {
        String tripState = String.valueOf(tripService
                .findTripById(modifyTripDto.getTripId()).get().getTripState());

        Optional<Trip> optionalTrip = tripService.modifyTrip(modifyTripDto);
        if (optionalTrip.isPresent()) {
            return "redirect:/trip/tripList/" + tripState;
        }
        return "redirect:/trip/tripList/" + tripState;
    }

    @GetMapping("/delete/{tripId}")
    public String deleteTrip(@PathVariable(name = "tripId") Long tripId) {
        String tripState = String.valueOf(tripService.findTripById(tripId).get().getTripState());
        tripService.deleteTripById(tripId);

        return "redirect:/trip/tripList/" + tripState;
    }

    @GetMapping("archive/{tripId}")
    public String archiveTrip(@PathVariable(name = "tripId") Long tripId) {
        String tripState = String.valueOf(tripService.findTripById(tripId).get().getTripState());
        tripService.archveTripById(tripId);

        return "redirect:/trip/tripList/" + tripState;
    }

    @GetMapping("postponed/{tripId}")
    public String postponedTrip(@PathVariable(name = "tripId") Long tripId) {
        String tripState = String.valueOf(tripService.findTripById(tripId).get().getTripState());
        tripService.postponedTripById(tripId);

        return "redirect:/trip/tripList/" + tripState;
    }

    @GetMapping("planed/{tripId}")
    public String planedTrip(@PathVariable(name = "tripId") Long tripId) {
        String tripState = String.valueOf(tripService.findTripById(tripId).get().getTripState());
        tripService.planedTripById(tripId);

        return "redirect:/trip/tripList/" + tripState;
    }
}
