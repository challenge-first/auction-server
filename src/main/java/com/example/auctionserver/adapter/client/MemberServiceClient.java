package com.example.auctionserver.adapter.client;

import com.example.auctionserver.domain.dto.response.ResponsePointDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "member-server")
public interface MemberServiceClient {

    @GetMapping("/members/point")
    ResponseEntity<ResponsePointDto> getPoint(@RequestHeader("x-authorization-id") Long memberId);
}
