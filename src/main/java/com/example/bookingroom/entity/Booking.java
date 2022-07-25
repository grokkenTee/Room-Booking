package com.example.bookingroom.entity;

import com.example.bookingroom.common.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking extends BaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_code", referencedColumnName = "roomCode")
    private Room room;

    @Column(nullable = false)
    private LocalDateTime fromTime;

    @Column(nullable = false)
    private LocalDateTime toTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(columnDefinition = "text")
    private String description;
}
