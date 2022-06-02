package app.theater.notifications;

import app.theater.*;
import app.theater.areas.*;
import app.theater.events.*;
import app.theater.users.*;
import app.theater.performances.tickets.*;
import app.theater.performances.*;
import app.theater.passes.*;
import app.theater.paymentmethod.*;

import java.time.*;
import java.util.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tester for the notifications package
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TestNotification {
	
	Application app;

	Event event= new Concert("Pablo live", "This is the description", 60, "Pablo ", "Álvaro Zamanillo", "./aux_files/img/test_im.jpg", "program", "orcherstra", "");

	Client client = new Client("pacuestas","helloworld123");

	SittingArea sittingArea1;
	 
			
	@Before
	public void setUp() {
		app = Application.getNewInstanceForTests();
		app.register("fernandes","abc123");

		sittingArea1 = new SittingArea("area1", 5, 5);
		app.addArea(sittingArea1);
		app.addEvent(event);
		event.addPrice(sittingArea1, 10);

		event.addPerformance(LocalDate.of(2022, 10, 16));
		Performance performance=event.getPerformances(false).get(0);
		Ticket ticket =performance.getTickets(sittingArea1).get(0);

		ticket.purchase(client, new PassPay(new AnnualPass(sittingArea1, 2022)));
	}

	@Test
	public void testReceivePostponeNotification() {
		Performance performance=event.getPerformances(false).get(0);
		performance.postpone(LocalDate.of(2023,2,2));

		/* Check that the client has recevied a notificiation*/
		assertTrue(client.getNotifications().size()==1);
		/*check the notification received is from the performance*/
		assertEquals(((PostponedNotification)(client.getNotifications().get(0))).getPerformance(),performance);
	}


	@Test
	public void testReceiveAvailableNotification(){
		Performance performance=event.getPerformances(false).get(0);

		app.setMaxTicketsReservation(1000);
		for(Ticket t: performance.getAvailableTickets()){
			t.reserve(client);
		}

		Client client2 = (Client)app.getUser("fernandes");
		client2.addToPerformanceWaitingList(performance);


		for(Ticket t: client.getTickets()){
			if(t instanceof Reservation){
				((Reservation) t).cancel();
				break;
			}
		}


		app.login("fernandes","abc123");
		assertTrue(client2.getNotifications().size()==1);
		AvailableNotification not =(AvailableNotification)(client2.getNotifications().get(0));

		assertEquals(not.getPerformance(),performance);
	}


	@Test
	public void testReceiveCancelledNotification(){
		Performance performance = event.getPerformances(false).get(0);
		performance.cancel();

		/* Check that the client has recevied a notificiation */
		assertTrue(client.getNotifications().size() == 1);
	
	}
}
