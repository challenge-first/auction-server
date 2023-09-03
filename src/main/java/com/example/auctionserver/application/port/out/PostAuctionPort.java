package com.example.auctionserver.application.port.out;

import com.example.auctionserver.application.port.in.model.RequestAuctionDto;

public interface PostAuctionPort {

    void createAuction(RequestAuctionDto requestAuctionDto, Long memberId);
}
