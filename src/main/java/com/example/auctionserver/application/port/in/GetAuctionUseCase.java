package com.example.auctionserver.application.port.in;

import com.example.auctionserver.application.port.out.model.GetAuctionResponse;

public interface GetAuctionUseCase {

    GetAuctionResponse getAuction();
}
