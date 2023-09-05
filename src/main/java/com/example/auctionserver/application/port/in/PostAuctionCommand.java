package com.example.auctionserver.application.port.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class PostAuctionCommand {

    @NotNull
    private Long memberId;

    @NotNull
    private Long productId;

    @NotNull
    @NotBlank
    private String productName;

    @NotNull
    @NotBlank
    private String imageUrl;

    @NotNull
    @Min(0)
    private Long openingPrice;

    @NotNull
    private LocalDateTime openingTime;

    @NotNull
    private LocalDateTime closingTime;
}
