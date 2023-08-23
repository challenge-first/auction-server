package com.example.auctionserver.adapter.client;

import com.example.auctionserver.auction.dto.ResponsePointDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "member-server")
public interface MemberServiceClient {

    @GetMapping("/members/{memberId}")
    ResponseEntity<ResponsePointDto> getPoint(@PathVariable Long memberId);
}
