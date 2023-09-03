package com.example.auctionserver.application.service;

import com.example.auctionserver.application.usecase.GetAuctionQuery;
import com.example.auctionserver.application.port.out.GetAuctionPort;
import com.example.auctionserver.application.usecase.model.ResponseAuctionDto;
import com.example.auctionserver.domain.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAuctionService implements GetAuctionQuery {

    private final GetAuctionPort getAuctionPort;

    @Override
    public ResponseAuctionDto getAuction() {

        Auction auction = getAuctionPort.findByCurrentTime(LocalDateTime.now());

        return createResponseAuctionDto(auction);
    }

    private ResponseAuctionDto createResponseAuctionDto(Auction auction) {
        return ResponseAuctionDto.builder()
                .id(auction.getId())
                .productName(auction.getProductName())
                .winningPrice(auction.getWinningPrice())
                .openingPrice(auction.getOpeningPrice())
                .openingTime(auction.getOpeningTime())
                .imageUrl(auction.getImageUrl())
                .closingTime(auction.getClosingTime())
                .build();
    }
}
