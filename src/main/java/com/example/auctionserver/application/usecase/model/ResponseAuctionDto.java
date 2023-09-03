package com.example.auctionserver.application.usecase.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class ResponseAuctionDto {

    private Long id;
    private String productName;
    private String imageUrl;
    private Long openingPrice;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private Long winningPrice;
}
