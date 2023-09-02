package com.example.auctionserver.global.dto.response;

import com.example.auctionserver.application.port.in.model.BidAuctionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.*;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Slf4j
public class ResponsePointDto {

    private Long point;
    private Long deposit;
    private Long availablePoint;

    public void validatePoint(BidAuctionRequest bidAuctionRequest) {
        if (bidAuctionRequest.getPoint() > this.point) {
            log.info("bid point: {}",bidAuctionRequest.getPoint());
            log.info("poin: {}", this.point);
            throw new IllegalArgumentException("가지고 있는 포인트보다 많은 금액을 입찰할 수 없습니다");
        }
    }
}
