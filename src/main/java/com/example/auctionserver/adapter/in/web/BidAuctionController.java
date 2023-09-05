package com.example.auctionserver.adapter.in.web;

import com.example.auctionserver.application.port.in.BidAuctionCommand;
import com.example.auctionserver.application.usecase.BidAuctionUseCase;
import com.example.auctionserver.adapter.in.web.model.RequestBidDto;
import com.example.auctionserver.application.usecase.model.ResponseBidDto;
import com.example.auctionserver.global.auth.LoginMember;
import com.example.auctionserver.global.response.ResponseDataDto;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions")
public class BidAuctionController {

    private final BidAuctionUseCase bidAuctionUseCase;

    @PostMapping("/{auctionId}")
    @Timed(value = "auctions.bid", longTask = true)
    public ResponseEntity<ResponseDataDto> bid(@PathVariable Long auctionId,
                                               @RequestBody RequestBidDto requestBidDto,
                                               @LoginMember Long memberId) {

        BidAuctionCommand bidAuctionCommand = BidAuctionCommand.builder()
                .auctionId(auctionId)
                .bidPoint(requestBidDto.getPoint())
                .memberId(memberId)
                .bidTime(LocalDateTime.now())
                .build();

        ResponseDataDto<ResponseBidDto> response = new ResponseDataDto<>(bidAuctionUseCase.bid(bidAuctionCommand));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
