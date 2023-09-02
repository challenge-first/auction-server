package com.example.auctionserver.adapter.in.web;

import com.example.auctionserver.application.port.in.GetAuctionUseCase;
import com.example.auctionserver.application.port.out.model.GetAuctionResponse;
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

    private final GetAuctionUseCase getAuctionUseCase;

    @GetMapping
    @Timed(value = "auctions.getAuction", longTask = true)
    public ResponseEntity<ResponseDataDto> getAuction() {

        ResponseDataDto<GetAuctionResponse> response = new ResponseDataDto<>(getAuctionUseCase.getAuction());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
