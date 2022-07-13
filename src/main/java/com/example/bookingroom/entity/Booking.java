package com.example.bookingroom.entity;

import com.example.bookingroom.common.ERoomStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Booking extends BaseEntity {
    @Column(unique = true, nullable = false)
    private Long bookingId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_number",referencedColumnName = "roomNumber")
    private Room room;

    @Column(nullable = false)
    private LocalDateTime fromTime;

    @Column(nullable = false)
    private LocalDateTime toTime;

    @Enumerated(EnumType.STRING)
    private ERoomStatus status;
}
