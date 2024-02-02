package com.mashreq.bookameetingroom.controller;

import com.mashreq.bookameetingroom.model.MeetingRoomDTO;
import com.mashreq.bookameetingroom.model.response.AvailableTimeSlot;
import com.mashreq.bookameetingroom.model.response.BookingResponse;
import com.mashreq.bookameetingroom.service.MeetingRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/meetingRoom")
public class MeetingRoomController {
    @Autowired
    private MeetingRoomService meetingRoomService;

    @GetMapping("/available")
    public ResponseEntity<List<AvailableTimeSlot>> getAvailableMeetingRooms(
            @RequestParam("startTime") @DateTimeFormat(pattern = "HH:mm") String startTime,
            @RequestParam("endTime") @DateTimeFormat(pattern = "HH:mm") String endTime) {
        List<AvailableTimeSlot> availableMeetingRoom = meetingRoomService.getAvailableMeetingRooms(startTime, endTime);
        return ResponseEntity.ok(availableMeetingRoom);
    }

    @PostMapping("/book")
    public ResponseEntity<BookingResponse> bookMeetingRoom(
            @RequestParam("startTime") @DateTimeFormat(pattern = "HH:mm") String startTime,
            @RequestParam("numberOfPeople") int numberOfPeople) {
        BookingResponse bookingResponse= meetingRoomService.bookMeetingRoom(startTime, numberOfPeople);
        return  ResponseEntity.ok(bookingResponse);
    }


}
