package com.example.bookingroom.controller;

import com.example.bookingroom.common.Endpoint;
import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;
import com.example.bookingroom.dto.RoomDto;
import com.example.bookingroom.service.RoomService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @GetMapping(Endpoint.ROOM_PAGE)
    public String listAll(Model model,
                          @RequestParam(name = "type", required = false) RoomType type,
                          @RequestParam(name = "status", required = false) RoomStatus status,
                          @RequestParam(name = "min", required = false, defaultValue = "0") Integer minCap,
                          @RequestParam(name = "max", required = false, defaultValue = "999999") Integer maxCap,
                          @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "5") Integer size ){
        Page<RoomDto> roomDtoPage = roomService.searchRoom(type, status, minCap, maxCap, PageRequest.of(page, size));
        List<RoomDto> roomList = roomDtoPage.toList();
        Pageable pageDetail = roomDtoPage.getPageable();


        model.addAttribute("title", "Room Index");
        model.addAttribute("numsRecord", roomDtoPage.getTotalElements());
        model.addAttribute("pageNumber", pageDetail.getPageNumber());
        model.addAttribute("numsPage", roomDtoPage.getTotalPages());
        model.addAttribute("roomList", roomList);
        return "room-index";
    }

    @GetMapping(Endpoint.ROOM_DETAIL)
    public String showDetail(@PathVariable("roomCode") String roomCode, Model model) {
        RoomDto roomToShow = roomService.getRoom(roomCode).get();

        model.addAttribute("title", "Room Info");
        model.addAttribute("room", roomToShow);
        return "room-info";
    }

    @GetMapping(Endpoint.ROOM_CREATE)
    public String create(Model model) {
        model.addAttribute("room", new RoomDto());
        return "room-info";
    }

    @PostMapping(Endpoint.ROOM_SAVE)
    public String save(RoomDto roomToSave, RedirectAttributes atts) {
        //id null means this is a new room request, otherwise it is an update request
        if (roomToSave.getId() == null) {
            roomService.createRoom(roomToSave);
            //TODO controllerAdvice?
            atts.addFlashAttribute("message", "Add room successfully. Room code: " + roomToSave.getRoomCode());
            return "redirect:/room";
        } else {
            roomService.modifyRoom(roomToSave);
            atts.addFlashAttribute("message", "Modify room successfully. Room code: " + roomToSave.getRoomCode());
            return "redirect:/room/detail/" + roomToSave.getRoomCode();
        }
    }

    @GetMapping(Endpoint.ROOM_DELETE)
    public String delete(@PathVariable(name = "roomCode") String roomCode, RedirectAttributes atts) {
        RoomDto roomToDelete = roomService.getRoom(roomCode).get();
        //TODO viết lại test chuẩn hơn
        roomService.deleteRoom(roomToDelete);
        atts.addFlashAttribute("message", "Delete room successfully. Room Code: " + roomCode);
        return "redirect:/room";
    }
}
