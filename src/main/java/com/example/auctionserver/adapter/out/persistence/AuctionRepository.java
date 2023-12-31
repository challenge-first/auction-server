package com.example.auctionserver.adapter.out.persistence;

import com.example.auctionserver.domain.Auction;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuctionRepository extends JpaRepository<Auction, Long>{


    @Query("select a from Auction a where a.closingTime > :current_time and a.openingTime <= :current_time order by a.id desc limit 1")
    Optional<Auction> findByCurrentTime(@Param("current_time") LocalDateTime currentTime );

    Optional<Auction> findByClosingTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Auction> findAuctionById(Long auctionId);

}

