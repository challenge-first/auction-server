package com.example.auctionserver.adapter.out.persistence;

import com.example.auctionserver.application.port.in.model.RequestAuctionDto;
import com.example.auctionserver.application.port.in.model.RequestBidDto;
import com.example.auctionserver.application.port.out.EndAuctionPort;
import com.example.auctionserver.application.port.out.GetAuctionPort;
import com.example.auctionserver.application.port.out.PostAuctionPort;
import com.example.auctionserver.application.port.out.UpdateWinningPricePort;
import com.example.auctionserver.domain.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class AuctionPersistenceAdapter implements GetAuctionPort, UpdateWinningPricePort, EndAuctionPort, PostAuctionPort {

    private final AuctionRepository auctionRepository;

    @Override
    public Auction findByCurrentTime(LocalDateTime currentTime) {
        return auctionRepository.findByCurrentTime(LocalDateTime.now()).orElseThrow(
                () -> new IllegalArgumentException("진행중인 경매가 없습니다"));
    }

    @Override
    public Auction updateAuction(Long auctionId, RequestBidDto requestBidDto, Long memberId) {
        Auction findAuction = auctionRepository.findAuctionById(auctionId).orElseThrow(
                () -> new IllegalArgumentException("진행중인 경매가 없습니다"));
        findAuction.update(requestBidDto, memberId);
        return findAuction;
    }

    @Override
    public Auction findByClosingTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return auctionRepository.findByClosingTimeBetween(startTime, endTime).orElseThrow();
    }

    @Override
    public void createAuction(RequestAuctionDto requestAuctionDto, Long memberId) {
        Auction createAuction = create(requestAuctionDto, memberId);
        auctionRepository.save(createAuction);
    }

    private Auction create(RequestAuctionDto requestAuctionDto, Long memberId) {
        return Auction.builder()
                .memberId(memberId)
                .productId(requestAuctionDto.getProductId())
                .productName(requestAuctionDto.getProductName())
                .imageUrl(requestAuctionDto.getImageUrl())
                .openingPrice(requestAuctionDto.getOpeningPrice())
                .winningPrice(requestAuctionDto.getOpeningPrice())
                .openingTime(requestAuctionDto.getOpeningTime())
                .closingTime(requestAuctionDto.getClosingTime())
                .build();
    }
}
