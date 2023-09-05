package com.example.auctionserver.adapter.out.persistence;

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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class AuctionPersistenceAdapterTest {

    @Mock
    private AuctionRepository auctionRepository;
    @InjectMocks
    private AuctionPersistenceAdapter auctionPersistenceAdapter;

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
    @DisplayName("현재 시간으로 조회가 가능한 경매 테스트")
    public void getAuctionTest(){

        //given
        Mockito.when(auctionRepository.findByCurrentTime(any()))
                .thenReturn(Optional.of(auction));

        //when
        Auction currentTimeAuction = auctionPersistenceAdapter.findByCurrentTime(LocalDateTime.now());

        //then
        assertThat(currentTimeAuction.getProductName()).isEqualTo("product");
        assertThat(currentTimeAuction.getWinningPrice()).isEqualTo(2000L);

    }

    @Test
    @DisplayName("현재 시간으로 조회가 불가능한 경매 테스트")
    public void getAuctionExceptionTest() {

        //given
        Mockito.when(auctionRepository.findByCurrentTime(any()))
                .thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> auctionPersistenceAdapter.findByCurrentTime(LocalDateTime.now()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("진행중인 경매가 없습니다");
    }

    @Test
    @DisplayName("입찰 갱신 성공 경매 테스트")
    public void updateAuctionTest() {

        //given
        Mockito.when(auctionRepository.findAuctionById(any()))
                .thenReturn(Optional.of(auction));

        //when
        Auction updatedAuction = auctionPersistenceAdapter.updateAuction(1L, 3000L, LocalDateTime.now(), 2L);

        //then
        assertThat(updatedAuction.getWinningPrice()).isEqualTo(3000L);
        assertThat(updatedAuction.getMemberId()).isEqualTo(2L);
    }

    @Test
    @DisplayName("특정 시간 범위 내 조회되는 경매 테스트")
    public void findByClosingTimeBetweenTest() {

        //given
        LocalDateTime startTime = LocalDateTime.now().minusDays(1L);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1L);
        Mockito.when(auctionRepository.findByClosingTimeBetween(startTime, endTime))
                .thenReturn(Optional.of(auction));

        //When
        Auction closingTimeBetweenAuction = auctionPersistenceAdapter.findByClosingTimeBetween(startTime, endTime);

        //Then
        assertThat(closingTimeBetweenAuction.getProductName()).isEqualTo("product");
        assertThat(closingTimeBetweenAuction.getWinningPrice()).isEqualTo(2000L);
    }

    @Test
    @DisplayName("특정 시간 범위 내 조회가 불가능한 경매 테스트")
    public void findByClosingTimeBetweenExceptionTest() {

        //given
        LocalDateTime startTime = LocalDateTime.now().minusDays(1L);
        LocalDateTime endTime = LocalDateTime.now().plusDays(1L);
        Mockito.when(auctionRepository.findByClosingTimeBetween(any(), any()))
                .thenReturn(Optional.empty());

        //when, then
        assertThatThrownBy(() -> auctionPersistenceAdapter.findByClosingTimeBetween(startTime, endTime))
                .isInstanceOf(RuntimeException.class);
    }

}