package uk.gov.dwp.uc.pairtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.fixtures.TicketTypeRequestFixture;

import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketServiceImplTest {

    @Mock
    private TicketPaymentService ticketPaymentService;
    @Mock
    private SeatReservationService seatReservationService;
    @InjectMocks
    private TicketServiceImpl ticketService;

    private TicketTypeRequestFixture ticketTypeRequestFixture;

    @BeforeEach
    public void setUp() {
        ticketTypeRequestFixture = new TicketTypeRequestFixture();
    }

    @Test
    public void purchaseValidTickets_CallsThirdPartyServices() {
        doNothing().when(ticketPaymentService).makePayment(anyLong(), anyInt());
        doNothing().when(seatReservationService).reserveSeat(anyLong(), anyInt());

        TicketTypeRequest[] requestArray = ticketTypeRequestFixture
                .createAdultTicket(5)
                .createChildTicket(5)
                .createInfantTicket(5)
                .getRequestArray();

        ticketService.purchaseTickets(1l, requestArray);
        verify(ticketPaymentService, times(1)).makePayment(anyLong(), anyInt());
        verify(seatReservationService, times(1)).reserveSeat(anyLong(), anyInt());
    }

    @Test
    public void purchaseInfantOrChildTicketsWithoutAdultTicket_ThrowsInvalidPurchaseException() {
        TicketTypeRequest[] requestArray = ticketTypeRequestFixture
                .createChildTicket(5)
                .createInfantTicket(5)
                .getRequestArray();

        InvalidPurchaseException exception = Assertions.assertThrows(InvalidPurchaseException.class, () -> {
            ticketService.purchaseTickets(1l, requestArray);
        });

        Assertions.assertEquals("Child and Infant tickets cannot be purchased without purchasing an Adult ticket.", exception.getMessage());
    }

    @Test
    public void purchaseMoreThan20Tickets_ThrowsInvalidPurchaseException() {
        TicketTypeRequest[] requestArray = ticketTypeRequestFixture
                .createChildTicket(5)
                .createInfantTicket(6)
                .createAdultTicket(10)
                .getRequestArray();

        InvalidPurchaseException exception = Assertions.assertThrows(InvalidPurchaseException.class, () -> {
            ticketService.purchaseTickets(1l, requestArray);
        });

        Assertions.assertEquals("Only a maximum of 20 tickets that can be purchased at a time.", exception.getMessage());
    }

    @Test
    public void correctTotalTicketPriceIsPassedTo_TicketPaymentService() {
        doNothing().when(ticketPaymentService).makePayment(anyLong(), anyInt());
        doNothing().when(seatReservationService).reserveSeat(anyLong(), anyInt());

        TicketTypeRequest[] requestArray = ticketTypeRequestFixture
                .createChildTicket(2) // 10 * 2
                .createInfantTicket(1) // free
                .createAdultTicket(2) // 20 * 2
                .getRequestArray();

        ticketService.purchaseTickets(1l, requestArray);
        ArgumentCaptor<Integer> totalArgCapture = ArgumentCaptor.forClass(Integer.class);

        verify(ticketPaymentService).makePayment(eq(1l), totalArgCapture.capture());
        Assertions.assertEquals(60, totalArgCapture.getValue());
    }

    @Test
    public void correctNumberOfSeatsIsPassedTo_SeatReservationService() {
        doNothing().when(ticketPaymentService).makePayment(anyLong(), anyInt());
        doNothing().when(seatReservationService).reserveSeat(anyLong(), anyInt());

        TicketTypeRequest[] requestArray = ticketTypeRequestFixture
                .createChildTicket(2)
                .createInfantTicket(5) // not seat required
                .createAdultTicket(2)
                .getRequestArray();

        ticketService.purchaseTickets(1l, requestArray);
        ArgumentCaptor<Integer> seatsArgCapture = ArgumentCaptor.forClass(Integer.class);
        verify(seatReservationService).reserveSeat(eq(1l), seatsArgCapture.capture());
        Assertions.assertEquals(4, seatsArgCapture.getValue());
    }
}