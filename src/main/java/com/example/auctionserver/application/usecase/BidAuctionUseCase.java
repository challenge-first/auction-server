package com.example.auctionserver.application.usecase;

import com.example.auctionserver.application.usecase.model.ResponseBidDto;
import com.example.auctionserver.application.port.in.BidAuctionCommand;

public interface BidAuctionUseCase {

    ResponseBidDto bid(BidAuctionCommand bidAuctionCommand);
}
