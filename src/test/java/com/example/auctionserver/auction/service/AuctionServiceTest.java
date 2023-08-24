package com.example.auctionserver.auction.service;

import com.example.auctionserver.adapter.client.MemberServiceClient;
import com.example.auctionserver.adapter.messagequeue.KafkaProducer;
import com.example.auctionserver.auction.dto.request.RequestAuctionDto;
import com.example.auctionserver.auction.dto.response.ResponseAuctionDto;
import com.example.auctionserver.auction.dto.response.ResponsePointDto;
import com.example.auctionserver.auction.dto.response.ResponseWinningPriceDto;
import com.example.auctionserver.auction.entity.Auction;
import com.example.auctionserver.auction.repository.AuctionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuctionServiceTest {

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    @Mock
    private MemberServiceClient memberServiceClient;

    @InjectMocks
    private AuctionService auctionService;

    private ResponsePointDto responsePointDto;

    private ResponsePointDto responsePointDtoSub;

    private Auction auction;


    @BeforeEach
    public void beforeEach() {

        auction = Auction.builder()
                .id(1L)
                .productId(1L)
                .productName("product")
                .imageUrl("image")
                .memberId(1L)
                .openingPrice(3000L)
                .openingTime(LocalDateTime.now().withHour(15))
                .closingTime(LocalDateTime.now().withHour(16).withMinute(59))
                .winningPrice(6000L)
                .build();

        responsePointDto = ResponsePointDto.builder()
                .point(30000L)
                .availablePoint(30000L)
                .deposit(0L)
                .build();

        responsePointDtoSub = ResponsePointDto.builder()
                .point(40000L)
                .availablePoint(40000L)
                .deposit(0L)
                .build();
    }

    @Test
    @DisplayName("경매 정보 조회")
    void getAuctionTest() throws Exception {
        //given

        when(auctionRepository.findByCurrentTime(any()))
                .thenReturn(Optional.of(auction));

        //when
        ResponseAuctionDto responseDto = auctionService.getAuction();
        //then
        assertThat(responseDto.getProductName()).isEqualTo("product");

    }

    @Test
    @DisplayName("입찰 성공 테스트1")
    public void bidSuccess1() {

        when(memberServiceClient.getPoint(any()))
                .thenReturn(ResponseEntity.ok(responsePointDto));

        doNothing().when(kafkaProducer).sendBidDto(any(), any());

        when(auctionRepository.findById(any()))
                .thenReturn(Optional.of(auction));

        RequestAuctionDto request = new RequestAuctionDto(12000L, LocalDateTime.now().withHour(15).withMinute(10));

        ResponseWinningPriceDto response = auctionService.bid(1L, request, 1L);

        Assertions.assertThat(response.getWinningPrice()).isEqualTo(12000L);
    }


    @Test
    @DisplayName("현재 입찰가 실패시 발생하는 예외 테스트")
    public void bidFailCausedByWinningPrice() {

        when(auctionRepository.findById(any()))
                .thenReturn(Optional.of(auction));

        when(memberServiceClient.getPoint(any()))
                .thenReturn(ResponseEntity.ok(responsePointDto));

        doNothing().when(kafkaProducer).sendBidDto(any(), any());

        RequestAuctionDto request = new RequestAuctionDto(5000L, LocalDateTime.now().withHour(16));

        assertThatThrownBy(() -> auctionService.bid(1L, request, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("현재 입찰가보다 부족한 입찰 금액입니다");
    }

    @Test
    @DisplayName("기본 입찰가 실패시 발생하는 예외 테스트")
    public void bidFailCausedByOpeningPrice() {

        when(auctionRepository.findById(any()))
                .thenReturn(Optional.of(auction));

        when(memberServiceClient.getPoint(any()))
                .thenReturn(ResponseEntity.ok(responsePointDto));

        doNothing().when(kafkaProducer).sendBidDto(any(), any());

        RequestAuctionDto request = new RequestAuctionDto(2000L, LocalDateTime.now().withHour(16));

        assertThatThrownBy(() -> auctionService.bid(1L, request, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기본 입찰가보다 부족한 입찰 금액입니다");
    }

    @Test
    @DisplayName("마감시간 초과시 발생하는 예외 테스트")
    public void bidFailCausedByClosingTime() {

        when(auctionRepository.findById(any()))
                .thenReturn(Optional.of(auction));

        when(memberServiceClient.getPoint(any()))
                .thenReturn(ResponseEntity.ok(responsePointDto));

        doNothing().when(kafkaProducer).sendBidDto(any(), any());


        RequestAuctionDto request = new RequestAuctionDto(10000L, LocalDateTime.now().withHour(17));

        assertThatThrownBy(() -> auctionService.bid(1L, request, 1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("경매가 종료되었습니다");
    }

    @Test
    @DisplayName("보유 포인트 부족시 발생하는 예외 테스트")
    public void bidFailCausedByPoint() {

        when(auctionRepository.findById(any()))
                .thenReturn(Optional.of(auction));

        when(memberServiceClient.getPoint(any()))
                .thenReturn(ResponseEntity.ok(responsePointDto));

        doNothing().when(kafkaProducer).sendBidDto(any(), any());


        RequestAuctionDto request = new RequestAuctionDto(40000L, LocalDateTime.now().withHour(16));

        assertThatThrownBy(() -> auctionService.bid(1L, request, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("가지고 있는 포인트보다 많은 금액을 입찰할 수 없습니다");
    }

    @Test
    @DisplayName("입찰 경쟁 및 예치금 초기화 테스트")
    public void bidCompetitionAndInitDeposit() {

        when(auctionRepository.findById(any()))
                .thenReturn(Optional.of(auction));

        when(memberServiceClient.getPoint(1L))
                .thenReturn(ResponseEntity.ok(responsePointDto));

        when(memberServiceClient.getPoint(2L))
                .thenReturn(ResponseEntity.ok(responsePointDtoSub));

        doNothing().when(kafkaProducer).sendBidDto(any(), any());

        RequestAuctionDto request1 = new RequestAuctionDto(12000L, LocalDateTime.now().withHour(15));
        RequestAuctionDto request2 = new RequestAuctionDto(20000L, LocalDateTime.now().withHour(15));

        auctionService.bid(1L, request1, 1L);
        auctionService.bid(1L, request2, 2L);

        Assertions.assertThat(auction.getMemberId()).isEqualTo(2L);
    }
}