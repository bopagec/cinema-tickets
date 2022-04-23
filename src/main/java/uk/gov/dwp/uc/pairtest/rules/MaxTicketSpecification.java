package uk.gov.dwp.uc.pairtest.rules;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.List;

import static uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.*;

public class MaxTicketSpecification extends AbstractSpecification<List<TicketTypeRequest>> {
    @Override
    public boolean test(List<TicketTypeRequest> requests) {
        int ticketsToPurchase = requests.stream()
                .filter(request -> request.getTicketType().equals(Type.ADULT) || request.getTicketType().equals(Type.CHILD))
                .mapToInt(request -> request.getNoOfTickets()).sum();

        if (ticketsToPurchase > 20) {
            throw new InvalidPurchaseException("Only a maximum of 20 tickets that can be purchased at a time excluding child and infant tickets.");
        }

        return true;
    }
}
