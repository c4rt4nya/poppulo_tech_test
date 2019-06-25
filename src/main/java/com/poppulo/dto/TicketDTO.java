package com.poppulo.dto;

import java.util.List;

public class TicketDTO {
    private List<LineDTO> lineDTO;
    private int ticketNumber;
    private boolean checked;

    public TicketDTO() {
    }

    public TicketDTO(List<LineDTO> lineDTO, int ticketNumber, boolean checked) {
        this.lineDTO = lineDTO;
        this.ticketNumber = ticketNumber;
        this.checked = checked;
    }

    public List<LineDTO> getLineDTO() {
        return lineDTO;
    }

    public void setLineDTO(List<LineDTO> lineDTO) {
        this.lineDTO = lineDTO;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
