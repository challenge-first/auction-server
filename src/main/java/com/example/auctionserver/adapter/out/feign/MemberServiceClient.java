package com.example.auctionserver.adapter.out.feign;

import com.example.auctionserver.application.port.out.GetMemberPointPort;
import com.example.auctionserver.application.port.out.model.ResponsePointDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "member-server", fallback = MemberServerClientFallback.class)
@Primary
public interface MemberServiceClient extends GetMemberPointPort {

    @GetMapping("/members/point")
    ResponseEntity<ResponsePointDto> getPoint(@RequestHeader("x-authorization-id") Long memberId);

    @Override
    default void validatePoint(Long bidPoint, Long memberId) {
        this.getPoint(memberId).getBody().validatePoint(bidPoint);
    }
}
