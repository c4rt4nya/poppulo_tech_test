package com.poppulo.enums;

public enum TicketErrors {
    TICKET_NOT_FOUND("Ticket not found"),
    TICKET_ALREADY_CHECKED("Ticket already checked");


    private final String description;

    TicketErrors(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}