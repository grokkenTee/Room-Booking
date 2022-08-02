package com.example.bookingroom.entity;

import com.example.bookingroom.common.BookingStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_code", referencedColumnName = "roomCode")
    @JsonIgnore
    private Room room;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Column(columnDefinition = "text")
    private String description;

    @Override
    public String toString() {
        return "Booking{ " + "id=" + super.getId() +
                "room=" + room +
                ", fromTime=" + startTime +
                ", toTime=" + endTime +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
