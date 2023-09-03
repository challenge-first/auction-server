package com.example.auctionserver.adapter.out.feign;

import com.example.auctionserver.application.port.out.GetMemberPointPort;
import com.example.auctionserver.application.port.out.model.ResponsePointDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "member-server")
public interface MemberServiceClient extends GetMemberPointPort {

    @GetMapping("/members/point")
    ResponseEntity<ResponsePointDto> getPoint(@RequestHeader("x-authorization-id") Long memberId);

    @Override
    default void validatePoint(Long bidPoint, Long memberId) {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                throw new IllegalArgumentException("CircuitBreakerException");
            }
        }
        this.getPoint(memberId).getBody().validatePoint(bidPoint);
    }

    default void fallback() {
        throw new IllegalStateException("회원 정보를 불러올 수 없습니다. 잠시 후 다시 시도해주세요");
    }

}
