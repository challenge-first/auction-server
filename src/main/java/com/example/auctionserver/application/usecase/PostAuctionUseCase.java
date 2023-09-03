package com.example.auctionserver.application.usecase;

import com.example.auctionserver.application.port.in.PostAuctionCommand;

public interface PostAuctionUseCase {

    String createAuction(PostAuctionCommand postAuctionCommand);
}
