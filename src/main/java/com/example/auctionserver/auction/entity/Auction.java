package com.example.auctionserver.auction.entity;

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

    public void update (Long winningPrice, Long memberId) {

        this.winningPrice = winningPrice;
        this.memberId = memberId;
    }
}
