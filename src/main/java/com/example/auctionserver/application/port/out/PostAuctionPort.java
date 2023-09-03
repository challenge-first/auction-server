package com.example.auctionserver.application.port.out;

import com.example.auctionserver.application.port.in.PostAuctionCommand;

public interface PostAuctionPort {

    void createAuction(PostAuctionCommand postAuctionCommand);
}
