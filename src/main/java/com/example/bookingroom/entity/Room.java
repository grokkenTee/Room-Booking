package com.example.bookingroom.entity;

import com.example.bookingroom.common.ERoomType;
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
    private String roomId;

    @Column(nullable = false)
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    private ERoomType roomType;

    private String description;

    private String imageUrl;

    @OneToMany(mappedBy = "room")
    private List<Booking> bookings;
}
