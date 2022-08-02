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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RoomController {
    public final Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private RoomService roomService;

    @Autowired
    private RoomSearchValidator roomSearchValidator;

    @InitBinder("roomSearchResult")
    protected void initBinder(final WebDataBinder binder) {
        binder.addValidators(roomSearchValidator);
    }

    @GetMapping(Endpoint.ROOM_INDEX)
    public String listAll(@Valid RoomSearchResult searchResult, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            //PageRequest start at index 0
            Page<RoomDto> roomDtoPage =
                    roomService.searchRoom(
                            searchResult.getType(), searchResult.getStatus(), searchResult.getMinCap(), searchResult.getMaxCap(),
                            PageRequest.of(searchResult.getPage() - 1, searchResult.getSize()));
            List<RoomDto> roomList = roomDtoPage.toList();
            //TODO nên để final rồi tạo mới trả về hay là như này?
            //Set new value, replace default
            searchResult.setNumsOfRecord(roomDtoPage.getTotalElements());
            searchResult.setNumsOfPage(roomDtoPage.getTotalPages());

            model.addAttribute("title", "Room Index");
            model.addAttribute("roomSearchResult", searchResult);
            model.addAttribute("roomList", roomList);
        }
        return "room-index";
    }

    @GetMapping(Endpoint.ROOM_DETAIL)
    public String showDetail(@PathVariable("roomCode") String roomCode, Model model) {
        RoomDto roomToShow = roomService.getRoom(roomCode);

        model.addAttribute("title", "Room Info");
        model.addAttribute("roomDto", roomToShow);

        return "room-info";
    }

    @GetMapping(Endpoint.ROOM_CREATE)
    public String create(Model model) {
        model.addAttribute("title", "Create Room");
        model.addAttribute("roomDto", new RoomDto());

        return "room-info";
    }

    @PostMapping(Endpoint.ROOM_SAVE)
    public String save(@Valid @ModelAttribute RoomDto roomToSave, BindingResult bindingResult, RedirectAttributes atts, Model model) {
        String error;
        if (bindingResult.hasErrors())
            return "room-info";

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
        model.addAttribute("roomDto", roomToSave);

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
