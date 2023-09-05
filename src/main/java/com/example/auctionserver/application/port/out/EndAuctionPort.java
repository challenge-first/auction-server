package com.example.auctionserver.application.port.out;

import com.example.auctionserver.domain.Auction;

import java.time.LocalDateTime;

public interface EndAuctionPort {

    Auction findByClosingTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
}
