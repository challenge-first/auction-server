package com.example.auctionserver.application.port.out;

import com.example.auctionserver.domain.Auction;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GetAuctionPort {

    Auction findByCurrentTime(LocalDateTime currentTime);
}
