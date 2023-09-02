package com.example.auctionserver.application.service;

import com.example.auctionserver.application.port.in.GetAuctionUseCase;
import com.example.auctionserver.application.port.out.GetAuctionPort;
import com.example.auctionserver.application.port.out.model.GetAuctionResponse;
import com.example.auctionserver.domain.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GetAuctionService implements GetAuctionUseCase {

    private final GetAuctionPort getAuctionPort;

    @Override
    public GetAuctionResponse getAuction() {

        Auction auction = getAuctionPort.findByCurrentTime(LocalDateTime.now());

        GetAuctionResponse getAuctionResponse = GetAuctionResponse.builder()
                .id(auction.getId())
                .productName(auction.getProductName())
                .winningPrice(auction.getWinningPrice())
                .openingPrice(auction.getOpeningPrice())
                .openingTime(auction.getOpeningTime())
                .imageUrl(auction.getImageUrl())
                .closingTime(auction.getClosingTime())
                .build();

        return getAuctionResponse;
    }
}
