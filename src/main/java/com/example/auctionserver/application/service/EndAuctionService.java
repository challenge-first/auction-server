package com.example.auctionserver.application.service;

import com.example.auctionserver.application.port.out.EndAuctionPort;
import com.example.auctionserver.application.port.out.PublishEventPort;
import com.example.auctionserver.domain.Auction;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EndAuctionService {

    private final EndAuctionPort endAuctionPort;
    private final PublishEventPort publishEventPort;

    @Scheduled(cron = "1 59 16 * * *")
    @Transactional(readOnly = true)
    public void checkAndCloseAuction() {

        Auction auction = endAuctionPort.findByClosingTimeBetween(LocalDateTime.now().withHour(15), LocalDateTime.now().withHour(16).withMinute(59));

        publishEventPort.sendEndEvent(auction.getWinningPrice(), auction.getMemberId());
    }
}
