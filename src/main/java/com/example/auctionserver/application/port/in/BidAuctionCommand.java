package com.example.auctionserver.application.port.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class BidAuctionCommand {

    @NotNull
    private Long auctionId;

    @NotNull
    @Min(0)
    private Long bidPoint;

    @NotNull
    private Long memberId;

    @NotNull
    private LocalDateTime bidTime;
}
