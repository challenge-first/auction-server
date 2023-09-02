package com.example.auctionserver.adapter.out.persistence;

import com.example.auctionserver.application.port.in.BidAuctionRequest;
import com.example.auctionserver.application.port.out.GetAuctionPort;
import com.example.auctionserver.application.port.out.UpdateWinningPricePort;
import com.example.auctionserver.domain.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class AuctionPersistenceAdapter implements GetAuctionPort, UpdateWinningPricePort {

    private final AuctionRepository auctionRepository;

    @Override
    public Auction findByCurrentTime(LocalDateTime currentTime) {
        return auctionRepository.findByCurrentTime(LocalDateTime.now()).orElseThrow(
                () -> new IllegalArgumentException("진행중인 경매가 없습니다"));
    }

    @Override
    public Auction updateAuction(Long auctionId, BidAuctionRequest bidAuctionRequest, Long memberId) {
        Auction findAuction = auctionRepository.findAuctionById(auctionId).orElseThrow(
                () -> new IllegalArgumentException("진행중인 경매가 없습니다"));
        findAuction.update(bidAuctionRequest, memberId);
        return findAuction;
    }
}
