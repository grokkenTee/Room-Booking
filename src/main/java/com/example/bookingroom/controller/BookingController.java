package com.example.bookingroom.controller;

import com.example.bookingroom.common.Endpoint;
import com.example.bookingroom.dto.BookingDto;
import com.example.bookingroom.dto.BookingSearchResult;
import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.service.BookingService;
import com.example.bookingroom.service.RoomService;
import com.example.bookingroom.service.validation.BookingSearchValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class BookingController {
    public static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookingSearchValidator searchValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) return;
        final List<Validator> validatorsList = List.of(
                new BookingSearchValidator()
        );

        for (Validator validator : validatorsList) {
            if (validator.supports(binder.getTarget().getClass())) {
                binder.addValidators(validator);
            }
        }
    }

    @GetMapping(Endpoint.BOOKING_INDEX)
    public String listAll(@Valid BookingSearchResult searchResult, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            Page<BookingDto> bookingDtoPage =
                    bookingService.searchBooking(
                            searchResult.getRoomCode(), searchResult.getStatus(),
                            searchResult.getFromTime(), searchResult.getToTime(),
                            PageRequest.of(searchResult.getPage() - 1, searchResult.getSize()));

            List<BookingDto> bookingList = bookingDtoPage.toList();
            searchResult.setNumsOfRecord(bookingDtoPage.getTotalElements());
            searchResult.setNumsOfPage(bookingDtoPage.getTotalPages());

            model.addAttribute("title", "Booking index");
            model.addAttribute("bookingSearchResult", searchResult);
            model.addAttribute("bookingList", bookingList);
            model.addAttribute("roomCodeList", roomService.getListRoomCode());
        }
        return "booking-index";
    }

    @GetMapping(Endpoint.BOOKING_DETAIL)
    public String showDetail(@PathVariable Long bookingId, Model model) {
        String error;
        try {
            BookingDto bookingDto = bookingService.getBooking(bookingId);
            model.addAttribute("bookingDto", bookingDto);
        } catch (Exception e) {
            error = e.getMessage();
            model.addAttribute("error", error);
        }
        model.addAttribute("title", "Booking Detail");
        model.addAttribute("roomCodeList", roomService.getListRoomCode());

        return "booking-info";
    }

    @GetMapping(Endpoint.BOOKING_CREATE)
    public String createBooking(Model model) {
        model.addAttribute("bookingDto", new BookingDto());
        model.addAttribute("title", "Create new booking");
        model.addAttribute("roomCodeList", roomService.getListRoomCode());

        return "booking-info";
    }

    @PostMapping(Endpoint.BOOKING_SAVE)
    public String saveBooking(@Valid BookingDto bookingDto,
                              BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        model.addAttribute("roomCodeList", roomService.getListRoomCode());
        if (bindingResult.hasErrors()) {
            return "booking-info";
        }
        String message = null;

        try {
            if (Objects.isNull(bookingDto.getId())) {
                bookingDto = bookingService.createBooking(bookingDto);
                message = String.format("Create booking successfully. Booking id: %s, room code: %s", bookingDto.getId(), bookingDto.getRoomCode());
            } else {
                bookingDto = bookingService.modifyBooking(bookingDto);
                message = String.format("Modify booking successfully. Booking id: %s, room code: %s", bookingDto.getId(), bookingDto.getRoomCode());
            }
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "booking-info";
        }
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/booking/detail/" + bookingDto.getId();
    }

    //TODO handle data đúng chuẩn ?
    @GetMapping(Endpoint.BOOKING_DELETE)
    public String deleteBooking(@PathVariable Long bookingId, RedirectAttributes redirectAttributes) {
        try {
            BookingDto bookingToDel = bookingService.getBooking(bookingId);
            bookingService.deleteBooking(bookingToDel);
            redirectAttributes.addFlashAttribute("message", "Successfully delete booking id " + bookingId + " of room " + bookingToDel.getRoomCode());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/booking";
    }

    @GetMapping("/api/booking")
    public @ResponseBody
    ResponseEntity<List<Booking>> getAll() {
        List<Booking> bookingList = bookingService.getAllBookingApi();
        return ResponseEntity.ok(bookingList);
    }

    @PostMapping("/api/booking/save")
    public @ResponseBody
    ResponseEntity<Object> save(BookingDto bookingDto, String roomCode) {
        try {
            return ResponseEntity.ok(bookingService.createBooking(bookingDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(null);
    }

}
