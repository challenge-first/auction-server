package com.example.auctionserver.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class BidAuctionRequest {

    private Long point;
    private LocalDateTime time;

    // 입찰 관련 유효성 검사
}
