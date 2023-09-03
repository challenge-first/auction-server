package com.example.auctionserver.application.port.out;

import com.example.auctionserver.application.port.in.model.RequestBidDto;

public interface PublishEventPort {

    void sendBidEvent(Long memberId, RequestBidDto requestBidDto);
    void sendEndEvent(Long memberId, Long point);
}
