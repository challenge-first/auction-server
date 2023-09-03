package com.example.auctionserver.application.port.out;

import com.example.auctionserver.domain.Auction;

import java.time.LocalDateTime;

public interface UpdateWinningPricePort {

    Auction updateAuction(Long auctionId, Long bidPoint, LocalDateTime bidTime, Long memberId);
}
