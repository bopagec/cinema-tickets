package uk.gov.dwp.uc.pairtest.fixtures;

import lombok.NoArgsConstructor;
import uk.gov.dwp.uc.pairtest.domain.PurchaseRequest;
import uk.gov.dwp.uc.pairtest.domain.TicketTypeRequest;

@NoArgsConstructor
public class PurchaseRequestFixture {

    public PurchaseRequest createValidRequest() {
        TicketTypeRequest[] requestArray = new TicketTypeRequestFixture()
                .createAdultTicket(1)
                .createChildTicket(1)
                .createInfantTicket(1)
                .getRequestArray();

        return PurchaseRequest.builder()
                .accountId(1)
                .tickets(requestArray)
                .build();
    }

    public PurchaseRequest createInValidRequest() {
        TicketTypeRequest[] requestArray = new TicketTypeRequestFixture()
                .createAdultTicket(0)
                .getRequestArray();

        return PurchaseRequest.builder()
                .accountId(0)
                .tickets(requestArray)
                .build();
    }
}
