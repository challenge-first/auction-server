package com.example.auctionserver.domain;

import com.example.auctionserver.application.port.in.model.RequestBidDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;


@Entity
@Table(name = "auctions")
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Auction {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "auction_id", nullable = false)
    private Long id;

    private Long memberId;

    @Column(nullable = false)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Long openingPrice;

    @Column(nullable = false)
    private Long winningPrice;

    @Column(nullable = false)
    private LocalDateTime openingTime;

    @Column(nullable = false)
    private LocalDateTime closingTime;

    public void update(RequestBidDto requestBidDto, Long memberId) {

        if (requestBidDto.getPoint() < this.openingPrice) {
            throw new IllegalArgumentException("기본 입찰가보다 부족한 입찰 금액입니다");
        }

        if (requestBidDto.getPoint() <= this.winningPrice) {
            throw new IllegalArgumentException("현재 입찰가보다 부족한 입찰 금액입니다");
        }

        if (requestBidDto.getTime().isAfter(this.closingTime)) {
            throw new IllegalStateException("경매가 종료되었습니다");
        }

        this.winningPrice = requestBidDto.getPoint();
        this.memberId = memberId;
    }
}
