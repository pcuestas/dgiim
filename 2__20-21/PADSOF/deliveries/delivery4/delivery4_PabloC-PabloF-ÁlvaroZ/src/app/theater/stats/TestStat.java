package app.theater.stats;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;
import app.theater.*;
import app.theater.areas.*;
import app.theater.events.*;
import app.theater.performances.*;

/**
 * Tester for the package stats
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TestStat {

    Application app;

	Event event1 = new Play("La Casa de Bernarda Alba", "description", 120, "Federico García Lorca", "Alvaro Zamanillo",
				"./aux_files/img/test_im.jpg", "");
	Event event2 = new Concert("Rock band", "descr", 60, "Pablo Cuesta", "John Doe", "./aux_files/img/test_im.jpg", "program",
				"orcherstra", "");
	Event event3 = new Dance("Nutcracker", "description", 78, "author", "director", "./aux_files/img/test_im.jpg", 
				"", "conductor", "orchestra");


	Performance performance1;
	Performance performance2;
	Performance performance3;

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

		app.addArea(sittingArea1);
		app.addArea(compositeArea);
		
		event1.addPrice(sittingArea1,13);
		event1.addPrice(sittingArea2,15);
		event1.addPrice(standingArea,10);

		event2.addPrice(sittingArea1,12);
		event2.addPrice(sittingArea2,14);
		event2.addPrice(standingArea,8);
		
		event1.addPerformance(LocalDate.of(2022, 3, 12));
		event1.addPerformance(LocalDate.of(2023, 5, 1));
		performance1 = event1.getPerformances().get(0);	
		performance2 = event1.getPerformances().get(1);
		event2.addPerformance(LocalDate.of(2022, 4, 6));
		performance3 = event2.getPerformances().get(0);
	}

	@Test
	public void testComparator() {
		Stat s1 = new Stat(event1, performance1, null, 100, 20, 1, 200);
		Stat s2 = new Stat(event1, performance2, standingArea, 60, 40, 2, 200);
		Stat s3 = new Stat(event1, performance2, sittingArea1, 31, 20, 2, 100);
		Stat s4 = new Stat(event1, null, sittingArea1, 500, 150, 10, 200);
		Stat s5 = new Stat(event2, performance3, null, 10, 2, 1, 200);
		Stat s6 = new Stat(event2, performance3, sittingArea2, 340, 87, 15, 300);
		Stat s7 = new Stat(event2, performance3, sittingArea2, 400, 150, 50, 800);
		Stat s8 = new Stat(event3, null, standingArea, 2460, 2500, 40, 3000);
		Stat s9 = new Stat(null, null, standingArea, 30, 4, 2, 100);
		Stat s10 = new Stat(null, null, sittingArea2, 90, 100, 40, 500);
		
		/*check StatComparator*/
		assertTrue(StatComparator.REVENUE.compare(s1,s2) > 0);
		assertTrue(StatComparator.REVENUE.compare(s2,s1) < 0);
        assertTrue(StatComparator.ATTENDANCE.compare(s2,s1) > 0);
        assertTrue(StatComparator.ATTENDANCE.compare(s2,s3) == 0);

		List<Stat> list = new ArrayList<>();	

		list.add(s2);
		list.add(s1);
		list.add(s3);
		list.add(s9);
		list.add(s6);
		list.add(s10);
		list.add(s4);
		list.add(s7);
		list.add(s8);
		list.add(s5);
        
		/*check sorting by revenue*/
		Stat.sortStats(list, StatComparator.REVENUE, false);
		assertTrue(list.get(0) == s5);
		assertTrue(list.get(1) == s9);
		assertTrue(list.get(2) == s3);

		Stat.sortStats(list, StatComparator.REVENUE, true);
		assertTrue(list.get(0) == s8);
		assertTrue(list.get(1) == s4);
		assertTrue(list.get(2) == s7);

		/*check sorting by attendance*/
        Stat.sortStats(list, StatComparator.ATTENDANCE, true);
		assertTrue(list.get(0) == s8);
		assertTrue(list.get(1) == s4);
		
		Stat.sortStats(list, StatComparator.ATTENDANCE, false);
		assertTrue(list.get(0) == s5);
		assertTrue(list.get(1) == s9);
		assertTrue(list.get(9) == s8); 

		list.remove(s9);
		list.remove(s10);
		
		/*check sorting by event*/
		Stat.sortStats(list, StatComparator.EVENT, false);
		assertTrue(list.get(0).getEvent() == event1); 
		assertTrue(list.get(4).getEvent() == event3); 
		assertTrue(list.get(7).getEvent() == event2); 
		assertTrue(list.get(0).getEvent() == list.get(1).getEvent());
		assertTrue(list.get(7).getEvent() == list.get(6).getEvent());

		Stat.sortStats(list, StatComparator.EVENT, true);
		assertTrue(list.get(0).getEvent() == event2); 
		assertTrue(list.get(3).getEvent() == event3); 
		assertTrue(list.get(7).getEvent() == event1); 
		assertTrue(list.get(0).getEvent() == list.get(1).getEvent());
		assertTrue(list.get(7).getEvent() == list.get(6).getEvent());

		list.remove(s8);
		list.remove(s4);
		
		/*check sorting by performance*/
		Stat.sortStats(list, StatComparator.PERFORMANCE, true);
		assertTrue(list.get(5) == s1);
		assertTrue(list.get(4).getPerformance() == performance3);
		assertTrue(list.get(3).getPerformance() == performance3);
		assertTrue(list.get(2).getPerformance() == performance3);
		assertTrue(list.get(1).getPerformance() == performance2);
		assertTrue(list.get(0).getPerformance() == performance2);

		Stat.sortStats(list, StatComparator.PERFORMANCE, false);
		assertTrue(list.get(0) == s1);
		assertTrue(list.get(1).getPerformance() == performance3);
		assertTrue(list.get(2).getPerformance() == performance3);
		assertTrue(list.get(3).getPerformance() == performance3);
		assertTrue(list.get(4).getPerformance() == performance2);
		assertTrue(list.get(5).getPerformance() == performance2);

		list = new ArrayList<>();
		list.add(s2);
		list.add(s3);
		list.add(s9);
		list.add(s6);
		list.add(s10);
		list.add(s7);
		list.add(s8);
		list.add(s4);
		
		/*check sorting by area*/
		Stat.sortStats(list, StatComparator.AREA, false);
		assertTrue(list.get(0).getArea() == sittingArea1);
		assertTrue(list.get(1).getArea() == sittingArea1);
		assertTrue(list.get(2).getArea() == sittingArea2);
		assertTrue(list.get(3).getArea() == sittingArea2);
		assertTrue(list.get(4).getArea() == sittingArea2);
		assertTrue(list.get(5).getArea() == standingArea);
		assertTrue(list.get(6).getArea() == standingArea);
		assertTrue(list.get(7).getArea() == standingArea);

		Stat.sortStats(list, StatComparator.AREA, true);
		assertTrue(list.get(7).getArea() == sittingArea1);
		assertTrue(list.get(6).getArea() == sittingArea1);
		assertTrue(list.get(5).getArea() == sittingArea2);
		assertTrue(list.get(4).getArea() == sittingArea2);
		assertTrue(list.get(3).getArea() == sittingArea2);
		assertTrue(list.get(2).getArea() == standingArea);
		assertTrue(list.get(1).getArea() == standingArea);
		assertTrue(list.get(0).getArea() == standingArea);

	}

}
