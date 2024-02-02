package com.mashreq.bookameetingroom.service.impl;

import com.mashreq.bookameetingroom.advice.BookingException;
import com.mashreq.bookameetingroom.entity.BookingDetails;
import com.mashreq.bookameetingroom.entity.MeetingRoom;
import com.mashreq.bookameetingroom.model.BookingDTO;
import com.mashreq.bookameetingroom.model.MeetingRoomDTO;
import com.mashreq.bookameetingroom.model.response.AvailableTimeSlot;
import com.mashreq.bookameetingroom.model.response.BookingResponse;
import com.mashreq.bookameetingroom.repository.BookRoomRepository;
import com.mashreq.bookameetingroom.repository.MeetingRoomRepository;
import com.mashreq.bookameetingroom.service.MeetingRoomService;
import com.mashreq.bookameetingroom.utility.ApplicationConstants;
import com.mashreq.bookameetingroom.utility.ApplicationProperties;
import com.mashreq.bookameetingroom.utility.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class MeetingRoomServiceImpl implements MeetingRoomService {

    @Autowired
    MeetingRoomRepository meetingRoomRepository;
    @Autowired
    BookRoomRepository bookRoomRepository;

    @Autowired
    private ApplicationProperties appProp;

    @Override
    public List<AvailableTimeSlot> getAvailableMeetingRooms(String inputStartTime, String inputEndTime) {
        LocalDateTime startTime = dateForHourMinutes(inputStartTime);
        LocalDateTime endTime = dateForHourMinutes(inputEndTime);
        List<AvailableTimeSlot> availableTimeSlots = new ArrayList<>();
        List<MeetingRoom> allMeetingRooms = meetingRoomRepository.findAll();
        List<MeetingRoomDTO> meetingRoomDTOS = Mapper.convertToMeetingDTO(allMeetingRooms);
        while (startTime.isBefore(endTime)) {
            LocalDateTime slotEndTime = startTime.plusMinutes(appProp.getSlotTime());
            List<MeetingRoomDTO> availableMeetingRooms = new ArrayList<>();
            LocalDateTime finalStartTime = startTime;
            meetingRoomDTOS.stream().forEach(meetingRoom ->{
               String slotAvailable= isAvailable(finalStartTime, slotEndTime, meetingRoom.getBookings());
                if (slotAvailable.equalsIgnoreCase(ApplicationConstants.AVAILABLE)) {
                    meetingRoom.setMeetingRoomStatus(ApplicationConstants.AVAILABLE);
                    availableMeetingRooms.add(meetingRoom);
                } else if (slotAvailable.equalsIgnoreCase(ApplicationConstants.BOOKED))  {
                    meetingRoom.setMeetingRoomStatus(ApplicationConstants.BOOKED);
                    availableMeetingRooms.add(meetingRoom);
                } else {
                    meetingRoom.setMeetingRoomStatus(ApplicationConstants.MAINTENANCE);
                    availableMeetingRooms.add(meetingRoom);
                }

            });
            availableTimeSlots.add(new AvailableTimeSlot(startTime, slotEndTime, availableMeetingRooms));
            startTime = slotEndTime;
        }

        return availableTimeSlots;
    }

    @Override
    public BookingResponse bookMeetingRoom(String inputStartTime, int numberOfPeople) {
        LocalDateTime startTime = dateForHourMinutes(inputStartTime);
        LocalDateTime endTime = startTime.plusMinutes(appProp.getSlotTime());
        List<MeetingRoom> availableRooms = meetingRoomRepository.findByCapacityGreaterThanEqual(numberOfPeople);
        List<MeetingRoomDTO> availableRoomsDTO = Mapper.convertToMeetingDTO(availableRooms);
        availableRooms.sort(Comparator.comparingInt(MeetingRoom::getCapacity).reversed());
        if (!availableRoomsDTO.isEmpty()) {
            for (MeetingRoomDTO meetingRoom : availableRoomsDTO) {
                String slotAvailable=isAvailable(startTime, endTime, meetingRoom.getBookings());
                if (slotAvailable.equalsIgnoreCase(ApplicationConstants.AVAILABLE) && meetingRoom.getCapacity() >= numberOfPeople) {
                    BookingDetails newBooking = new BookingDetails(startTime, endTime, numberOfPeople, new MeetingRoom(meetingRoom.getId()));
                    bookRoomRepository.save(newBooking);
                    return new BookingResponse("Meeting room booked successfully: " + meetingRoom.getName(), HttpStatus.OK);
                } else if (slotAvailable.equalsIgnoreCase(ApplicationConstants.BOOKED)) {
                    throw new BookingException("Meeting room is already booked during the requested time range");
                } else{
                    throw new BookingException("meeting room is under maintenance  during the requested time range");
                }
            }
        }
        // No available rooms for the requested time and capacity
        return new BookingResponse("No available meeting rooms for the requested time and capacity.", HttpStatus.OK);
        //return ResponseEntity.badRequest().body();
    }


    // Method to check if the room is available for a given time range
    private String isAvailable(LocalDateTime startTime, LocalDateTime endTime, List<BookingDTO> bookings) {

            Boolean isRoomUnderMaintenance = checkRoomMaintenance(startTime, endTime);
        if (!isRoomUnderMaintenance) {
            if (Objects.nonNull(bookings)) {
                for (BookingDTO booking : bookings) {
                    if (startTime.isBefore(booking.getEndTime()) && endTime.isAfter(booking.getStartTime())) {
                        return ApplicationConstants.BOOKED;
                    }
                }
            }
        } else {
            return ApplicationConstants.MAINTENANCE;
        }
        return ApplicationConstants.AVAILABLE;
    }

    private Boolean checkRoomMaintenance(LocalDateTime startTime, LocalDateTime endTime) {
        List<String> maintenanceSlots = appProp.getMaintenanceSlot();
        for (String timeslot : maintenanceSlots) {
            String[] splitTimeSlot = timeslot.split(ApplicationConstants.REGEX);
            LocalDateTime maintenanceStart = dateForHourMinutes(splitTimeSlot[0]);
            LocalDateTime maintenanceEnd = dateForHourMinutes(splitTimeSlot[1]);
            if (startTime.isBefore(maintenanceEnd) && endTime.isAfter(maintenanceStart)) {
                return true;
            }
        }
        return false;
    }

    private LocalDateTime dateForHourMinutes(String hourMinu) {
        LocalDateTime startDateTime = LocalDateTime.now()
                .withHour(Integer.parseInt(hourMinu.split(":")[0]))
                .withMinute(Integer.parseInt(hourMinu.split(":")[1]));
        return startDateTime;
    }
}

