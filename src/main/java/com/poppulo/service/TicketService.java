package com.poppulo.service;

import com.poppulo.dto.TicketDTO;
import com.poppulo.enums.TicketErrors;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TicketService {

    int createTicket(int nlines);

    Collection<TicketDTO> getAllTickets();

    Optional<TicketDTO> getATicket(int ticketNumber);

    Optional<TicketErrors> amendTicket(TicketDTO ticketDTO);

    boolean checkTicketStatus(int ticketNumber);
}
