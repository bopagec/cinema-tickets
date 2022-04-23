package uk.gov.dwp.uc.pairtest.domain;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Immutable Object
 */
@ToString
public class TicketTypeRequest {

    @Min(value = 1, message = "number of tickets cannot be less than 1")
    @NotNull
    private Integer noOfTickets;
    private Type type;

    public TicketTypeRequest(Type type, int noOfTickets) {
        this.type = type;
        this.noOfTickets = noOfTickets;
    }

    public int getNoOfTickets() {
        return noOfTickets;
    }

    public Type getTicketType() {
        return type;
    }

    @AllArgsConstructor
    @Getter
    public enum Type {
        ADULT(20), CHILD(10), INFANT(0);
        private int price;
    }
}
