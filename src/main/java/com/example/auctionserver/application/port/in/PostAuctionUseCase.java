package com.example.auctionserver.application.port.in;

import com.example.auctionserver.application.port.in.model.RequestAuctionDto;

public interface PostAuctionUseCase {

    String createAuction(RequestAuctionDto requestAuctionDto, Long memberId);
}
