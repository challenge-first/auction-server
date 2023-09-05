package com.example.auctionserver.application.port.out;

public interface PublishEventPort {

    void sendBidEvent(Long bidPoint, Long memberId);
    void sendEndEvent(Long bidPoint, Long memberId);
}
