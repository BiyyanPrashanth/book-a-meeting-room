package com.mashreq.bookameetingroom.repository;

import com.mashreq.bookameetingroom.entity.BookingDetails;
import com.mashreq.bookameetingroom.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRoomRepository extends JpaRepository<BookingDetails, Long> {
}
