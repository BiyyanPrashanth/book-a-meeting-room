package com.mashreq.bookameetingroom.model;

import lombok.Data;


import java.util.List;

@Data
public class MeetingRoomDTO {
    private Long id;
    private String name;
    private Integer capacity;
    private String MeetingRoomStatus;
    private List<BookingDTO> bookings;

}
