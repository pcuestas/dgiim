package app.theater.performances;

import static org.junit.Assert.*;
import java.util.*;
import java.time.LocalDate;

import app.theater.*;
import app.theater.areas.*;
import app.theater.events.*;
import app.theater.passes.*;
import app.theater.paymentmethod.*;
import app.theater.users.*;
import app.theater.stats.*;

import app.theater.performances.tickets.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Tester for the performance package
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TestPerformance {
	Application app;
	Event event;

	Client client;

	SittingArea sittingArea1;
	SittingArea sittingArea2;
	StandingArea standingArea;
	CompositeArea compositeArea;

	@Before
	public void setUp() throws Exception {
		app = Application.getNewInstanceForTests();
		
		sittingArea1 = new SittingArea("sitarea1", 30, 50);
		sittingArea2 = new SittingArea("sitarea2", 10, 5);
		standingArea = new StandingArea("staarea", 60);
		compositeArea = new CompositeArea("comp");

		compositeArea.addArea(sittingArea2);
		compositeArea.addArea(standingArea);
		
		client = new Client("pacuestas", "helloworld123");
		event = new Concert("title", "descr", 60, "author", "dir", "./aux_files/img/test_im.jpg", "program", "orcherstra",
				"");

		app.addArea(sittingArea1);
		app.addArea(compositeArea);
		
		event.addPrice(sittingArea1,13);
		event.addPrice(sittingArea2,15);
		event.addPrice(standingArea,10);
		
		event.addPerformance(LocalDate.of(2022, 3, 12));	
		
	}


	@Test
	public void furthestSelection() {

		int[][] indexPurchase = new int[][]{{3,2},{2,5},{17,23},{19,25},{10,4},{10,5},
											 {3,3},{3,4},{3,5},{28,40},{28,41},{28,42}};

        Performance perf=event.getPerformances(false).get(0);
        
        int j = 0;
		
		for(int[] i : indexPurchase){
			PassPay method = new PassPay(new AnnualPass(sittingArea1, 2022));
			boolean flag = perf.getSittingTicket(sittingArea1,i[0],i[1]).purchase(client,method);// only the first ten
			if(j < Application.getInstance().getMaxTicketsPurchase()) {
				/*true while the maximum number of tickets to purchase by a client isn't surpassed*/
				assertTrue(flag);
			}else {
				/*false when it's surpassed*/
				assertFalse(flag);
			}
			j++;
		}
		
		// restrict some (not counted for the center of mass)
		perf.getSittingTicket(sittingArea1, 28, 48).restrict();
		perf.getSittingTicket(sittingArea1, 28, 49).restrict();
		perf.getSittingTicket(sittingArea1, 29, 48).restrict();
		
		
		List <Ticket> selected = perf.selectAutomatic(sittingArea1, 3, AutomaticSelectionType.FURTHEST);
		
		// the following seats must have been selected (the closest to the corner opposite to the center of mass-of purchased tickets)
		List<Integer[]> checkSelection = new ArrayList<>(Arrays.asList(new Integer[] {29, 47},
																		new Integer[] {27, 49},
																		new Integer[] {29, 49}));
		/*check it has selected three tickets*/
		assertTrue(selected.size() == 3);

		/*check the selected tickets are the ones furthest away from the center of mass*/
		for(Ticket t: selected){
			boolean flag = false;
			
			for(Integer[] i: checkSelection) {
				if(t.getLocation() == perf.getSittingTicket(sittingArea1, i[0], i[1]).getLocation()) {
					checkSelection.remove(checkSelection.lastIndexOf(i));
					flag = true;
					break;
				}
			}
			
			assertTrue(flag);
		}
		
		/*check all selected tickets were tested*/
		assertTrue(checkSelection.isEmpty());
	}
	
	

	@Test
	public void centeredSelection(){
		Performance perf=event.getPerformances(false).get(0);
		
		List <Ticket> selected = perf.selectAutomatic(sittingArea1, 5, AutomaticSelectionType.CENTERED);

		// the following seats must have been selected (the closest to the center)
		List<Integer[]> checkSelection = new ArrayList<>(Arrays.asList(new Integer[] {15, 25},
																		new Integer[] {15, 26},
																		new Integer[] {15, 24},
																		new Integer[] {14, 25},
																		new Integer[] {16, 25}));
		/*check it has selected five tickets*/
		assertTrue(selected.size()==5);
		
		/*check the selected tickets are the ones closest to the center*/
		for(Ticket t: selected){
			boolean flag = false;
			
			for(Integer[] i: checkSelection) {
				if(t.getLocation() == perf.getSittingTicket(sittingArea1, i[0], i[1]).getLocation()) {
					checkSelection.remove(checkSelection.lastIndexOf(i));
					flag = true;
					break;
				}
			}
			
			assertTrue(flag);
		}
		
		/*check all selected tickets were tested*/
		assertTrue(checkSelection.isEmpty());
	}
	
	@Test
	public void centeredUpperSelection(){
		Performance perf=event.getPerformances(false).get(0);
		
		perf.getSittingTicket(sittingArea1, 29, 26).restrict(); //now unavailable
		
		List <Ticket> selected = perf.selectAutomatic(sittingArea1, 3, AutomaticSelectionType.CENTEREDUPPER);

		// the following seats must have been selected (the closest to the center, upper rows)
		List<Integer[]> checkSelection = new ArrayList<>(Arrays.asList(new Integer[] {29, 25},
																		new Integer[] {29, 24},
																		new Integer[] {28, 25}));
		/*check it has selected three tickets*/
		assertTrue(selected.size()==3);
		
		/*check the selected tickets are as desired*/
		for(Ticket t: selected){
			boolean flag = false;
			
			for(Integer[] i: checkSelection) {
				if(t.getLocation() == perf.getSittingTicket(sittingArea1, i[0], i[1]).getLocation()) {
					checkSelection.remove(checkSelection.lastIndexOf(i));
					flag = true;
					break;
				}
			}
			
			assertTrue(flag);
		}
		
		/*check all selected tickets were tested*/
		assertTrue(checkSelection.isEmpty());
	}
	
	@Test
	public void centeredLowerSelection(){
		Performance perf=event.getPerformances(false).get(0);

		perf.getSittingTicket(sittingArea1, 0, 26).restrict();
		perf.getSittingTicket(sittingArea1, 1, 24).restrict();
		perf.getSittingTicket(sittingArea1, 0, 25).restrict();
		
		
		List <Ticket> selected = perf.selectAutomatic(sittingArea1, 5, AutomaticSelectionType.CENTEREDLOWER);

		// the following seats must have been selected (the closest to the center, upper rows)
		List<Integer[]> checkSelection = new ArrayList<>(Arrays.asList(new Integer[] {0, 23}, // dist 2
																		new Integer[] {0, 24}, // dist 1
																		new Integer[] {1, 25}, // dist 1
																		new Integer[] {1, 26}, // dist 2
																		new Integer[] {0, 27})); // dist 2
		/*check all five tickets were selected*/
		assertTrue(selected.size()==5);
		
		for(Ticket t: selected){
			boolean flag = false;
			
			for(Integer[] i: checkSelection) {
				if(t.getLocation() == perf.getSittingTicket(sittingArea1, i[0], i[1]).getLocation()) {
					checkSelection.remove(checkSelection.lastIndexOf(i));
					flag = true;
					break;
				}
			}
		
			assertTrue(flag);
		}
		/*check all selected tickets were tested*/
		assertTrue(checkSelection.isEmpty());
	}

	@Test
	public void testStats(){
		Performance performance=event.getPerformances(false).get(0);

		CreditCard method= new CreditCard("1234567891234567");
		PassPay method1 = new PassPay(new AnnualPass(standingArea, 2022));

		performance.getSittingTicket(sittingArea1, 1, 1).purchase(client, method);
		performance.getSittingTicket(sittingArea1, 1, 4).purchase(client,method);
		performance.getTickets(standingArea).get(0).purchase(client,method1);
		performance.getTickets(standingArea).get(1).purchase(client, method);
		performance.getSittingTicket(sittingArea2, 1, 2).purchase(client,method);


		Stat stat1=event.getStats();
		Stat stat2=event.getStats(sittingArea1);
		Stat stat22 = event.getStatsAreas().get(0);
		Stat stat3=event.getStats(compositeArea);

		/*check getStatsAreas method*/
		assertTrue(stat22.getRevenue()==stat2.getRevenue());
		assertTrue(stat22.getPurchasedWithPass()==stat2.getPurchasedWithPass());
		assertTrue(stat22.getAttendance()==stat2.getAttendance());
		assertTrue(stat22.getAttendancePercentage()==stat2.getAttendancePercentage());

		/*check stat revenue*/
		assertTrue(stat1.getRevenue()==(2*event.getPrice(sittingArea1) + event.getPrice(standingArea) + event.getPrice(sittingArea2) )); // Only the tickets purchased with a card are taken into account
		assertTrue(stat2.getRevenue() == 2*event.getPrice(sittingArea1)); 
		assertTrue(stat3.getRevenue() == event.getPrice(standingArea) + event.getPrice(sittingArea2));

		/*check stat purchased with pass*/
		assertTrue(stat1.getPurchasedWithPass()==1);
		assertTrue(stat2.getPurchasedWithPass()==0);
		assertTrue(stat3.getPurchasedWithPass()==1);

		/*check stat attendance*/
		assertTrue(stat1.getAttendance() == 5);
		assertTrue(stat2.getAttendance() == 2);
		assertTrue(stat3.getAttendance() == 3);
		
		/*check stat attendance percentage*/
		assertTrue(stat1.getAttendancePercentage() == 100*5./1610.);
		assertTrue(stat2.getAttendancePercentage() == 100*2./1500.);
		assertTrue(stat3.getAttendancePercentage() == 100*3./110.);
	}
	
}

