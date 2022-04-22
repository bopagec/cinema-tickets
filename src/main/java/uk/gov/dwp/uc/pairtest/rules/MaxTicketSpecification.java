package uk.gov.dwp.uc.pairtest.rules;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.List;

public class MaxTicketSpecification extends AbstractSpecification<List<TicketTypeRequest>> {
    @Override
    public boolean test(List<TicketTypeRequest> requests) {
        if (requests.stream().map(TicketTypeRequest::getNoOfTickets).count() > 20) {
            throw new InvalidPurchaseException("Only a maximum of 20 tickets that can be purchased at a time.");
        }

        return true;
    }
}
