package com.mashreq.bookameetingroom.model.response;


import com.mashreq.bookameetingroom.model.MeetingRoomDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
public class AvailableTimeSlot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<MeetingRoomDTO> availableMeetingRooms;


}
