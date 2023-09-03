package com.example.auctionserver.application.port.out;

import com.example.auctionserver.application.port.in.model.RequestBidDto;
import com.example.auctionserver.domain.Auction;

public interface PublishEventPort {

    void sendBidEvent(Long memberId, RequestBidDto bidAuctionRequest);
    void sendEndEvent(Auction auction);
}
