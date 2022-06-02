package app.theater;

import app.theater.areas.*;
import app.theater.events.*;
import app.theater.users.*;
import app.theater.searches.*;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * TestApplication Class.
 * 
 * On the application: the admin username is "admin", and its password is "pass"
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class TestApplication {
	Application app;
	private static RegUser u1= new Client("pacuestas","helloworld123");

	private static CompositeArea cAreaA = new CompositeArea("cAreaA");
	private static SittingArea sitA1 = new SittingArea("sitA1", 12, 16);
	private static SittingArea sitB = new SittingArea("sitB", 20, 14);
	private static StandingArea stanA2 = new StandingArea("stanA2", 56);
	
	
	private static Event e1 = new Concert("Best Performance", "descr", 60, "author", "Pablo Cuesta", "", "program", "orcherstra", "");
	private static Event e2 = new Play("Two besties","descr2 ",30," author","Alvaro Zamanillo"," ", "");
	private static Event e3= new Dance("tit3", "descr3",45,"auth","Pablo Fernandez"," ", "cond, orchestra", "", "");

	@Before
	public void setUp() throws Exception {
		app = Application.getNewInstanceForTests();
		app.register("pacuestas","helloworld123");
		app.register("fernandes","abdc");

		app.addEvent(e1);
		app.addEvent(e2);
		app.addEvent(e3);

		cAreaA.addArea(sitA1);
		cAreaA.addArea(stanA2);
		app.addArea(cAreaA);
		app.addArea(sitB);
		
		Map <SimpleArea, Double> prices1 = new HashMap<>();
		prices1.put(sitA1, 10.);
		prices1.put(sitB, 12.);
		prices1.put(stanA2, 8.);
		e1.setPrices(prices1);
		
		Map <SimpleArea, Double> prices2 = new HashMap<>();
		prices2.put(sitA1, 10.);
		prices2.put(sitB, 12.);
		prices2.put(stanA2, 8.);
		e2.setPrices(prices2);
		
		Map <SimpleArea, Double> prices3 = new HashMap<>();
		prices3.put(sitA1, 10.);
		prices3.put(sitB, 12.);
		prices3.put(stanA2, 8.);
		e3.setPrices(prices3);
	}


	@Test public void uniqueUsername(){
		
		/*Usernames must be unique, therefore register should return false */
		assertFalse(app.register("pacuestas","otherpassword3410"));

	}

	@Test
	public void testLogin() {
		boolean flag;
		
		flag=app.login("pacuestas","helloworld123");

		/*Check that the login is successfull*/
		assertTrue(flag);

		/*Check that the current user has been updated correctly*/
		assertEquals(u1,app.getCurrentUser());
	}


	@Test
	public void testLogin2(){
		boolean flag;

		/*Check that login is unsuccessful with wrong password*/
		flag=app.login("pacuestas","INCORRECT");
		assertFalse(flag);
	}


	@Test 
	public void testLogin3(){
		boolean flag;
		/*Check that login is unsuccessful if there is a current user that has not logoout*/
		app.login("pacuestas", "helloworld123");
		flag=app.login("fernandes","abdc");

		assertFalse(flag);

	}

	@Test
	public void testLogOut(){
		app.login("pacuestas", "helloworld123");
		app.logout();
		assertEquals(app.getCurrentUser(),null);
	}

	@Test
	public void testLogin4(){
		boolean flag;

		/*Check that login is successful after the previous user has logout*/
		app.login("pacuestas", "helloworld123");
		app.logout();
		flag = app.login("fernandes", "abdc");

		assertTrue(flag);
	}


	@Test
	public void testSearchEventByTitle(){
		List <Event> res;

		res=app.searchEvents(SearchBy.TITLE, "best");

		assertTrue(res.contains(e1));
		assertTrue(res.contains(e2));
		assertFalse(res.contains(e3));

	}

	@Test
	public void testSearchEventByDirector(){
		List<Event> res;

		res = app.searchEvents(SearchBy.DIRECTOR, "Pablo");

		assertTrue(res.contains(e1));
		assertFalse(res.contains(e2));
		assertTrue(res.contains(e3));
	}

	@Test
	public void testSearchEventByAuthor(){
		List<Event> res;
    
		res = app.searchEvents(SearchBy.AUTHOR,"John");

		assertFalse(res.contains(e1));
		assertFalse(res.contains(e2));
		assertFalse(res.contains(e3));
	}

	@Test
	public void testPersistance() {
		app.writeToFile();
		
		SittingArea s1 = new SittingArea("s1", 1, 1);
		
		assertTrue(app.addArea(s1));
		assertTrue(app.getSimpleAreas().contains(s1));
		
        app = Application.getInstanceFromFile(); // read the file after writing in it

        app = Application.getInstance(); // not necessary, but to check the instance has been changed 

        assertTrue(app.getSimpleAreas().contains(sitA1));
        assertFalse(app.getSimpleAreas().contains(s1)); // modified after writing the app into a file
	}

}
