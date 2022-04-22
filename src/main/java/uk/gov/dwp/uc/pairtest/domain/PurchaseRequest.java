package uk.gov.dwp.uc.pairtest.domain;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Component
@Data
public class PurchaseRequest {
    private TicketTypeRequest[] tickets;
    @Min(value=0, message="All accounts with an id less than than zero are invalid.")
    private long accountId;
}
