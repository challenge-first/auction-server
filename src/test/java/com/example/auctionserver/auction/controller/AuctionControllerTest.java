package com.example.auctionserver.auction.controller;

import static org.mockito.Mockito.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//class AuctionControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @MockBean
//    AuctionService auctionService;
//
//    private ResponseAuctionDto responseAuctionDto;
//
//    @BeforeEach
//    public void beforeEach() {
//        responseAuctionDto = ResponseAuctionDto.builder()
//                .id(1L)
//                .openingPrice(100L)
//                .openingTime(LocalDateTime.now().minusMinutes(1))
//                .closingTime(LocalDateTime.now().plusHours(1))
//                .imageUrl("url")
//                .productName("product")
//                .winningPrice(100L)
//                .build();
//    }
//
//    @Test
//    @DisplayName("경매 조회 테스트")
//    public void getAuction() throws Exception {
//        //given
//        when(auctionService.getAuction())
//                .thenReturn(responseAuctionDto);
//
//        //when, then
//        mockMvc.perform(get("/auctions"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
//                .andExpect(jsonPath("data").exists())
//                .andExpect(jsonPath("data.openingPrice").value(100L));
//    }
//
//    @Test
//    @DisplayName("경매 입찰 테스트")
//    void bid() throws Exception {
//
//        LocalDateTime time = LocalDateTime.now();
//        RequestAuctionDto request = new RequestAuctionDto(10000L, time);
//
//        ResponseWinningPriceDto responseWinningPriceDto = ResponseWinningPriceDto.builder()
//                .winningPrice(10000L)
//                .build();
//
//        when(auctionService.bid(any(), any(), any()))
//                .thenReturn(responseWinningPriceDto);
//
//
//        mockMvc.perform(post("/auctions/1")
//                        .contentType(APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(content().contentType(APPLICATION_JSON))
//                .andExpect(jsonPath("data.winningPrice").value(10000L))
//                .andExpect(jsonPath("data").exists());
//    }
//}