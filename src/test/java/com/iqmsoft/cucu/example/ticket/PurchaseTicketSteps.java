package com.iqmsoft.cucu.example.ticket;

import cucumber.api.java.After;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.iqmsoft.cucu.StepsBase;
import com.iqmsoft.cucu.model.Client;
import com.iqmsoft.cucu.model.Flight;
import com.iqmsoft.cucu.model.PremiumClient;
import com.iqmsoft.cucu.model.Ticket;
import com.iqmsoft.cucu.service.ClientService;
import com.iqmsoft.cucu.service.TicketService;

public class PurchaseTicketSteps extends StepsBase {

	@Autowired
	protected ClientService customerService;

	@Autowired
	protected TicketService ticketService;

	private Ticket ticket;

	@After
	public void reset() {
		ticket = null;
	}

	@Given("^tickets can be purchased$")
	public void assertAllServicesActive() throws Throwable {
		assertNotNull(customerService);
		assertNotNull(ticketService);
	}

	@When("^\"([^\"]*)\" customer makes request to purchase the ticket$")
	public void makePurchase(String customerType) throws Throwable {
		Client customer = customerType.equals("regular") ? new Client() : new PremiumClient();
		Flight flight = new Flight(Calendar.getInstance());
		ticket = ticketService.purchase(flight, customer);
		assertNotNull(ticket);
	}

	@And("^request is within the acceptable discount period defined by the policy$")
	public void configureDiscountOn() throws Throwable {
		Calendar in48Hours = Calendar.getInstance();
		in48Hours.add(Calendar.HOUR, 48);
		ticket.getFlight().setDatetime(in48Hours);
		assertNotNull(ticket);
	}

	@Then("^price of the ticket should be reduced for amount defined by the policy$")
	public void assertTicketWithDiscount() throws Throwable {
		assertNotNull(ticket.getDiscount());
	}

	@And("^payment transaction should be initiated with payment provider$")
	public void assertPaymentTransactionMade() throws Throwable {
		assertTrue(ticket.isPaymentCompleted());
	}

	@And("^customer should be notified about the \"([^\"]*)\"$")
	public void assertCustomerNotified(String message) throws Throwable {
		assertTrue(ticket.getMessages().contains(message));
	}

	@And("^discount period defined by the policy has expired$")
	public void configureDiscountOff() throws Throwable {
		Calendar in23Hours = Calendar.getInstance();
		in23Hours.add(Calendar.HOUR, 23);
		ticket.getFlight().setDatetime(in23Hours);
		assertNotNull(ticket);
	}

	@Then("^ticket should have regular price$")
	public void assertTicketWithoutDiscount() throws Throwable {
		assertEquals(BigDecimal.ZERO, ticket.getDiscount());
	}

}
