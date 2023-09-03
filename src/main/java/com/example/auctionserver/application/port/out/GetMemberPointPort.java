package com.example.auctionserver.application.port.out;

import com.example.auctionserver.application.port.in.model.RequestBidDto;

public interface GetMemberPointPort {

    void validatePoint(Long memberId, RequestBidDto requestBidDto);
}
