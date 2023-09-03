package com.example.auctionserver.application.usecase;

import com.example.auctionserver.application.usecase.model.ResponseAuctionDto;

public interface GetAuctionQuery {

    ResponseAuctionDto getAuction();
}
