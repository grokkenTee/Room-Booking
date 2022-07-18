package com.example.bookingroom.entity;

import com.example.bookingroom.common.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String roomCode;

    @Column(nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private String description;

    private String imageUrl;

//    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;
}
