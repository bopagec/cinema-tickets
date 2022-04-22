package uk.gov.dwp.uc.pairtest.fixtures;

import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

import java.util.ArrayList;
import java.util.List;

import static uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest.Type;

public class TicketTypeRequestFixture {
    private List<TicketTypeRequest> requests = new ArrayList<>();

    private TicketTypeRequest createTicket(Type type, int count) {
        return new TicketTypeRequest(type, count);
    }

    public TicketTypeRequestFixture createChildTicket(int number) {
        requests.add(createTicket(Type.CHILD, number));
        return this;
    }

    public TicketTypeRequestFixture createInfantTicket(int number) {
        requests.add(createTicket(Type.INFANT, number));
        return this;
    }

    public TicketTypeRequestFixture createAdultTicket(int number) {
        requests.add(createTicket(Type.ADULT, number));
        return this;
    }

    public TicketTypeRequest[] getRequestArray() {
        return requests.toArray(new TicketTypeRequest[requests.size()]);
    }
}
