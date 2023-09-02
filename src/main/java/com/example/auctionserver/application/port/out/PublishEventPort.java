package com.example.auctionserver.application.port.out;

import com.example.auctionserver.application.port.in.model.BidAuctionRequest;
import com.example.auctionserver.domain.Auction;

public interface PublishEventPort {

    void sendBidEvent(Long memberId, BidAuctionRequest bidAuctionRequest);

    void sendEndEvent(Auction auction);
}
