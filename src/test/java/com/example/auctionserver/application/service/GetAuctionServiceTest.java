package com.example.auctionserver.application.service;

import com.example.auctionserver.application.port.out.GetAuctionPort;
import com.example.auctionserver.application.usecase.model.ResponseAuctionDto;
import com.example.auctionserver.domain.Auction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class GetAuctionServiceTest {

    @Mock
    private GetAuctionPort getAuctionPort;

    @InjectMocks
    private GetAuctionService getAuctionService;

    private Auction auction;

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
    }

    @Test
    @DisplayName("경매 조회 테스트")
    public void getAuctionTest(){

        //given
        Mockito.when(getAuctionPort.findByCurrentTime(any()))
                .thenReturn(auction);

        //when
        ResponseAuctionDto responseAuctionDto = getAuctionService.getAuction();

        //then
        assertThat(responseAuctionDto.getProductName()).isEqualTo("product");
        assertThat(responseAuctionDto.getWinningPrice()).isEqualTo(2000L);

    }
}