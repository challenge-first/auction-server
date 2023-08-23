package com.example.auctionserver.auction.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class ResponsePointDto {

    private Long point;
    private Long deposit;
    private Long availablePoint;
}
