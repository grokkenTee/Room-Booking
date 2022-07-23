package com.example.bookingroom.controller;

import com.example.bookingroom.common.Endpoint;
import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;
import com.example.bookingroom.dto.RoomDto;
import com.example.bookingroom.dto.RoomSearchResult;
import com.example.bookingroom.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RoomController {
    public final Logger logger = LoggerFactory.getLogger(RoomController.class);

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    //TODO refactor default value?
    @GetMapping(Endpoint.ROOM_INDEX)
    public String listAll(Model model,
                          @RequestParam(name = "type", required = false) RoomType type,
                          @RequestParam(name = "status", required = false) RoomStatus status,
                          @RequestParam(name = "min", required = false, defaultValue = "0") Integer minCap,
                          @RequestParam(name = "max", required = false, defaultValue = "999999") Integer maxCap,
                          @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
        //PageRequest start at index 0
        Page<RoomDto> roomDtoPage = roomService.searchRoom(type, status, minCap, maxCap, PageRequest.of(page - 1, size));
        List<RoomDto> roomList = roomDtoPage.toList();

        var searchResult = new RoomSearchResult(
                type, status, minCap, maxCap,
                page, size,
                roomDtoPage.getTotalElements(), roomDtoPage.getTotalPages());

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
    public String save(RoomDto roomToSave, RedirectAttributes atts, Model model) {
        String error = new String();
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
