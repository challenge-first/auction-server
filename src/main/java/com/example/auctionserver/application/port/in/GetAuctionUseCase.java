package com.example.auctionserver.application.port.in;

import com.example.auctionserver.application.port.out.model.ResponseAuctionDto;

public interface GetAuctionUseCase {

    ResponseAuctionDto getAuction();
}
