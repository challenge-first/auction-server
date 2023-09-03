package com.example.auctionserver.application.port.out;

import com.example.auctionserver.application.port.in.model.RequestBidDto;
import com.example.auctionserver.domain.Auction;

public interface UpdateWinningPricePort {

    Auction updateAuction(Long auctionId, RequestBidDto bidAuctionRequest, Long memberId);
}
