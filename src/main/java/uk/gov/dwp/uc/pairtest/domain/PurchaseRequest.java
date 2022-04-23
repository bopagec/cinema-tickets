package uk.gov.dwp.uc.pairtest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Component
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {
    @Valid
    @NotNull
    @Size(min = 1)
    private TicketTypeRequest[] tickets;
    @Min(value = 0, message = "All accounts with an id less than than zero are invalid.")
    @NotNull
    private Long accountId;
}
