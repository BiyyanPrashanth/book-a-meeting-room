package com.mashreq.bookameetingroom.repository;

import com.mashreq.bookameetingroom.entity.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {
    List<MeetingRoom> findByCapacityGreaterThanEqual(Integer capacity);
}
