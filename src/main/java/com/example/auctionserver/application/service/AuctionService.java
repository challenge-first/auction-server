package com.example.auctionserver.application.service;

import com.example.auctionserver.adapter.out.feign.MemberServiceClient;
import com.example.auctionserver.adapter.out.event.BidEventProducer;
import com.example.auctionserver.global.dto.request.RequestAuctionDto;
import com.example.auctionserver.global.dto.request.RequestBidDto;
import com.example.auctionserver.global.dto.response.ResponseAuctionDto;
import com.example.auctionserver.global.dto.response.ResponsePointDto;
import com.example.auctionserver.global.dto.response.ResponseWinningPriceDto;
import com.example.auctionserver.domain.Auction;
import com.example.auctionserver.adapter.out.persistence.AuctionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final MemberServiceClient memberServiceClient;
    private final BidEventProducer kafkaProducer;

    @Value("${kafka.topic.bid}")
    private String bidTopic;

    @Value("${kafka.topic.auction-end}")
    private String auctionEndTopic;

    public ResponseAuctionDto getAuction() {

        Auction auction = auctionRepository.findByCurrentTime(LocalDateTime.now()).orElseThrow(
                () -> new IllegalArgumentException("현재 진행중인 경매가 없습니다."));

        ResponseAuctionDto responseDto = ResponseAuctionDto.builder()
                .id(auction.getId())
                .productName(auction.getProductName())
                .winningPrice(auction.getWinningPrice())
                .openingPrice(auction.getOpeningPrice())
                .openingTime(auction.getOpeningTime())
                .imageUrl(auction.getImageUrl())
                .closingTime(auction.getClosingTime())
                .build();

        return responseDto;
    }

    @Transactional
    public ResponseWinningPriceDto bid(Long auctionId, RequestAuctionDto requestAuctionDto, Long memberId) {

        Auction findAuction = auctionRepository.findAuctionById(auctionId).orElseThrow(() -> new IllegalArgumentException("진행중인 경매가 없습니다"));

        RequestBidDto bidDto = RequestBidDto.builder()
                .memberId(memberId)
                .bid(requestAuctionDto.getPoint())
                .build();

        kafkaProducer.sendBidDto(bidTopic, bidDto);

        ResponsePointDto responsePointDto = memberServiceClient.getPoint(memberId).getBody();

        validateAuctionCondition(requestAuctionDto, findAuction, responsePointDto);

        findAuction.update(requestAuctionDto.getPoint(), memberId);

        return createResponseWinningPriceDto(findAuction);
    }

    private void validateAuctionCondition(RequestAuctionDto requestAuctionDto, Auction auction, ResponsePointDto responsePointDto) {
        if (requestAuctionDto.getPoint() > responsePointDto.getPoint()) {
            throw new IllegalArgumentException("가지고 있는 포인트보다 많은 금액을 입찰할 수 없습니다");
        }

        if (requestAuctionDto.getPoint() < auction.getOpeningPrice()) {
            throw new IllegalArgumentException("기본 입찰가보다 부족한 입찰 금액입니다");
        }

        if (requestAuctionDto.getPoint() <= auction.getWinningPrice()) {
            throw new IllegalArgumentException("현재 입찰가보다 부족한 입찰 금액입니다");
        }

        if (requestAuctionDto.getTime().isAfter(auction.getClosingTime())) {
            throw new IllegalStateException("경매가 종료되었습니다");
        }
    }

    private ResponseWinningPriceDto createResponseWinningPriceDto(Auction auction) {
        return ResponseWinningPriceDto.builder()
                .winningPrice(auction.getWinningPrice())
                .build();
    }

    @Scheduled(cron = "1 59 16 * * *")
    @Transactional(readOnly = true)
    public void checkAndCloseAuctions() {

        Auction auction = auctionRepository.findByClosingTimeBetween(LocalDateTime.now().withHour(15), LocalDateTime.now().withHour(16).withMinute(59)).orElseThrow();

        RequestBidDto requestBidDto = RequestBidDto.builder()
                .memberId(auction.getMemberId())
                .bid(auction.getWinningPrice())
                .build();

        kafkaProducer.sendBidDto(auctionEndTopic, requestBidDto);
    }
}
