package com.example.auctionserver.adapter.out.persistence;

import com.example.auctionserver.adapter.in.web.model.RequestAuctionDto;
import com.example.auctionserver.adapter.in.web.model.RequestBidDto;
import com.example.auctionserver.application.port.in.PostAuctionCommand;
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
    public Auction updateAuction(Long auctionId, Long bidPoint, LocalDateTime bidTime, Long memberId) {
        Auction findAuction = auctionRepository.findAuctionById(auctionId).orElseThrow(
                () -> new IllegalArgumentException("진행중인 경매가 없습니다"));
        findAuction.update(bidPoint, bidTime, memberId);
        return findAuction;
    }

    @Override
    public Auction findByClosingTimeBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return auctionRepository.findByClosingTimeBetween(startTime, endTime).orElseThrow();
    }

    @Override
    public void createAuction(PostAuctionCommand postAuctionCommand) {
        Auction createAuction = create(postAuctionCommand);
        auctionRepository.save(createAuction);
    }

    private Auction create(PostAuctionCommand postAuctionCommand) {
        return Auction.builder()
                .memberId(postAuctionCommand.getMemberId())
                .productId(postAuctionCommand.getProductId())
                .productName(postAuctionCommand.getProductName())
                .imageUrl(postAuctionCommand.getImageUrl())
                .openingPrice(postAuctionCommand.getOpeningPrice())
                .winningPrice(postAuctionCommand.getOpeningPrice())
                .openingTime(postAuctionCommand.getOpeningTime())
                .closingTime(postAuctionCommand.getClosingTime())
                .build();
    }
}
