package com.example.auctionserver.application.port.out.model;

import com.example.auctionserver.adapter.in.web.model.RequestBidDto;
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

    public void validatePoint(Long bidPoint) {
        if (bidPoint > this.point) {
            throw new IllegalArgumentException("가지고 있는 포인트보다 많은 금액을 입찰할 수 없습니다");
        }
    }
}
