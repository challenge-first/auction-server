package com.example.auctionserver.adapter.in.web;

import com.example.auctionserver.application.port.in.BidAuctionRequest;
import com.example.auctionserver.application.port.in.BidAuctionUseCase;
import com.example.auctionserver.application.port.out.model.UpdateWinningPriceResponse;
import com.example.auctionserver.global.auth.LoginMember;
import com.example.auctionserver.global.response.ResponseDataDto;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions")
public class BidAuctionController {

    private final BidAuctionUseCase bidAuctionUseCase;

    @PostMapping("/{auctionId}")
    @Timed(value = "auctions.bid", longTask = true)
    public ResponseEntity<ResponseDataDto> bid(@PathVariable Long auctionId,
                                               @RequestBody BidAuctionRequest bidAuctionRequest,
                                               @LoginMember Long memberId) {

        UpdateWinningPriceResponse updateWinningPriceResponse = bidAuctionUseCase.bid(auctionId, bidAuctionRequest, memberId);
        ResponseDataDto responseDataDto = new ResponseDataDto(updateWinningPriceResponse);
        return ResponseEntity.status(HttpStatus.OK).body(responseDataDto);
    }
}
