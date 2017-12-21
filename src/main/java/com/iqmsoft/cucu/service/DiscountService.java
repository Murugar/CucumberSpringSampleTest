package com.iqmsoft.cucu.service;

import java.math.BigDecimal;
import java.util.Calendar;

import org.springframework.stereotype.Component;

import com.iqmsoft.cucu.model.Ticket;

@Component
public class DiscountService {

	public void execute(Ticket ticket) {

		Calendar now = Calendar.getInstance();
		Calendar discountExpiresAt = Calendar.getInstance();
		
		discountExpiresAt.setTime(ticket.getFlight().getDatetime().getTime());
		discountExpiresAt.add(Calendar.HOUR, 0 - ticket.getPolicy().getDiscountPeriodHours());

		if (now.before(discountExpiresAt)) {
			ticket.setDiscount(BigDecimal.valueOf(0.25));
		}
	}

}
