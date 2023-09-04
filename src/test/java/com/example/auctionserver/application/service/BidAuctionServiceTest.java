package com.example.auctionserver.application.service;

import com.example.auctionserver.application.port.in.BidAuctionCommand;
import com.example.auctionserver.application.port.out.GetMemberPointPort;
import com.example.auctionserver.application.port.out.PublishEventPort;
import com.example.auctionserver.application.port.out.UpdateWinningPricePort;
import com.example.auctionserver.application.usecase.model.ResponseBidDto;
import com.example.auctionserver.domain.Auction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BidAuctionServiceTest {

    @Mock
    private UpdateWinningPricePort updateWinningPricePort;

    @Mock
    private GetMemberPointPort getMemberPointPort;

    @Mock
    private PublishEventPort publishEventPort;

    private Auction auction;

    private BidAuctionCommand bidAuctionCommand;

    @InjectMocks
    private BidAuctionService bidAuctionService;

    @BeforeEach
    public void init() {
        auction = Auction.builder()
                .id(1L)
                .memberId(1L)
                .productId(1L)
                .productName("product")
                .imageUrl("image")
                .openingPrice(1000L)
                .winningPrice(2000L)
                .openingTime(LocalDateTime.now().minusDays(1L))
                .closingTime(LocalDateTime.now().plusDays(1L))
                .build();

        bidAuctionCommand = BidAuctionCommand.builder()
                .auctionId(1L)
                .memberId(1L)
                .bidPoint(2000L)
                .bidTime(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("경매 입찰 테스트")
    public void bidSuccess() {

        //given
        doNothing().when(getMemberPointPort).validatePoint(any(), any());

        when(updateWinningPricePort.updateAuction(any(), any(), any(), any()))
                .thenReturn(auction);

        doNothing().when(publishEventPort).sendBidEvent(any(), any());

        //when
        ResponseBidDto responseBidDto = bidAuctionService.bid(bidAuctionCommand);

        //then
        assertThat(responseBidDto.getWinningPrice()).isEqualTo(2000L);
    }

}