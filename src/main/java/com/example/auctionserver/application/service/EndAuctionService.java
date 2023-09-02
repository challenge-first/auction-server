package com.example.auctionserver.application.service;

import com.example.auctionserver.adapter.out.event.BidEventProducer;
import com.example.auctionserver.adapter.out.persistence.AuctionRepository;
import com.example.auctionserver.domain.Auction;
import com.example.auctionserver.global.dto.request.RequestBidDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EndAuctionService {

    private final AuctionRepository auctionRepository;
    private final BidEventProducer bidEventProducer;

    @Value("${kafka.topic.auction-end}")
    private String auctionEndTopic;

    @Scheduled(cron = "1 59 16 * * *")
    @Transactional(readOnly = true)
    public void checkAndCloseAuctions() {

        Auction auction = auctionRepository.findByClosingTimeBetween(LocalDateTime.now().withHour(15), LocalDateTime.now().withHour(16).withMinute(59)).orElseThrow();

        RequestBidDto requestBidDto = RequestBidDto.builder()
                .memberId(auction.getMemberId())
                .bid(auction.getWinningPrice())
                .build();

        bidEventProducer.sendBidDto(auctionEndTopic, requestBidDto);
    }
}
