package com.mashreq.bookameetingroom.service;

import com.mashreq.bookameetingroom.model.response.AvailableTimeSlot;
import com.mashreq.bookameetingroom.model.response.BookingResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRoomService {
    List<AvailableTimeSlot> getAvailableMeetingRooms(String startTime, String endTime);

    BookingResponse bookMeetingRoom(String startTime, int numberOfPeople);
}
