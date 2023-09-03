package com.example.auctionserver.application.service;

import com.example.auctionserver.application.port.in.PostAuctionUseCase;
import com.example.auctionserver.application.port.in.model.RequestAuctionDto;
import com.example.auctionserver.application.port.out.PostAuctionPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostAuctionService implements PostAuctionUseCase {

    private final PostAuctionPort postAuctionPort;

    @Override
    @Transactional
    public String createAuction(RequestAuctionDto requestAuctionDto, Long memberId) {

        postAuctionPort.createAuction(requestAuctionDto, memberId);

        return "등록완료";
    }
}
