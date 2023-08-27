package com.example.auctionserver.auction.controller;

import com.example.auctionserver.auction.dto.request.RequestAuctionDto;
import com.example.auctionserver.auction.dto.response.ResponseAuctionDto;
import com.example.auctionserver.auction.dto.response.ResponseWinningPriceDto;
import com.example.auctionserver.auction.service.AuctionService;
import com.example.auctionserver.global.auth.LoginMember;
import com.example.auctionserver.global.response.ResponseDataDto;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions")
@Slf4j
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping
    @Timed(value = "auctions.getAuction", longTask = true)
    public ResponseEntity<ResponseDataDto> getAuction() {

        ResponseDataDto<ResponseAuctionDto> response = new ResponseDataDto<>(auctionService.getAuction());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{auctionId}")
    @Timed(value = "auctions.bid", longTask = true)
    public ResponseEntity<ResponseDataDto> bid(@PathVariable Long auctionId,
                                               @RequestBody RequestAuctionDto requestAuctionDto,
                                               @LoginMember Long memberId) {

        ResponseWinningPriceDto responseWinningPriceDto = auctionService.bid(auctionId, requestAuctionDto, memberId);
        ResponseDataDto responseDataDto = new ResponseDataDto(responseWinningPriceDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDataDto);
    }
}
