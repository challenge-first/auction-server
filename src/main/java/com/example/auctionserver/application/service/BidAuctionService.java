package com.example.auctionserver.application.service;

import com.example.auctionserver.application.port.in.BidAuctionCommand;
import com.example.auctionserver.application.usecase.BidAuctionUseCase;
import com.example.auctionserver.application.port.out.GetMemberPointPort;
import com.example.auctionserver.application.port.out.PublishEventPort;
import com.example.auctionserver.application.port.out.UpdateWinningPricePort;
import com.example.auctionserver.application.usecase.model.ResponseBidDto;
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
    public ResponseBidDto bid(BidAuctionCommand bidAuctionCommand) {

        getMemberPointPort.validatePoint(bidAuctionCommand.getBidPoint(), bidAuctionCommand.getMemberId());

        Auction updateAuction = updateWinningPricePort.updateAuction(bidAuctionCommand.getAuctionId(), bidAuctionCommand.getBidPoint(), bidAuctionCommand.getBidTime(), bidAuctionCommand.getMemberId());

        publishEventPort.sendBidEvent(bidAuctionCommand.getBidPoint(), bidAuctionCommand.getMemberId());

        return createResponseBidDto(updateAuction);
    }

    private ResponseBidDto createResponseBidDto(Auction auction) {
        return ResponseBidDto.builder()
                .winningPrice(auction.getWinningPrice())
                .build();
    }
}
