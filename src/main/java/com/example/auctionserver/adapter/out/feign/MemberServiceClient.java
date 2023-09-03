package com.example.auctionserver.adapter.out.feign;

import com.example.auctionserver.application.port.in.model.RequestBidDto;
import com.example.auctionserver.application.port.out.GetMemberPointPort;
import com.example.auctionserver.application.port.out.model.ResponsePointDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "member-server")
public interface MemberServiceClient extends GetMemberPointPort {


    @GetMapping("/members/point")
    ResponseEntity<ResponsePointDto> getPoint(@RequestHeader("x-authorization-id") Long memberId);

    @Override
    default void validatePoint(Long memberId, RequestBidDto requestBidDto) {
        this.getPoint(memberId).getBody().validatePoint(requestBidDto);
    }
}
