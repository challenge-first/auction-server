package com.example.auctionserver.adapter.in.web;

import com.example.auctionserver.application.usecase.GetAuctionQuery;
import com.example.auctionserver.application.usecase.model.ResponseAuctionDto;
import com.example.auctionserver.global.response.ResponseDataDto;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions")
public class GetAuctionController {

    private final GetAuctionQuery getAuctionQuery;

    @GetMapping
    @Timed(value = "auctions.getAuction", longTask = true)
    public ResponseEntity<ResponseDataDto> getAuction() {

        ResponseDataDto<ResponseAuctionDto> response = new ResponseDataDto<>(getAuctionQuery.getAuction());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
