package uk.gov.dwp.uc.pairtest.rules;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChildAndInfantTicketSpecification extends AbstractSpecification<List<TicketTypeRequest>> {
    @Override
    public boolean test(List<TicketTypeRequest> requests) {
        Map<TicketTypeRequest.Type, Long> ticketCountByType = requests.stream()
                .collect(Collectors.groupingBy(ticketTypeRequest -> ticketTypeRequest.getTicketType(), Collectors.counting()));

        int childAndInfantTickets = 0;

        for (TicketTypeRequest.Type type : TicketTypeRequest.Type.values()) {
            if (ticketCountByType.containsKey(type)) {
                childAndInfantTickets += ticketCountByType.get(type);
            }
        }

        if (childAndInfantTickets > 0 && ticketCountByType.get(TicketTypeRequest.Type.ADULT) != null && ticketCountByType.get(TicketTypeRequest.Type.ADULT) <= 0) {
            throw new InvalidPurchaseException("Child and Infant tickets cannot be purchased without purchasing an Adult ticket.");
        }

        return true;
    }
}
