package service;

import com.poppulo.dto.LineDTO;
import com.poppulo.dto.TicketDTO;
import com.poppulo.enums.TicketErrors;
import com.poppulo.service.TicketService;
import com.poppulo.service.TicketServiceImpl;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TicketServiceTest {

    private TicketService ticketService = new TicketServiceImpl();
    private Map<Integer, TicketDTO> ticketMap = new HashMap<>();

    @Test
    public void testCreateTicket() {
        assert ticketService.createTicket(1) != 0;
    }

    @Test
    public void testGetAllTickets() {
        TicketDTO ticketDTO = new TicketDTO();
        List<LineDTO> lines = new ArrayList<>();
        lines.add(new LineDTO());
        ticketDTO.setLineDTO(lines);
        ticketMap.put(1, ticketDTO);
        ReflectionTestUtils.setField(ticketService, "ticketMap", ticketMap);
        Collection<TicketDTO> tickets = ticketService.getAllTickets();
        assert tickets != null && !tickets.isEmpty();
    }

    @Test
    public void testAmendTicket() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setTicketNumber(1);
        List<LineDTO> lines = new ArrayList<>();
        lines.add(new LineDTO());
        ticketDTO.setLineDTO(lines);
        ticketMap.put(1, ticketDTO);
        ReflectionTestUtils.setField(ticketService, "ticketMap", ticketMap);
        Optional<TicketErrors> ticketErrorsOptional=ticketService.amendTicket(ticketDTO);
        assert !ticketErrorsOptional.isPresent();
    }
}
