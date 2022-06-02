package app.theater.areas.locations;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import app.theater.areas.*;
import app.theater.util.*;
import java.time.*;

/**
 * Location package tester
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TestLocation {
    SittingArea sittingArea = new SittingArea("A", 9, 5);
    StandingArea standingArea = new StandingArea("B", 12);

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSeat() {
		/*initialize seats*/
        Seat[][] s = Seat.initSeats(sittingArea);
		
        /*disable one seat*/
		s[2][3].disable(LocalDate.of(2021,1,12), LocalDate.of(2021,10,15));

        for(int i=0; i < sittingArea.getRows(); i++ ){
            for(int j=0; j < sittingArea.getColumns(); j++ ){
            	/*for the disabled seat*/
                if(i==2 && j==3){
                	/*should not be disabled before the interval of disablement*/
                    assertFalse(s[i][j].isDisabled(LocalDate.of(2008,10,15)));
                    /*should be disabled in the interval of disablement*/
                    assertTrue(s[i][j].isDisabled(LocalDate.now()));
                    /*should not be disabled after the interval of disablement*/
                    assertFalse(s[i][j].isDisabled(LocalDate.of(2022,10,15)));
                }else{
                	/*for the rest of seats, shouldn't be disabled*/
                    assertFalse(s[i][j].isDisabled(LocalDate.now()));
                }
			}
		}
	}

    @Test
	public void testInterval() {
        Interval i = new Interval(LocalDate.of(2020,12,3), LocalDate.of(2021,4,1));
        /*date before interval should not belong to interval*/
        assertFalse(i.belongsToInterval(LocalDate.of(2020, 12, 1)));
        /*start date of interval should belong to interval*/
        assertTrue(i.belongsToInterval(LocalDate.of(2020, 12, 3)));
        /*should belong to interval*/
        assertTrue(i.belongsToInterval(LocalDate.of(2020, 12, 31)));
        /*should belong to interval*/
        assertTrue(i.belongsToInterval(LocalDate.of(2021, 2, 28)));
        /*date after interval should not belong to interval*/
        assertFalse(i.belongsToInterval(LocalDate.of(2021, 4, 2)));
    }

    @Test
	public void testStandingLocation() {
        Location l = new Location(standingArea); // in a standing area
        
        /*location's area should be the standing area*/
        assertTrue(standingArea == l.getArea());
        /*location shouldn't be disabled*/
        assertFalse(l.isDisabled(LocalDate.of(2020,12,3)));
        /*location shouldn't be disabled*/
        assertFalse(l.isDisabled(LocalDate.of(2020, 12, 3)));
    }
	
}
