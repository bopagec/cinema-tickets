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
import java.util.stream.Collectors;

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
        boolean isValidTicketRequest = false;

        try{
            isValidTicketRequest = new ChildAndInfantTicketSpecification().and(new MaxTicketSpecification()).test(Arrays.asList(ticketTypeRequests));
        }catch (InvalidPurchaseException e){
            throw e;
        }

        if(isValidTicketRequest){
            log.info("purchasing ticket: {}, {}", accountId, ticketTypeRequests);
        }

    }
}
