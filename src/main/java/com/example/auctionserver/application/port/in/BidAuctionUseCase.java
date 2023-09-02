package com.example.auctionserver.application.port.in;

import com.example.auctionserver.application.port.out.model.UpdateWinningPriceResponse;

public interface BidAuctionUseCase {

    UpdateWinningPriceResponse bid(Long auctionId, BidAuctionRequest bidAuctionRequest, Long memberId);
}
