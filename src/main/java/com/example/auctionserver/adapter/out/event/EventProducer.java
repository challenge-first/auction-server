package com.example.auctionserver.adapter.out.event;

import com.example.auctionserver.application.port.out.PublishEventPort;
import com.example.auctionserver.application.port.out.model.BidEventModel;
import com.example.auctionserver.domain.Auction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventProducer implements PublishEventPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.bid}")
    private String bidTopic;

    @Value("${kafka.topic.auction-end}")
    private String auctionEndTopic;

    @Override
    public void sendBidEvent(Long memberId, com.example.auctionserver.application.port.in.model.RequestBidDto bidAuctionRequest) {

        BidEventModel publishBidEventRequest = BidEventModel.builder()
                .memberId(memberId)
                .bid(bidAuctionRequest.getPoint())
                .build();

        String jsonInString = "";

        try {
            jsonInString = objectMapper.writeValueAsString(publishBidEventRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(bidTopic, jsonInString);
        log.info("memberId:{}, point:{}", publishBidEventRequest.getMemberId(), publishBidEventRequest.getBid());
    }

    @Override
    public void sendEndEvent(Auction auction) {

        BidEventModel publishBidEventRequest = BidEventModel.builder()
                .memberId(auction.getMemberId())
                .bid(auction.getWinningPrice())
                .build();

        String jsonInString = "";

        try {
            jsonInString = objectMapper.writeValueAsString(publishBidEventRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(auctionEndTopic, jsonInString);
        log.info("memberId:{}, point:{}", publishBidEventRequest.getMemberId(), publishBidEventRequest.getBid());
    }
}
