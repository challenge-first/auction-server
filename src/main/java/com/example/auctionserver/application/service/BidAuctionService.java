package com.example.auctionserver.application.service;

import com.example.auctionserver.application.port.in.BidAuctionUseCase;
import com.example.auctionserver.application.port.in.model.BidAuctionRequest;
import com.example.auctionserver.application.port.out.GetMemberPointPort;
import com.example.auctionserver.application.port.out.PublishEventPort;
import com.example.auctionserver.application.port.out.UpdateWinningPricePort;
import com.example.auctionserver.application.port.out.model.UpdateWinningPriceResponse;
import com.example.auctionserver.domain.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BidAuctionService implements BidAuctionUseCase {

    private final UpdateWinningPricePort updateWinningPricePort;
    private final GetMemberPointPort getMemberPointPort;
    private final PublishEventPort publishEventPort;

    @Override
    @Transactional
    public UpdateWinningPriceResponse bid(Long auctionId, BidAuctionRequest bidAuctionRequest, Long memberId) {

        getMemberPointPort.validatePoint(memberId, bidAuctionRequest);

        Auction updateAuction = updateWinningPricePort.updateAuction(auctionId, bidAuctionRequest, memberId);

        publishEventPort.sendBidEvent(memberId, bidAuctionRequest);

        return updateWinningPriceResponse(updateAuction);
    }

    private UpdateWinningPriceResponse updateWinningPriceResponse(Auction auction) {
        return UpdateWinningPriceResponse.builder()
                .winningPrice(auction.getWinningPrice())
                .build();
    }
}
