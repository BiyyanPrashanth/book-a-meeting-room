package com.mashreq.bookameetingroom.model;

import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@NoArgsConstructor
public class MeetingRoomDTO {
    private Long id;
    private String name;
    private Integer capacity;
    private String MeetingRoomStatus;
    private List<BookingDTO> bookings;

    public MeetingRoomDTO(Long id, String name, Integer capacity, List<BookingDTO> bookings) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.bookings = bookings;
    }
}
