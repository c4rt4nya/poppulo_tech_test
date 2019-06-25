package com.poppulo.service;

import com.poppulo.dto.LineDTO;
import com.poppulo.dto.TicketDTO;
import com.poppulo.enums.TicketErrors;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class TicketServiceImpl implements TicketService {

    Map<Integer, TicketDTO> ticketMap = new HashMap<>();

    @Override
    public int createTicket(int nlines) {
        TicketDTO ticketDTO = new TicketDTO();
        List<LineDTO> lines = new ArrayList<>();
        for (int i = 0; i < nlines; i++) {
            LineDTO lineDTO = new LineDTO(getRandomLineNumber(), getRandomLineNumber(), getRandomLineNumber());
            setLineResult(lineDTO);
            lines.add(lineDTO);
        }
        ticketDTO.setLineDTO(lines);
        int ticketNumber = ticketMap.size() + 1;
        ticketDTO.setTicketNumber(ticketNumber);
        ticketMap.put(ticketNumber, ticketDTO);
        return ticketNumber;
    }

    private void setLineResult(LineDTO lineDTO) {
        int n1 = lineDTO.getN1();
        int n2 = lineDTO.getN2();
        int n3 = lineDTO.getN3();
        int result;
        if (n1 + n2 + n3 == 2) {
            result = 10;
        } else if (n1 == n2 && n2 == n3) {
            result = 5;
        } else if (n2 != n1 && n3 != n1) {
            result = 1;
        } else {
            result = 0;
        }
        lineDTO.setResult(result);
    }

    @Override
    public Collection<TicketDTO> getAllTickets() {
        return ticketMap.values();
    }

    @Override
    public Optional<TicketDTO> getATicket(int ticketNumber) {
        return Optional.ofNullable(ticketMap.get(ticketNumber));
    }

    @Override
    public Optional<TicketErrors> amendTicket(TicketDTO ticketDTO) {
        Optional<TicketErrors> optionalTicketErrors = Optional.empty();
        if (ticketMap.containsKey(ticketDTO.getTicketNumber())) {
            TicketDTO persistedTicket = ticketMap.get(ticketDTO.getTicketNumber());
            if (!persistedTicket.isChecked()) {
                persistedTicket.getLineDTO().addAll(ticketDTO.getLineDTO());
                for (LineDTO lineDTO : ticketDTO.getLineDTO()) {
                    setLineResult(lineDTO);
                }
            } else {
                optionalTicketErrors = Optional.of(TicketErrors.TICKET_ALREADY_CHECKED);
            }
        } else {
            optionalTicketErrors = Optional.of(TicketErrors.TICKET_NOT_FOUND);
        }
        return optionalTicketErrors;
    }

    @Override
    public boolean checkTicketStatus(int ticketNumber) {
        //the winner number will be 1 0 5 10
        if (ticketMap.containsKey(ticketNumber)) {
            TicketDTO persistedTicket = ticketMap.get(ticketNumber);
            if (persistedTicket.getLineDTO().get(0).getResult() == 1 &&
                    persistedTicket.getLineDTO().get(1).getResult() == 0 &&
                    persistedTicket.getLineDTO().get(2).getResult() == 5 &&
                    persistedTicket.getLineDTO().get(3).getResult() == 10) {
                return true;
            }
            persistedTicket.setChecked(true);
        }
        return false;
    }


    private static int getRandomLineNumber() {
        Random r = new Random();
        return r.ints(0, (2 + 1)).limit(1).findFirst().getAsInt();
    }

}
