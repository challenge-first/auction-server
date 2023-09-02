package com.example.auctionserver.application.port.out;

import com.example.auctionserver.application.port.in.BidAuctionRequest;
import com.example.auctionserver.domain.Auction;

public interface UpdateWinningPricePort {

    Auction updateAuction(Long auctionId, BidAuctionRequest bidAuctionRequest, Long memberId);
}
