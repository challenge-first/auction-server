package com.example.auctionserver.auction.controller;

import com.example.auctionserver.auction.dto.RequestAuctionDto;
import com.example.auctionserver.auction.dto.ResponseAuctionDto;
import com.example.auctionserver.auction.dto.ResponseWinningPriceDto;
import com.example.auctionserver.global.auth.LoginMember;
import com.example.auctionserver.global.response.ResponseDataDto;
import com.example.auctionserver.auction.service.AuctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    @GetMapping
    public ResponseEntity<ResponseDataDto> getAuction() {

        ResponseDataDto<ResponseAuctionDto> response = new ResponseDataDto<>(auctionService.getAuction());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{auctionId}")
    public ResponseEntity<ResponseDataDto> bid(@PathVariable Long auctionId,
                                               @RequestBody RequestAuctionDto requestAuctionDto,
                                               @LoginMember Long memberId) {

        ResponseWinningPriceDto responseWinningPriceDto = auctionService.bid(auctionId, requestAuctionDto, memberId);
        ResponseDataDto responseDataDto = new ResponseDataDto(responseWinningPriceDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDataDto);
    }
}
