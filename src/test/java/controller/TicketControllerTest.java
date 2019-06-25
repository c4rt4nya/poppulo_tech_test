package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.poppulo.controller.TicketController;
import com.poppulo.dto.LineDTO;
import com.poppulo.dto.TicketDTO;
import com.poppulo.service.TicketService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;
    private MockMvc mvc;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(ticketController).build();
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    public void testGetAllTickets() throws Exception {
        when(ticketService.getAllTickets()).thenReturn(new ArrayList<>());
        StringBuilder stringBuilder = new StringBuilder("/api/v1/ticket");
        mvc.perform(MockMvcRequestBuilders.get(stringBuilder.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateTicket() throws Exception {
        when(ticketService.createTicket(2)).thenReturn(1);
        StringBuilder stringBuilder = new StringBuilder("/api/v1/ticket?nlines=10");
        mvc.perform(MockMvcRequestBuilders.post(stringBuilder.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAmendTicket() throws Exception {
        LineDTO lineDTO = new LineDTO(1,0,1);
        TicketDTO ticketDTO = new TicketDTO();
        List<LineDTO> lines = new ArrayList<>();
        lines.add(lineDTO);
        ticketDTO.setLineDTO(lines);
        when(ticketService.amendTicket(any(TicketDTO.class))).thenReturn(Optional.empty());
        StringBuilder stringBuilder = new StringBuilder("/api/v1/ticket");
        mvc.perform(MockMvcRequestBuilders.put(stringBuilder.toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(ticketDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
