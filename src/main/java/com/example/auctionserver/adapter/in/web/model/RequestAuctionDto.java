package com.example.auctionserver.adapter.in.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class RequestAuctionDto {

    private Long memberId;

    private Long productId;

    private String productName;

    private String imageUrl;

    private Long openingPrice;

    private LocalDateTime openingTime;

    private LocalDateTime closingTime;
}
