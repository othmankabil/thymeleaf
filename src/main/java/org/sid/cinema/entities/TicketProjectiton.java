package org.sid.cinema.entities;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="ticketProj",types=Ticket.class)
public interface TicketProjectiton {
	public Long getId();
	public String getNomClient();
	public double getPrix();
	public Integer getCodePaiement();
	public boolean getReservee();
	public Place getPlace();
}
