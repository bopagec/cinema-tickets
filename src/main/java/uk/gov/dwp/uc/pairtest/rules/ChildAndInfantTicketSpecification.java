package uk.gov.dwp.uc.pairtest.rules;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;
import uk.gov.dwp.uc.pairtest.exception.InvalidPurchaseException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public class ChildAndInfantTicketSpecification extends AbstractSpecification<List<TicketTypeRequest>> {
    @Override
    public boolean test(List<TicketTypeRequest> requests) {
        Map<Type, Long> ticketCountByType = requests.stream()
                .collect(Collectors.groupingBy(ticketTypeRequest -> ticketTypeRequest.getTicketType(), Collectors.summingLong(value -> value.getNoOfTickets())));

        long childAndInfantTickets = ticketCountByType.entrySet().stream()
                .filter(entry -> entry.getKey().equals(Type.CHILD) || entry.getKey().equals(Type.INFANT))
                .mapToLong(entry -> entry.getValue()).sum();

        if (childAndInfantTickets > 0 && ticketCountByType.get(Type.ADULT) == null || ticketCountByType.get(Type.ADULT) <= 0) {
            throw new InvalidPurchaseException("Child or Infant tickets cannot be purchased without purchasing an Adult ticket.");
        }

        return true;
    }
}
