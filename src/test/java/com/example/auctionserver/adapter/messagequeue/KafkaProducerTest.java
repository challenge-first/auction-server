package com.example.auctionserver.adapter.messagequeue;

import com.example.auctionserver.auction.dto.request.RequestBidDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@EmbeddedKafka
@AutoConfigureMockMvc
@TestPropertySource(properties = "spring.config.location=classpath:application-test.yaml")
class KafkaProducerTest {

    @Configuration
    public class KafkaTestConfig {

        @Value("${spring.kafka.consumer.bootstrap-servers}")
        private String BOOTSTRAP_ADDRESS;

        @Value("${spring.kafka.consumer.auto-offset-reset}")
        private String AUTO_OFFSET_RESET;

        @Value("${spring.kafka.consumer.enable-auto-commit}")
        private boolean AUTO_COMMIT;

        @Bean
        ConsumerFactory<String,String> consumerFactory(){
            Map<String, Object> config = new HashMap<>();
            config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_ADDRESS);
            config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

            return new DefaultKafkaConsumerFactory<>(config);
        }

        @Bean
        ConcurrentKafkaListenerContainerFactory<String, String> containerFactory(){
            ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            return factory;
        }
    }

    @RequiredArgsConstructor
    @Component
    public class BidEventConsumer {

        private final ObjectMapper objectMapper;
        @KafkaListener(topics = "bid-topic", containerFactory = "containerFactory")
        public void listener(String message) throws JsonProcessingException {

            RequestBidDto requestBidDto = objectMapper.readValue(message, RequestBidDto.class);
        }
    }


    @Autowired
    private KafkaProducer auctionProducer;

    @Autowired
    private BidEventConsumer bidEventConsumer;
    @Autowired
    ObjectMapper objectMapper;
    @Test
    public void testBidMethod() {


    }
}