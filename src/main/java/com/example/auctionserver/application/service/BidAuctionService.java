package com.example.auctionserver.application.service;

import com.example.auctionserver.adapter.out.event.BidEventProducer;
import com.example.auctionserver.adapter.out.feign.MemberServiceClient;
import com.example.auctionserver.application.port.in.BidAuctionRequest;
import com.example.auctionserver.application.port.in.BidAuctionUseCase;
import com.example.auctionserver.application.port.out.UpdateWinningPricePort;
import com.example.auctionserver.application.port.out.model.UpdateWinningPriceResponse;
import com.example.auctionserver.domain.Auction;
import com.example.auctionserver.global.dto.request.RequestBidDto;
import com.example.auctionserver.global.dto.response.ResponsePointDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BidAuctionService implements BidAuctionUseCase {

    private final UpdateWinningPricePort updateWinningPricePort;
    private final MemberServiceClient memberServiceClient;
    private final BidEventProducer bidEventProducer;

    @Value("${kafka.topic.bid}")
    private String bidTopic;

    @Override
    @Transactional
    public UpdateWinningPriceResponse bid(Long auctionId, BidAuctionRequest bidAuctionRequest, Long memberId) {

        ResponsePointDto responsePointDto = memberServiceClient.getPoint(memberId).getBody();

        validateAuctionCondition(bidAuctionRequest, responsePointDto);

        Auction updateAuction = updateWinningPricePort.updateAuction(auctionId, bidAuctionRequest, memberId);

        RequestBidDto bidDto = RequestBidDto.builder()
                .memberId(memberId)
                .bid(bidAuctionRequest.getPoint())
                .build();

        bidEventProducer.sendBidDto(bidTopic, bidDto);

        return updateWinningPriceResponse(updateAuction);
    }

    private void validateAuctionCondition(BidAuctionRequest BidAuctionRequest, ResponsePointDto responsePointDto) {
        if (BidAuctionRequest.getPoint() > responsePointDto.getPoint()) {
            throw new IllegalArgumentException("가지고 있는 포인트보다 많은 금액을 입찰할 수 없습니다");
        }
    }

    private UpdateWinningPriceResponse updateWinningPriceResponse(Auction auction) {
        return UpdateWinningPriceResponse.builder()
                .winningPrice(auction.getWinningPrice())
                .build();
    }
}
