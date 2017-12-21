package com.iqmsoft.cucu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iqmsoft.cucu.model.Client;
import com.iqmsoft.cucu.model.Flight;
import com.iqmsoft.cucu.model.Ticket;

@Service
public class TicketService {

	@Autowired
	ClientService customerService;

	@Autowired
	DiscountService discount;

	@Autowired
	PaymentService paymentService;

	public Ticket purchase(Flight flight, Client customer) {

		Ticket ticket = new Ticket(flight, customer);

		discount.execute(ticket);

		paymentService.createTransaction(ticket);

		customerService.notify(ticket, "purchase");

		return ticket;
	}

}
