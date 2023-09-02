package com.example.auctionserver.application.port.out;

import com.example.auctionserver.application.port.in.model.BidAuctionRequest;

public interface GetMemberPointPort {

    void validatePoint(Long memberId, BidAuctionRequest bidAuctionRequest);
}
