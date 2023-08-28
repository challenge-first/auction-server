package com.example.auctionserver.adapter.messagequeue;

import com.example.auctionserver.domain.dto.request.RequestBidDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendBidDto(String topic, RequestBidDto bidDto) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";

        try {
            jsonInString = objectMapper.writeValueAsString(bidDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("memberId:{}, point:{}", bidDto.getMemberId(), bidDto.getBid());
    }
}
