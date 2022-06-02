package app.theater.passes;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

import app.theater.Application;
import app.theater.areas.*;
import app.theater.events.*;
import app.theater.events.cycles.*;
import app.theater.performances.Performance;
import app.theater.users.Client;

/**
 * Tester for the package passes
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TestPass {
    Application app;

    CompositeArea compositeAreaA = new CompositeArea("A");
    SittingArea sittingAreaA1 = new SittingArea("A1", 9, 5);
    StandingArea standingAreaA2 = new StandingArea("A2", 12);
    StandingArea standingAreaB = new StandingArea("B", 12);
    
    Event event;
    Event event2;
    
    Cycle cycle;
    
    Performance perf;
    Performance perf2;
    Performance perf3;
    
    Client client;
    
    Map<SimpleArea, Double> reductions = new HashMap<>();
    
	@Before
	public void setUp() throws Exception {
		app = Application.getNewInstanceForTests();
		
		compositeAreaA.addArea(sittingAreaA1);
		compositeAreaA.addArea(standingAreaA2);
		
		app.addArea(compositeAreaA);
		app.addArea(standingAreaB);
		
		event = new Concert("Pablo live", "This is the description", 60, "Pablo ", "Álvaro Zamanillo", "./aux_files/img/test_im.jpg", "program", "orcherstra", "");
		event2 = new Concert("Pablo live2", "This is the description", 60, "Pablo ", "Álvaro Zamanillo", "./aux_files/img/test_im.jpg", "program", "orcherstra", "");
		
		Map<SimpleArea, Double> prices = new HashMap<>();
		
		prices.put(sittingAreaA1, 12.);
		prices.put(standingAreaA2, 13.);
		prices.put(standingAreaB, 11.);
		
		event.setPrices(prices);
		event2.setPrices(prices);
		
		reductions = new HashMap<>();

		reductions.put(sittingAreaA1, 10.);
		reductions.put(standingAreaA2, 10.);
		reductions.put(standingAreaB, 10.);
		
		cycle = new Cycle("cycle", new ArrayList<>(Arrays.asList(event, event2)), reductions);
		
		event.addPerformance(LocalDate.of(2023, 12, 4));
		event.addPerformance(LocalDate.of(2023, 4, 4));
		event2.addPerformance(LocalDate.of(2023, 5, 4));

		List<Performance> list = event.getPerformances(false);
		
        perf = list.get(0);
        perf2 = event.getPerformances(false).get(1);
        perf3 = event2.getPerformances(false).get(0);
        
    	client = new Client("pacuestas","helloworld123");
	}

	@Test
	public void testCyclePass() {		
		app.addCycle(cycle);
		
		CyclePass cyclePass = new CyclePass(sittingAreaA1, cycle);
		
		// buy any ticket for the first event
		assertFalse(cyclePass.use(perf.getAvailableTickets(standingAreaB).get(0)));//wrong area
		assertTrue(cyclePass.use(perf.getAvailableTickets(sittingAreaA1).get(0)));
		assertFalse(cyclePass.use(perf.getAvailableTickets(sittingAreaA1).get(3)));//not again for same performance
		assertFalse(cyclePass.use(perf2.getAvailableTickets(sittingAreaA1).get(2)));//not again for same event
		
		// buy any ticket for the second event
		assertTrue(cyclePass.use(perf3.getAvailableTickets(sittingAreaA1).get(2)));		
		
		cyclePass.restore(perf);
		assertTrue(cyclePass.use(perf2.getAvailableTickets(sittingAreaA1).get(3)));//ok now that it has been restored
		assertFalse(cyclePass.use(perf.getAvailableTickets(sittingAreaA1).get(3)));//not again for same event
	}
	
	@Test
	public void testAnnualPass() {		
		app.addCycle(cycle);
		
		AnnualPass annualPass = new AnnualPass(standingAreaA2, 2023);

		AnnualPass annualPassWrongYear = new AnnualPass(standingAreaA2, 2022);
		
		// buy any ticket for the first perf
		assertFalse(annualPass.use(perf.getAvailableTickets(standingAreaB).get(0)));//wrong area
		assertFalse(annualPassWrongYear.use(perf.getAvailableTickets(standingAreaA2).get(0)));//wrong year
		assertTrue(annualPass.use(perf.getAvailableTickets(standingAreaA2).get(0)));
		assertFalse(annualPass.use(perf.getAvailableTickets(standingAreaA2).get(3)));//not again for same performance
		
		//ok for a different performance of the same event
		assertTrue(annualPass.use(perf2.getAvailableTickets(standingAreaA2).get(2)));
		assertFalse(annualPassWrongYear.use(perf2.getAvailableTickets(standingAreaA2).get(2)));//wrong year
		
		// buy any ticket for the second event
		assertTrue(annualPass.use(perf3.getAvailableTickets(standingAreaA2).get(2)));		
		
		annualPass.restore(perf);
		assertFalse(annualPass.use(perf2.getAvailableTickets(standingAreaA2).get(3)));//not ok for this perf
		assertTrue(annualPass.use(perf.getAvailableTickets(standingAreaA2).get(3)));//ok for this performance
	}
	
	@Test
	public void testEqualsPass() {
		AnnualPass annualPass = new AnnualPass(standingAreaA2, 1987);
		AnnualPass annualPass2 = new AnnualPass(standingAreaA2, 1987);
		AnnualPass annualPass3 = new AnnualPass(standingAreaA2, 1983);
		AnnualPass annualPass4 = new AnnualPass(standingAreaB, 1983);

		/*checks an annual pass is equal to another iff the area and the year is the same*/
		assertFalse(annualPass4.equals(annualPass3));
		assertTrue(annualPass.equals(annualPass2));
		assertTrue(annualPass2.equals(annualPass));
		assertFalse(annualPass.equals(annualPass3));
		assertFalse(annualPass2.equals(annualPass3));
		
		CyclePass cyclePass = new CyclePass(standingAreaA2, cycle);
		CyclePass cyclePass2 = new CyclePass(standingAreaA2, cycle);
		CyclePass cyclePass3 = new CyclePass(standingAreaB, cycle);
		CyclePass cyclePass4 = new CyclePass(standingAreaB, new Cycle("kljh dfk",  new ArrayList<>(Arrays.asList(event, event2)), reductions));

		/*checks a cycle pass is equal to another iff the cycle and the area is the same*/
		assertTrue(cyclePass.equals(cyclePass2));
		assertFalse(cyclePass.equals(cyclePass3));
		assertFalse(cyclePass.equals(cyclePass4));
		assertFalse(cyclePass2.equals(cyclePass3));
		assertFalse(cyclePass2.equals(cyclePass4));
		assertFalse(cyclePass4.equals(cyclePass3));
		
		/*passes of different type aren't equal*/
		assertFalse(cyclePass.equals(annualPass4));
		

		List <Pass> list = new ArrayList<>();
		
		list.add(annualPass);
		
		/*check method contains*/
		assertTrue(list.contains(annualPass2));
	}

}
