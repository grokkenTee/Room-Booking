package com.example.bookingroom.entity;

import com.example.bookingroom.common.RoomStatus;
import com.example.bookingroom.common.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//TODO thay bang custom toString()
@ToString
@Entity
public class Room extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String roomCode;

    @Column(nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    private String description;

    private String imageUrl;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Booking> bookings;

}
