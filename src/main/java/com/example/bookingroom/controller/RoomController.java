package com.example.bookingroom.controller;

import com.example.bookingroom.common.Endpoint;
import com.example.bookingroom.dto.RoomDto;
import com.example.bookingroom.dto.RoomSearchResult;
import com.example.bookingroom.service.RoomService;
import com.example.bookingroom.service.validation.RoomSearchValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RoomController {
    public final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomSearchValidator searchValidator;

    @GetMapping(Endpoint.ROOM_INDEX)
    public String listAll(@Valid RoomSearchResult searchResult, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        searchValidator.validate(searchResult, bindingResult);

        if (bindingResult.hasErrors()) {

            return "redirect:/room";
        }

        //PageRequest start at index 0
        Page<RoomDto> roomDtoPage =
                roomService.searchRoom(
                        searchResult.getType(), searchResult.getStatus(), searchResult.getMinCap(), searchResult.getMaxCap(),
                        PageRequest.of(searchResult.getPage() - 1, searchResult.getSize()));
        List<RoomDto> roomList = roomDtoPage.toList();
        //Set new value, replace default
        searchResult.setNumsOfRecord(roomDtoPage.getTotalElements());
        searchResult.setNumsOfPage(roomDtoPage.getTotalPages());

        model.addAttribute("title", "Room Index");
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("roomList", roomList);

        return "room-index";
    }

    @GetMapping(Endpoint.ROOM_DETAIL)
    public String showDetail(@PathVariable("roomCode") String roomCode, Model model) {
        RoomDto roomToShow = roomService.getRoom(roomCode);

        model.addAttribute("title", "Room Info");
        model.addAttribute("room", roomToShow);

        return "room-info";
    }

    @GetMapping(Endpoint.ROOM_CREATE)
    public String create(Model model) {
        model.addAttribute("room", new RoomDto());
        model.addAttribute("title", "Create Room");

        return "room-info";
    }

    @PostMapping(Endpoint.ROOM_SAVE)
    public String save(@Valid @ModelAttribute RoomDto roomToSave, BindingResult bindingResult, RedirectAttributes atts, Model model) {
        String error;
        if (roomToSave.getId() == null) {
            try {
                roomService.createRoom(roomToSave);
                atts.addFlashAttribute("message", "Add room successfully. Room code: " + roomToSave.getRoomCode());
                return "redirect:" + Endpoint.ROOM_INDEX;
            } catch (Exception ex) {
                error = ex.getMessage();
            }
        } else {
            try {
                roomService.updateRoom(roomToSave);
                atts.addFlashAttribute("message", "Modify room successfully. Room code: " + roomToSave.getRoomCode());
                return "redirect:/room/detail/" + roomToSave.getRoomCode();
            } catch (Exception ex) {
                error = ex.getMessage();
            }
        }
        model.addAttribute("error", error);
        model.addAttribute("room", roomToSave);

        return "room-info";
    }

    @GetMapping(Endpoint.ROOM_DELETE)
    public String delete(@PathVariable(name = "roomCode") String roomCode, RedirectAttributes atts) {
        RoomDto roomToDelete = roomService.getRoom(roomCode);
        //TODO viết lại test chuẩn hơn
        roomService.deleteRoom(roomToDelete);
        atts.addFlashAttribute("message", "Delete room successfully. Room Code: " + roomCode);

        return "redirect:" + Endpoint.ROOM_INDEX;
    }
}
