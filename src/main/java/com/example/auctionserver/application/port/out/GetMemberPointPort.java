package com.example.auctionserver.application.port.out;

public interface GetMemberPointPort {

    void validatePoint(Long bidPoint, Long memberId);
}
