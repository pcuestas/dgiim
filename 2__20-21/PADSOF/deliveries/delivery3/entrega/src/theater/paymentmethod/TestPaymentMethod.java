package app.theater.paymentmethod;

import static org.junit.Assert.*;

import java.time.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import app.theater.Application;
import app.theater.areas.*;
import app.theater.events.*;
import app.theater.events.cycles.Cycle;
import app.theater.passes.CyclePass;
import app.theater.passes.Pass;
import app.theater.performances.*;
import app.theater.performances.tickets.*;

/**
 * Tester for the paymentMethod package
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TestPaymentMethod {
	Event event1;
	Event event2 ;
	Event event3 ;
	
	Application app;

	Performance performance1;

	StandingArea a1;
    SittingArea a2;

	Ticket ticket1;
	Ticket ticket2;
	Ticket ticket3;

	@Before
	public void setUp() throws Exception {
		app = Application.getNewInstanceForTests();
		a1 = new StandingArea("A", 80);
	    a2 = new SittingArea("B", 5, 5);
	
		event1 = new Play("La Casa de Bernarda Alba", "description", 120, "Federico García Lorca", "Alvaro Zamanillo", "./aux_files/img/test_im.jpg", new ArrayList<>(Arrays.asList("Pepe", "Juana")));
		event2 = new Concert("Rock band", "descr", 60, "Pablo Cuesta", "John Doe", "./aux_files/img/test_im.jpg", "program", "orcherstra", new ArrayList<>(Arrays.asList("sol1", "sol2")));
		event3 = new Dance("Nutcracker", "description", 78, "author", "director", "./aux_files/img/test_im.jpg", new ArrayList<>(Arrays.asList("dan1", "dan2")), "conductor", "orchestra");
		
		app.addArea(a1);
		app.addArea(a2);
		app.addEvent(event1);
		
		event1.addPrice(a1, 13.50);
		event1.addPrice(a2, 18.50);
		event3.addPrice(a1, 13.50);
		event3.addPrice(a2, 18.50);

		event1.addPerformance(LocalDate.of(2023,5,19));
		event1.addPerformance(LocalDate.of(2024,10,19));
		event3.addPerformance(LocalDate.of(2026,2,1));

		Performance perf1 = event1.getPerformances(false).get(0);
		Performance perf2 = event1.getPerformances(false).get(1);
		Performance perf3 = event3.getPerformances(false).get(0);
		ticket1 = perf1.getAvailableTickets().get(0);
		ticket2 = perf2.getAvailableTickets().get(0);
		ticket3 = perf3.getAvailableTickets().get(0);
	}

	@Test
	public void testCreditCard() {
		CreditCard cc1 = new CreditCard("1234567891234567");
		CreditCard cc2 = new CreditCard("123456789123456");

		/*ticket can be paid with a valid credit card*/
		assertTrue(cc1.pay(ticket1));
		/*ticket can't be paid with an invalid credit card*/
		assertFalse(cc2.pay(ticket1));
	}

	@Test
	public void testPassPay() {
		Map<SimpleArea, Double> reductions = new HashMap<>();
		// reduction of each area
		reductions.put(a1, 25.);
		reductions.put(a2, 14.);

		Cycle cycle = new Cycle("cycle", new ArrayList<>(Arrays.asList(event1, event2)), reductions);
		Pass pass = new CyclePass(a1, cycle); 

		PassPay passpay = new PassPay(pass);
		/*ticket of the cycle can be paid with the pass*/
		assertTrue(passpay.pay(ticket1));
		/*pass can't purchase two tickets from the same event*/
		assertFalse(passpay.pay(ticket2));
		passpay.payback(ticket1);
		/*after ticket was cancelled, now a ticket of the event can be purchased with it*/
		assertTrue(passpay.pay(ticket2));
		/*ticket not from the cycle can't be paid with the pass*/
		assertFalse(passpay.pay(ticket3));
	}
	
}
