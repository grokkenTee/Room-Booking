package com.example.bookingroom.controller;

import com.example.bookingroom.common.Endpoint;
import com.example.bookingroom.dto.BookingDto;
import com.example.bookingroom.entity.Booking;
import com.example.bookingroom.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class BookingController {
    public static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(Endpoint.BOOKING_INDEX)
    public String listAll(Model model) {
        List<BookingDto> bookingList = bookingService.getAllBooking();
        model.addAttribute("bookingList", bookingList);

        return "booking-index";
    }

    @GetMapping(Endpoint.BOOKING_DETAIL)
    public String showDetail(@PathVariable Long bookingId, Model model) {
        String error;
        try {
            BookingDto bookingDto = bookingService.getBooking(bookingId);
            model.addAttribute("booking", bookingDto);
        } catch (Exception e) {
            error = e.getMessage();
            model.addAttribute("error", error);
        }
        model.addAttribute("title", "Booking Detail");

        return "booking-info";
    }

    @GetMapping(Endpoint.BOOKING_CREATE)
    public String createBooking(Model model) {
        model.addAttribute("booking", new BookingDto());
        model.addAttribute("title", "Create new booking");

        return "booking-info";
    }

    @PostMapping(Endpoint.BOOKING_SAVE)
    public String saveBooking(@Valid BookingDto bookingDto, RedirectAttributes redirectAttributes) {
        if (Objects.isNull(bookingDto.getId())) {
            try {
                bookingService.createBooking(bookingDto, bookingDto.getRoomCode());
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", e.getMessage());
            }
        } else {
            bookingService.updateBooking(bookingDto);
        }
        return "redirect:/booking";
    }

    @GetMapping(Endpoint.BOOKING_DELETE)
    public String deleteBooking(@PathVariable Long bookingId) {
        try {
            BookingDto roomToDelete = bookingService.getBooking(bookingId);
            bookingService.deleteBooking(roomToDelete);
        } catch (Exception e) {
            e.printStackTrace();
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
            return ResponseEntity.ok(bookingService.createBooking(bookingDto, roomCode));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(null);
    }
}
