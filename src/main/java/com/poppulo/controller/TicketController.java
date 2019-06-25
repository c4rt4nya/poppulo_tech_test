package com.poppulo.controller;

import com.poppulo.dto.TicketDTO;
import com.poppulo.enums.TicketErrors;
import com.poppulo.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(value = "/api/v1", produces = "application/json; charset=UTF-8")
public class TicketController {

    private final static Logger log = Logger.getLogger(TicketController.class.getName());
    @Resource
    private TicketService ticketService;

    @GetMapping(value = "/ticket")
    public ResponseEntity getAllTickets() {
        log.info("Retrieving all the tickets");
        Collection<TicketDTO> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok().body(tickets);
    }

    @GetMapping(value = "/ticket/{ticketNumber}")
    public ResponseEntity getATicket(@PathVariable int ticketNumber) {
        log.info("Retrieving all the tickets");
        ResponseEntity responseEntity;
        Optional<TicketDTO> optionalTicketDTO = ticketService.getATicket(ticketNumber);
        if (optionalTicketDTO.isPresent()) {
            responseEntity = ResponseEntity.ok().body(optionalTicketDTO.get());
        } else {
            responseEntity = ResponseEntity.badRequest().body(TicketErrors.TICKET_NOT_FOUND);
        }
        return responseEntity;
    }

    @PostMapping(value = "/ticket")
    public ResponseEntity createTicket(@RequestParam int nlines) {
        log.info("Creating a ticket");
        ResponseEntity responseEntity;
        int ticketNumber = ticketService.createTicket(nlines);
        return ResponseEntity.ok().body(ticketNumber);
    }

    @PutMapping(value = "/ticket")
    public ResponseEntity amendTicket(@RequestBody TicketDTO ticketDTO) {
        log.info("Amending ticket");
        ResponseEntity responseEntity;
        Optional<TicketErrors> ticketErrorsOptional = ticketService.amendTicket(ticketDTO);
        if (!ticketErrorsOptional.isPresent()) {
            responseEntity = ResponseEntity.ok().build();
        } else {
            responseEntity = ResponseEntity.badRequest().body(ticketErrorsOptional.get());
        }
        return responseEntity;
    }

    @PutMapping(value = "/status/{ticketNumber}")
    public ResponseEntity ticketStatus(@PathVariable int ticketNumber) {
        log.info("Getting ticket status");
        ResponseEntity responseEntity;
        if(ticketService.checkTicketStatus(ticketNumber)){
            responseEntity = ResponseEntity.ok().body("You WON!");
        }else{
            responseEntity = ResponseEntity.ok().body("You did not win");
        }
        return responseEntity;
    }
}
