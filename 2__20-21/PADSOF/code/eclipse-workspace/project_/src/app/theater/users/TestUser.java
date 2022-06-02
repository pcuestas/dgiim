package app.theater.users;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import app.theater.*;
import app.theater.areas.*;
import app.theater.events.*;
import app.theater.performances.Performance;
import app.theater.performances.tickets.Ticket;
import app.theater.performances.tickets.*;
import app.theater.notifications.*;
import app.theater.paymentmethod.*;
import app.theater.events.cycles.*;

/**
 * Tester for the package users
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TestUser {
	Application app;
	Event 	event1 = new Play("La Casa de Bernarda Alba", "description", 120, "Federico García Lorca", "Alvaro Zamanillo", "./aux_files/img/test_im.jpg", "");
	Event event2 = new Concert("Pablo live2", "This is the description", 60, "Pablo ", "Álvaro Zamanillo", "./aux_files/img/test_im.jpg", "program", "orcherstra", "");
		
	StandingArea a1= new StandingArea("A", 10);
	StandingArea a2 = new StandingArea("B", 20);

	Client client = new Client("pacuestas", "helloworld123");
	CreditCard method = new CreditCard("1234567891234567");

	@Before
	public void setUp() {
		app = Application.getNewInstanceForTests();
		app.addEvent(event1);
        app.addEvent(event2);
        app.addArea(a1);
        app.addArea(a2);
        Map<SimpleArea, Double> prices = new HashMap<>();
        prices.put(a1, 10.);
        prices.put(a2, 5.);
        event1.setPrices(prices);
        event2.setPrices(prices);
	}

	@Test
	public void testCanReserve() {
		event1.addPrice(a1, 13.50);
		event1.addPerformance(LocalDate.of(2023,5,19));
		Performance performance = event1.getPerformances().get(0);
		List<Ticket> tickets=performance.getAvailableTickets(a1);

		int n=app.getMaxTicketsReservation();

		//Can reserve as many tickets as the maximum number indicates
		for(int i=0;i<n;i++){
			assertTrue(client.canReserve(performance));
			tickets.get(i).reserve(client);
		}

		//Cannot reserve more than the maximum stablished
		assertFalse(client.canReserve(performance));
	}


	@Test
	public void testCanPurchase(){
		event1.addPrice(a1, 13.50);
		event1.addPerformance(LocalDate.of(2023, 5, 19));
		Performance performance = event1.getPerformances().get(0);
		List<Ticket> tickets = performance.getAvailableTickets(a1);
		

		int n = app.getMaxTicketsPurchase();

		//Can purchase as many tickets as the maximum number indicates
		for (int i = 0; i < n; i++) {
			assertTrue(client.canPurchase(performance));
			tickets.get(i).purchase(client,method);
		}

		// Cannot purchase more than the maximum established
		assertFalse(client.canPurchase(performance));
	}


	@Test
	public void testWaitingList(){
		event1.addPrice(a1, 13.50);
		event1.addPerformance(LocalDate.of(2023, 5, 19));
		Performance performance = event1.getPerformances().get(0);
		List<Ticket> tickets = performance.getAvailableTickets();
		
		app.setMaxTicketsReservation(30);// for this test

		//Can reserve as many tickets as the maximum number indicates
		for (int i = 0; i < 30; i++) {
			assertTrue(tickets.get(i).reserve(client));
		}
		
		//Can register
		assertTrue(app.register("fernandes","abc123"));
		
		Client client2 = (Client)app.getUser("fernandes");

		//There are no tickets, he signs up for the waiting list
		assertTrue(client2.addToPerformanceWaitingList(performance));

		((Reservation)(client.getTickets().get(0))).cancel();

		
		//When client2 logs in he should receive a notification
		assertTrue(app.login("fernandes","abc123"));

		AvailableNotification notification=((AvailableNotification)(client2.getNotifications().get(0)));

		//He has received a notification
		assertTrue(client2.getNotifications().size()==1);

		//performance associated to the notification is okey
		assertTrue(notification.getPerformance()==performance);


	}

	@Test
	public void testBuyAnnualPass(){
		
		//Purchase of an annual pass for an area with defined price
		a1.setAnnualPassPrice(24);
		assertTrue(client.buyPass(2020,a1,method));

		//Check the pass has been saved in the client account
		assertTrue(client.getPasses().size()==1);

		//Cannot buy two passes for the same area and same year
		assertFalse(client.buyPass(2020,a1,method));

		//Can buy for the same area but different years
		assertTrue(client.buyPass(2021,a1,method));

		//Purchase of an annual pass for an area without defined price
		assertFalse(client.buyPass(2020, a2, method));
	}


	@Test
	public void testBuyCyclePass(){

		Map<SimpleArea, Double> reductions = new HashMap<>();

		reductions.put(a1, 10.);

		Cycle cycle =new Cycle("cycle", new ArrayList<>(Arrays.asList(event1, event2)), reductions);

		//Check cycle without reductions for an area can't be added
        assertFalse(Application.getInstance().addCycle(cycle));


		reductions.put(a2, 10.);

		cycle =new Cycle("cycle", new ArrayList<>(Arrays.asList(event1, event2)), reductions);

		//Cycle is correct, can be added
        assertTrue(Application.getInstance().addCycle(cycle));

		//Buy a cycle Pass
		assertTrue(client.buyPass(cycle,a1,method));

		// Check the pass has been saved in the client account
		assertTrue(client.getPasses().size() == 1);

		//Cannot buy a cyclePass for the same area
		assertFalse(client.buyPass(cycle,a1,method));

	}

}