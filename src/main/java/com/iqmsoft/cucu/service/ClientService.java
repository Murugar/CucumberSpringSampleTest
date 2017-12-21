package com.iqmsoft.cucu.service;

import org.springframework.stereotype.Service;

import com.iqmsoft.cucu.model.Ticket;

@Service
public class ClientService {

	public void notify(Ticket ticket, String message) {
		ticket.addMessage(message);
	}

}
