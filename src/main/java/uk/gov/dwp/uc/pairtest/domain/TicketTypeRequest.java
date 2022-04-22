package uk.gov.dwp.uc.pairtest.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Min;

/**
 * Immutable Object
 */
@ToString
public class TicketTypeRequest {

    @Min(value = 1, message = "number of tickets cannot be less than 1")
    private int noOfTickets;
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
