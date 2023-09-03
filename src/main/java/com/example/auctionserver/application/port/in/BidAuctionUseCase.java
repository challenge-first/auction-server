package com.example.auctionserver.application.port.in;

import com.example.auctionserver.application.port.in.model.RequestBidDto;
import com.example.auctionserver.application.port.out.model.ResponseWinningPriceDto;

public interface BidAuctionUseCase {

    ResponseWinningPriceDto bid(Long auctionId, RequestBidDto requestBidDto, Long memberId);
}
