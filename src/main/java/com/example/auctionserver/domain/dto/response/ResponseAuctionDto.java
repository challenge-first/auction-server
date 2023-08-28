package com.example.auctionserver.domain.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseAuctionDto {
    private Long id;
    private String productName;
    private String imageUrl;
    private Long openingPrice;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private Long winningPrice;
}