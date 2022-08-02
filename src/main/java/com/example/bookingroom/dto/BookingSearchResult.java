package com.example.bookingroom.dto;

import com.example.bookingroom.common.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookingSearchResult {
    private String roomCode = null;
    private BookingStatus status = null;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fromTime = null;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime toTime = null;

    private Long numsOfRecord = 0L;
    private Integer numsOfPage = 0;

    @Min(value = 1, message = "Ê page number phải dương")
    private Integer page = 1;
    @Min(value = 1, message = "Ê page size phải dương")
    private Integer size = 10;

}
