package app.theater.events;
import app.theater.events.cycles.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import app.theater.users.*;

import app.theater.areas.*;
import app.theater.*;
import app.theater.passes.*;
import app.theater.paymentmethod.*;
import app.theater.searches.SearchBy;
import app.theater.performances.*;
import app.theater.stats.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * Tester for the package events
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TestEvent {
	Application app;

	Event event1;
	Event event2 ;
	Event event3 ;

	StandingArea a1;
    SittingArea a2;

	Client client = new Client("pacuestas", "helloworld123");
	Client client2 = new Client("fernandez", "abcd");
	

	@Before
	public void setUp() {
		a1 = new StandingArea("A", 80);
	    a2 = new SittingArea("B", 5, 5);
		event1 = new Play("La Casa de Bernarda Alba", "description", 120, "Federico García Lorca", "Alvaro Zamanillo", "./aux_files/img/test_im.jpg", new ArrayList<>(Arrays.asList("Pepe", "Juana")));
		event2 = new Concert("Rock band", "descr", 60, "Pablo Cuesta", "John Doe", "./aux_files/img/test_im.jpg", "program", "orcherstra", new ArrayList<>(Arrays.asList("sol1", "sol2")));
		event3 = new Dance("Nutcracker", "description", 78, "author", "director", "./aux_files/img/test_im.jpg", new ArrayList<>(Arrays.asList("dan1", "dan2")), "conductor", "orchestra");

		app = Application.getNewInstanceForTests();
		app.addArea(a1);
		app.addArea(a2);
		app.addEvent(event1);
		app.addEvent(event2);
		app.addEvent(event3);
	}

	@Test
	public void testCycle() {
		Map<SimpleArea, Double> reductions = new HashMap<>();
		// reduction of each area
		reductions.put(a1, 25.);
		reductions.put(a2, 14.);

        Map<SimpleArea, Double> prices1 = new HashMap<>();
        Map<SimpleArea, Double> prices2 = new HashMap<>();
        Map<SimpleArea, Double> prices3 = new HashMap<>();
		prices1.put(a1, 15.);
	    prices1.put(a2, 10.0);
        event1.setPrices(prices1);
		prices2.put(a1, 10.);
	    prices2.put(a2, 12.0);
        event2.setPrices(prices2);
		prices3.put(a1, 12.);
	    prices3.put(a2, 8.0);
        event3.setPrices(prices3);

		Cycle cycle = new Cycle("cycle", new ArrayList<>(Arrays.asList(event1, event2, event3)), reductions);

		/*checks events belong to the cycle*/
		assertTrue(cycle.isEventValid(event1));
		assertTrue(cycle.isEventValid(event2));
		assertTrue(cycle.isEventValid(event3));

		Map<SimpleArea, Double> newprices = cycle.getPrices();

		double p1 = (double)newprices.get(a1);
		double p2 = (double)newprices.get(a2);
		
		// total price of performances in the cycle for each area, with the reduction applied for each area
		assertTrue(p1==37*.75); 
		assertTrue(p2==30*.86);
	}

	@Test
	public void testEvent1() {
		/*check getATR works well*/
		assertEquals(event1.getATR(SearchBy.DIRECTOR), "Alvaro Zamanillo");
		assertEquals(event2.getATR(SearchBy.AUTHOR), "Pablo Cuesta");
		assertEquals(event3.getATR(SearchBy.TITLE), "Nutcracker");
	}
	
	@Test
	public void testEvent2() {
		event1.addPrice(a1, 13.50);

        /*not all simple areas have a price, no performances should be added*/
        assertFalse(event1.addPerformance(LocalDate.of(2025,3,12))); 

        event1.addPrice(a2, 18.50);
        
        /*all simple areas have a price, performances might be added*/
        assertTrue(event1.addPerformance(LocalDate.of(2025,3,12))); 
	}
	
	@Test
	public void testEvent3() {
		event1.addPrice(a1, 13.50);
		event1.addPrice(a2, 18.50);

		/*Only correct restrictions can be set*/
		assertFalse(event1.setRestriction(101.));
		assertFalse(event1.setRestriction(-1.));
		assertTrue(event1.setRestriction(20.));
		event1.addPerformance(LocalDate.of(2019,3,12));// in the past
		/*there are only past performances so restrictions can be added*/
		assertTrue(event1.setRestriction(10.));
		event1.addPerformance(LocalDate.of(2023,5,19));// in the future
		/*there are future performances so restriction can't be added*/
		assertFalse(event1.setRestriction(5.));
	}

	@Test
	public void testEvent4() {
		/*checks method check prices (the event has prices for all areas)*/
		assertFalse(event1.checkPrices());
		event1.addPrice(a1, 13.50);
		assertFalse(event1.checkPrices());
		event1.addPrice(a2, 18.50);
		assertTrue(event1.checkPrices());
	}

	@Test
	public void testEvent5() {
		event1.addPrice(a1, 13.50);
		event1.addPrice(a2, 18.50);
		
		event1.addPerformance(LocalDate.of(2019,3,12));
		/*prices can be added/modified when there are past performances*/
		assertTrue(event1.addPrice(a1, 10.));
		
		event1.addPerformance(LocalDate.of(2023,5,19));
		/*prices can't be added/modified when there are future performances*/
		assertFalse(event1.addPrice(a2, 5.)); 
	}

    @Test
	public void testStats(){
		event1.addPrice(a1, 13.50);
		event1.addPrice(a2, 18.50);

		event1.addPerformance(LocalDate.of(2022, 3, 12));
		event1.addPerformance(LocalDate.of(2023, 4, 02));

		Performance p1=event1.getPerformances(false).get(0);
		Performance p2 = event1.getPerformances(false).get(1);

		PassPay method = new PassPay(new AnnualPass(a2, 2022));
		PassPay method2= new PassPay(new AnnualPass(a1,2023));
		PassPay method3 = new PassPay(new AnnualPass(a2, 2023));
		CreditCard method4= new CreditCard("1234567891234567");

		p1.getSittingTicket(a2, 1, 1).purchase(client, method);
		p1.getTickets(a1).get(0).purchase(client,method4);

		p2.getTickets(a1).get(1).purchase(client,method2);
		p2.getSittingTicket(a2, 1, 2).purchase(client,method3);

		/*check stats by area*/
		Stat stat1=event1.getStats();
		Stat stat2=event1.getStats(a2);

		Stat stat22 = event1.getStatsAreas().get(1);
		
		/*check getStatsAreas*/
		assertTrue(stat22.getRevenue()==stat2.getRevenue());
		assertTrue(stat22.getPurchasedWithPass()==stat2.getPurchasedWithPass());
		assertTrue(stat22.getAttendance()==stat2.getAttendance());
		assertTrue(stat22.getAttendancePercentage()==stat2.getAttendancePercentage());

		assertTrue(stat1.getRevenue()==event1.getPrice(a1)); // Only the ticket purchased with a card is taken into account
		assertTrue(stat2.getRevenue() == 0); //The tickets purchased with passes are not taken into account for the total revenue
		
		/*check stat purchased with pass*/
		assertTrue(stat1.getPurchasedWithPass()==3); 
		assertTrue(stat2.getPurchasedWithPass()==2);

		/*check attendance stat*/
		assertTrue(stat1.getAttendance() == 4);
		assertTrue(stat2.getAttendance() == 2);
		
		/*check attendance percentage stat*/
		assertTrue(stat1.getAttendancePercentage() == 100*4./210.);
		assertTrue(stat2.getAttendancePercentage() == 100*2./50.);

		/*check stats by performance*/
		Stat stat3 = event1.getStatsPerformances().get(0);
		Stat stat4 = event1.getStatsPerformances().get(1);

		assertTrue(stat3.getRevenue()==event1.getPrice(a1)); // Only the ticket purchased with a card is taken into account
		assertTrue(stat4.getRevenue() == 0); //The tickets purchased with passes are not taken into account for the total revenue
		
		/*check stat purchased with pass*/
		assertTrue(stat3.getPurchasedWithPass()==1);
		assertTrue(stat4.getPurchasedWithPass()==2);

		/*check attendance stat*/
		assertTrue(stat3.getAttendance() == 2);
		assertTrue(stat4.getAttendance() == 2);

		/*check attendance percentage stat*/
		assertTrue(stat3.getAttendancePercentage() == 100*2./105);
		assertTrue(stat4.getAttendancePercentage() == 100*2./105);
	}
}
