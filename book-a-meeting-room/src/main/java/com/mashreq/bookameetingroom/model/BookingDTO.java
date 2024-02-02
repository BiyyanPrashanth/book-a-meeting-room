package com.mashreq.bookameetingroom.model;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class BookingDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int numberOfPeople;
}
