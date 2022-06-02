package app.theater.performances.tickets;

import app.theater.*;
import app.theater.areas.*;
import app.theater.areas.locations.*;
import app.theater.events.*;
import app.theater.performances.*;
import app.theater.users.*;
import app.theater.passes.*;
import app.theater.paymentmethod.*;

import static org.junit.Assert.*;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import java.time.*;

/**
 * Tester for the tickets package
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TestTicket {
    Application app;

    Event event= new Concert("Pablo live", "This is the description", 60, "Pablo ", "Álvaro Zamanillo", "./aux_files/img/test_im.jpg", "program", "orcherstra", new ArrayList<>());
	
    Client client = new Client("pacuestas","helloworld123");

    CompositeArea compositeArea1_2;
	SittingArea sittingArea1 ;
	StandingArea standingArea2;
	StandingArea standingArea3;
		

	@Before
	public void setUp(){
        app = Application.getNewInstanceForTests();
        
		compositeArea1_2 = new CompositeArea("area1_2");
		sittingArea1 = new SittingArea("area1", 5, 5);
		standingArea2 = new StandingArea("area2", 50);
		standingArea3 = new StandingArea("area3", 50);

		
		compositeArea1_2.addArea(sittingArea1);
		compositeArea1_2.addArea(standingArea2);
        app.addArea(compositeArea1_2);
        app.addArea(standingArea3);

        event.addPrice(sittingArea1, 10);
        event.addPrice(standingArea2, 12);
        event.addPrice(standingArea3, 15);
        
	}
	
	@Test
	public void testTicket() {
		Seat seatdisabled = sittingArea1.getSeat(1,2);

		seatdisabled.disable(LocalDate.of(2021,3,9), LocalDate.of(2023,5,4));

        event.addPerformance(LocalDate.of(2022,10,16));

        Performance p = event.getPerformances(false).get(0);


        Ticket ticket = p.getSittingTicket(sittingArea1, 1, 2);
       	/*disabled seats can't be reserved nor purchased*/
		assertFalse(ticket.reserve(client));
		assertFalse(ticket.purchase(client, new PassPay(new AnnualPass(sittingArea1, 2022))));
	
		
		//another ticket--reservation
		ticket = p.getSittingTicket(sittingArea1, 1, 3);
		/*available ticket can be reserved*/
		assertTrue(ticket.reserve(client));
		/*now it is not in the list of tickets because it is another object (a reservation)*/
		assertFalse(p.getTickets(sittingArea1).contains(ticket));
		
		//it is now a reservation
        Ticket reservedTicket = p.getSittingTicket(sittingArea1, 1, 3);
        assertTrue(reservedTicket instanceof Reservation);
		/*ticket cannot be purchased nor reserved again*/
		assertFalse(ticket.purchase(client, new PassPay(new AnnualPass(sittingArea1, 2022))));
		/*ticket cannot be purchased nor reserved again*/
		assertFalse(ticket.reserve(client));
		
        ((Reservation)reservedTicket).cancel();
        // now it is available, not a reservation
        Ticket notReserved = p.getSittingTicket(sittingArea1, 1, 3);
        assertFalse(notReserved instanceof Reservation);
        assertTrue(notReserved.purchase(client, new PassPay(new AnnualPass(sittingArea1, 2022))));
        
		
        //another ticket
		ticket = p.getSittingTicket(sittingArea1, 1, 1);
		/*ticket can't be purchased with a pass from another year*/
		assertFalse(ticket.purchase(client, new PassPay(new AnnualPass(sittingArea1, 2021))));
		/*ticket can be purchased with a correct pass*/
		assertTrue(ticket.purchase(client, new PassPay(new AnnualPass(sittingArea1, 2022))));
		/*ticket cannot be purchased nor reserved again*/
		assertFalse(ticket.purchase(client, new PassPay(new AnnualPass(sittingArea1, 2022))));
		/*ticket cannot be purchased nor reserved again*/
		assertFalse(ticket.reserve(client));
		
		
		//another ticket--reservation
		ticket = p.getSittingTicket(sittingArea1, 3, 2);
		/*available ticket can be reserved*/
		assertTrue(ticket.reserve(client));
		/*ticket cannot be purchased nor reserved again*/
		assertFalse(ticket.purchase(client, new PassPay(new AnnualPass(sittingArea1, 2022))));
		/*ticket cannot be purchased nor reserved again*/
		assertFalse(ticket.reserve(client));
        
		reservedTicket = p.getSittingTicket(sittingArea1, 3, 2);
		/*check the ticket is a reservation*/
        assertTrue(reservedTicket instanceof Reservation);
        /*reserved tickets can't be purchased*/
        assertFalse(reservedTicket.purchase(client, new PassPay(new AnnualPass(sittingArea1, 2021))));
        boolean ret = ((Reservation)reservedTicket).confirm(new PassPay(new AnnualPass(sittingArea1, 2022)));
        assertTrue(ret); // it can be confirmed
        assertFalse(p.getTickets(sittingArea1).contains(reservedTicket));// not a reservation anymore
        ticket =  p.getSittingTicket(sittingArea1, 3, 2);
        /*confirm ticket is purchased*/
        assertTrue(ticket.isPurchased());
        
        System.out.printf("%s",p.printAreaState(sittingArea1)); //just to show the area state 
	}

}
