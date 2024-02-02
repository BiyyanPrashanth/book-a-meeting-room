package com.mashreq.bookameetingroom.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meetingRoom_BookingDetails")
public class BookingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer capacity;

    @ManyToOne
    @JoinColumn(name = "meeting_room_id", nullable = false)
    private MeetingRoom meetingRoom;

    public BookingDetails(LocalDateTime startTime, LocalDateTime endTime, Integer capacity,MeetingRoom meetingRoom) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = capacity;
        this.meetingRoom= meetingRoom;
    }
}
