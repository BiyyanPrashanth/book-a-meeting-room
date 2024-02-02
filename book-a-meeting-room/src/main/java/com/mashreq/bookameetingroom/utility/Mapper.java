package com.mashreq.bookameetingroom.utility;

import com.mashreq.bookameetingroom.entity.BookingDetails;
import com.mashreq.bookameetingroom.entity.MeetingRoom;
import com.mashreq.bookameetingroom.model.BookingDTO;
import com.mashreq.bookameetingroom.model.MeetingRoomDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Mapper {

    public static List<MeetingRoomDTO> convertToMeetingDTO(List<MeetingRoom> meetingRooms) {
        List<MeetingRoomDTO> meetingRoomDTOList= meetingRooms.stream()
                .map(meetingRoom-> {
                    MeetingRoomDTO meetingRoomDTO= new MeetingRoomDTO();
                    meetingRoomDTO.setId(meetingRoom.getId());
                    meetingRoomDTO.setName(meetingRoom.getName());
                    meetingRoomDTO.setCapacity(meetingRoom.getCapacity());
                    meetingRoomDTO.setBookings(convertToBookingDTO(meetingRoom.getBookings()));
                    return meetingRoomDTO;
                }).collect(Collectors.toList());
        return meetingRoomDTOList ;
    }

    public static List<BookingDTO> convertToBookingDTO(List<BookingDetails> bookings) {
        List<BookingDTO> bookingDTOList= bookings.stream()
                .map(booking-> {
                    BookingDTO bookingDTO= new BookingDTO();
                    bookingDTO.setEndTime(booking.getEndTime());
                    bookingDTO.setStartTime(booking.getStartTime());
                    bookingDTO.setNumberOfPeople(booking.getCapacity());
                    return bookingDTO;
                }).collect(Collectors.toList());
        return bookingDTOList;
    }
}
