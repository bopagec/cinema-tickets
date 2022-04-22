package uk.gov.dwp.uc.pairtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.dwp.uc.pairtest.TicketService;
import uk.gov.dwp.uc.pairtest.domain.PurchaseRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class TicketPurchaseController {
    @Autowired
    private TicketService ticketService;

    @PostMapping("/ticket")
    public String purchaseTicket(@Valid @RequestBody PurchaseRequest request) {
        ticketService.purchaseTickets(request.getAccountId(), request.getTickets());
        return "done";
    }
}
