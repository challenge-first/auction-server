package com.example.auctionserver.application.service;

import com.example.auctionserver.application.port.in.PostAuctionCommand;
import com.example.auctionserver.application.port.out.PostAuctionPort;
import com.example.auctionserver.application.usecase.PostAuctionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostAuctionService implements PostAuctionUseCase {

    private final PostAuctionPort postAuctionPort;

    @Override
    @Transactional
    public String createAuction(PostAuctionCommand postAuctionCommand) {

        postAuctionPort.createAuction(postAuctionCommand);

        return "등록완료";
    }
}
