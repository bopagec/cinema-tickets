package uk.gov.dwp.uc.pairtest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.fixtures.PurchaseRequestFixture;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TicketPurchaseControllerTest {
    @MockBean
    private TicketService ticketService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    private PurchaseRequestFixture purchaseRequestFixture;

    @BeforeEach
    public void setUp() {
        purchaseRequestFixture = new PurchaseRequestFixture();
    }

    @Test
    public void purchaseThrowsBadRequestWithValidPurchaseRequest() throws Exception {
        doNothing().when(ticketService).purchaseTickets(anyLong(), any());
        String requestString = objectMapper.writeValueAsString(purchaseRequestFixture.createInValidRequest());

        mockMvc.perform(
                        post("/ticket")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestString))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("Ticket purchase failed. Method Argument Not Valid"));
    }

    @Test
    public void purchaseReturn_200_WithValidPurchaseRequest() throws Exception {
        doNothing().when(ticketService).purchaseTickets(anyLong(), any());
        String requestString = objectMapper.writeValueAsString(purchaseRequestFixture.createValidRequest());

        mockMvc.perform(
                        post("/ticket")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestString))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("purchase success!"));
    }
}