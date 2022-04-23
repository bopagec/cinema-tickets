package uk.gov.dwp.uc.pairtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import thirdparty.paymentgateway.TicketPaymentService;
import thirdparty.seatbooking.SeatReservationService;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;
import uk.gov.dwp.uc.pairtest.rules.ChildAndInfantTicketSpecification;
import uk.gov.dwp.uc.pairtest.rules.MaxTicketSpecification;

import java.util.Arrays;

@Slf4j
public class TicketServiceImpl implements TicketService {
    /**
     * Should only have private methods other than the one below.
     */
    @Autowired
    private TicketPaymentService ticketPaymentService;
    @Autowired
    private SeatReservationService seatReservationService;

    @Override
    public void purchaseTickets(Long accountId, TicketTypeRequest... ticketTypeRequests) throws InvalidPurchaseException {
        boolean isValidTicketRequest = new ChildAndInfantTicketSpecification()
                .and(new MaxTicketSpecification())
                .test(Arrays.asList(ticketTypeRequests));

        if (isValidTicketRequest) {
            int totalPrice = calculateTotalPrice(ticketTypeRequests);
            log.info("purchasing ticket, accountId: {}, totalPrice: {}, request: {}", accountId, totalPrice, ticketTypeRequests);
            ticketPaymentService.makePayment(accountId, totalPrice);

            log.info("reserving seats, accountId: {}, request: {}", accountId, ticketTypeRequests);
            int seatsToAllocate = calculateTotalSeatsToAllocate(ticketTypeRequests);
            seatReservationService.reserveSeat(accountId, seatsToAllocate);
        }
    }

    private int calculateTotalPrice(TicketTypeRequest... requests) {
        return Arrays.stream(requests)
                .mapToInt(value -> value.getNoOfTickets() * value.getTicketType().getPrice())
                .sum();
    }

    private int calculateTotalSeatsToAllocate(TicketTypeRequest... requests) {
        return Arrays.stream(requests)
                .filter(ticketTypeRequest -> !ticketTypeRequest.getTicketType().equals(TicketTypeRequest.Type.INFANT))
                .mapToInt(value -> value.getNoOfTickets())
                .sum();
    }
}