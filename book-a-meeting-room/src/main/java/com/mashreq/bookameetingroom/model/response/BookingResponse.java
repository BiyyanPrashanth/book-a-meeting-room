package com.mashreq.bookameetingroom.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponse {
    private String responseMessage;
    private HttpStatus status;

}
