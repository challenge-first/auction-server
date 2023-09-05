package com.example.auctionserver.adapter.in.web;

import com.example.auctionserver.application.usecase.BidAuctionUseCase;
import com.example.auctionserver.application.usecase.model.ResponseBidDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class BidAuctionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BidAuctionUseCase bidAuctionUseCase;

    private ResponseBidDto responseBidDto;

    @BeforeEach
    public void beforeEach() {
        responseBidDto = ResponseBidDto.builder()
                .winningPrice(2000L)
                .build();
    }

    @Test
    @DisplayName("경매 입찰 컨트롤러 테스트")
    void bid() throws Exception {

        when(bidAuctionUseCase.bid(any()))
                .thenReturn(responseBidDto);

        mockMvc.perform(post("/auctions/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(any())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("data.winningPrice").value(2000L))
                .andExpect(jsonPath("data").exists());
    }
}