package com.example.auctionserver.adapter.out.event;

import com.example.auctionserver.application.port.out.PublishEventPort;
import com.example.auctionserver.application.port.out.model.BidEventModel;
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
    public void sendBidEvent(Long bidPoint, Long memberId) {

        BidEventModel bidEventModel = getBidEventModel(memberId, bidPoint);

        try {
            kafkaTemplate.send(bidTopic, objectMapper.writeValueAsString(bidEventModel));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("memberId:{}, point:{}", bidEventModel.getMemberId(), bidEventModel.getBid());
    }

    @Override
    public void sendEndEvent(Long memberId, Long bidPoint) {

        BidEventModel bidEventModel = getBidEventModel(memberId, bidPoint);

        try {
            kafkaTemplate.send(auctionEndTopic, objectMapper.writeValueAsString(bidEventModel));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("memberId:{}, point:{}", bidEventModel.getMemberId(), bidEventModel.getBid());
    }

    private BidEventModel getBidEventModel(Long memberId, Long point) {
        return BidEventModel.builder()
                .memberId(memberId)
                .bid(point)
                .build();
    }
}
