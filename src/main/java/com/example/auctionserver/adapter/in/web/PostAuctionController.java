package com.example.auctionserver.adapter.in.web;

import com.example.auctionserver.application.port.in.PostAuctionUseCase;
import com.example.auctionserver.application.port.in.model.RequestAuctionDto;
import com.example.auctionserver.global.auth.LoginMember;
import com.example.auctionserver.global.response.ResponseDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auctions")
public class PostAuctionController {

    private final PostAuctionUseCase postAuctionUseCase;

    @PostMapping
    public ResponseEntity<ResponseDataDto> bid(@RequestBody RequestAuctionDto requestAuctionDto,
                                               @LoginMember Long memberId) {

        ResponseDataDto<String> response = new ResponseDataDto<>(postAuctionUseCase.createAuction(requestAuctionDto, memberId));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
