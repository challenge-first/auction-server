package com.example.auctionserver.adapter.in.web;

import com.example.auctionserver.application.usecase.GetAuctionQuery;
import com.example.auctionserver.application.usecase.model.ResponseAuctionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class GetAuctionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    GetAuctionQuery getAuctionQuery;

    private ResponseAuctionDto responseAuctionDto;

    @BeforeEach
    public void beforeEach() {
        responseAuctionDto = ResponseAuctionDto.builder()
                .imageUrl("url")
                .productName("product")
                .winningPrice(2000L)
                .openingPrice(1000L)
                .openingTime(LocalDateTime.now().minusMinutes(1))
                .closingTime(LocalDateTime.now().plusHours(1))
                .build();
    }

    @Test
    @DisplayName("경매 조회 테스트")
    public void getAuction() throws Exception {
        //given
        Mockito.when(getAuctionQuery.getAuction())
                .thenReturn(responseAuctionDto);

        //when, then
        mockMvc.perform(get("/auctions"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("data").exists())
                .andExpect(jsonPath("data.openingPrice").value(2000L));
    }
}