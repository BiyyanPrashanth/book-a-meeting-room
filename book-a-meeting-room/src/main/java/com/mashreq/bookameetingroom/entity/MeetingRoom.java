package com.mashreq.bookameetingroom.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "meetingRooms")
@NoArgsConstructor
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_room_id")
    private Long id;
    @Column(name = "meeting_room_name")
    private String name;
    @Column(name = "meeting_room_capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "meetingRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingDetails> bookings;

    public MeetingRoom(Long id) {
        this.id = id;
    }
}
