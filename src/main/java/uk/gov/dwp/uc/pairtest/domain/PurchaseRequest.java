package uk.gov.dwp.uc.pairtest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {
    @Valid
    private TicketTypeRequest[] tickets;
    @Min(value=0, message="All accounts with an id less than than zero are invalid.")
    private long accountId;
}
