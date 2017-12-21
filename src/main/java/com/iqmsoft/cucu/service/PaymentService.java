package com.iqmsoft.cucu.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.iqmsoft.cucu.model.Ticket;

@Service
public class PaymentService {

	public void createTransaction(Ticket ticket) {
		ticket.setPaymentTransaction(UUID.randomUUID().toString());
	}

}
