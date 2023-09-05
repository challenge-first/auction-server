package com.example.auctionserver.application.port.out;

import com.example.auctionserver.domain.Auction;

import java.time.LocalDateTime;

public interface GetAuctionPort {

    Auction findByCurrentTime(LocalDateTime currentTime);
}
