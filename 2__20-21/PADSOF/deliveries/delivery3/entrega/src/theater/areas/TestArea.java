package app.theater.areas;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import app.theater.*;
import app.theater.events.*;

/**
 * Tester for the package areas
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TestArea {
    Application app;
    Event event= new Concert("title", "descr", 60, "author", "dir", "", "program", "orcherstra", new ArrayList<>(Arrays.asList("sol1", "sol2")));

    
	@Before
	public void setUp() throws Exception {
		/*new app for each tester*/
		app = Application.getNewInstanceForTests();
	}

	@Test
	public void testAddSimpleArea1() {
		SimpleArea a = new SittingArea("A", 12, 4);
		/*area should be added correctly*/
		assertTrue(app.addArea(a));
	}
    @Test
	public void testAddSimpleArea2() {
		app.addEvent(event); 
		
		SimpleArea a = new SittingArea("A", 11, 4);
		/*sitting area should be added correctly when there are no performances scheduled*/
		assertTrue(app.addArea(a));
        event.addPrice(a, 13.50);

        event.addPerformance(LocalDate.of(2022,3,12)); 

		SimpleArea b = new StandingArea("B", 50);
		/*there is a performance, no new areas should be created*/
		assertFalse(app.addArea(b));
	}

    @Test
	public void testAddSimpleArea3() {
		app.addEvent(event); 
		
		SimpleArea a = new StandingArea("B", 50);
		/*standing area should be added correctly when there are no performances scheduled*/
		assertTrue(app.addArea(a));
        event.addPrice(a, 13.50);

        event.addPerformance(LocalDate.of(2020,3,12)); 
        
		SimpleArea b = new SittingArea("A", 11, 4);
		/*there is a performance but has already passed, new areas might be created*/
		assertTrue(app.addArea(b));		
	}
    
	@Test
	public void testCompositeArea1() {
		CompositeArea a = new CompositeArea("Comp");

		/*no empty composite areas should be added*/
		assertFalse(app.addArea(a));

        StandingArea a1 = new StandingArea("A", 80);
        SittingArea a2 = new SittingArea("B", 5,5);

		a.addArea(a1);
		a.addArea(a2);
		
		/*capacity of the composite area should be the sum of the capacity of it's simpler areas*/
		assertEquals(a.getCapacity(), 105);
		/*area can now be added to the theater*/
		assertTrue(app.addArea(a));

        event.addPrice(a1, 13.50);

        /*not all simple areas have a price, no performances should be added*/
        assertFalse(event.addPerformance(LocalDate.of(2025,3,12))); 

        event.addPrice(a2, 18.50);
        
        /*all simple areas have a price, performances might be added*/
        assertTrue(event.addPerformance(LocalDate.of(2025,3,12))); 
	}
	
	@Test
	public void testEquals() {
		CompositeArea comp = new CompositeArea("Comp");
		SimpleArea a = new SittingArea("A", 11, 4);
		SimpleArea b = new StandingArea("B", 50);
		
		comp.addArea(a);
		/*checks composite area contains the simple one*/
		assertTrue(comp.getSimpleAreas().contains(new SittingArea("A", 11, 4)));
		/*checks two areas are equal if all parameters are equal*/
		assertFalse(comp.getSimpleAreas().contains(new SittingArea("A", 112, 4)));
		
		/*check method equals trivially*/
		assertTrue(a.equals(new SittingArea("A", 11, 4)));
		assertFalse(a.equals(new StandingArea("B", 50)));
		assertTrue(b.equals(new StandingArea("B", 50)));
		assertFalse(b.equals(new StandingArea("By", 50)));
	}
}
