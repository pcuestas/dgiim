package app.theater;

import app.theater.areas.*;
import app.theater.events.*;
import app.theater.searches.*;
import app.theater.stats.Stat;
import app.theater.stats.StatComparator;
import app.theater.performances.*;
import app.theater.performances.tickets.*;
import app.theater.users.*;
import app.theater.paymentmethod.*;
import app.theater.util.*;
import app.theater.events.cycles.*;

import java.util.*;
import java.time.*;
/**
 * ApplicationDemonstrator Class.
 * 
 * On the application: the admin username is "admin", and its password is "pass"
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class ApplicationDemonstrator {

    private static String adminName = "admin";
    private static String adminPass = "pass";
    
    private static String descrBernarda = "Escrita por Federico García Lorca en 1936, poco antes de ser asesinado, La casa de Bernarda Alba es una de las grandes obras del teatro español y universal.";
	private static String nutcrackerDescr = "'The Nutcracker' is a ballet in two acts based on the story by E.T. Amadeus Hoffmann 'The Nutcracker and the king of mice'.";

	public static void main(String[] args) {
		Application app = Application.getInstance();
		ModifiableDate.setDate(12, 4, 2021);// for the simulation of dates in this main

		/****************************************************** CONFIGURATION ********************************************* */

		app.login(adminName, adminPass, ModifiableDate.getModifiableDate());
		
        /* the admin configures the app at the beginning */
		app.setMaxTicketsPurchase(15);
		app.setMaxTicketsReservation(10);
		app.setTimeAfterReservation(3);
		
		System.out.println("Theater configuration: \nMaxTicketPurchase: "+app.getMaxTicketsPurchase()+"\nMaxTicketReservation: "+app.getMaxTicketsReservation()+"\n\n");
		
		
		/* admin configures areas */
        

        /* create the areas */        
        CompositeArea compositeAreaA = new CompositeArea("A");
	    SittingArea sittingAreaA1 = new SittingArea("A1", 8, 12);
        compositeAreaA.addArea(sittingAreaA1);
	    StandingArea standingAreaA2 = new StandingArea("A2", 56);
        compositeAreaA.addArea(standingAreaA2);
	    SittingArea sittingAreaB = new SittingArea("B", 20, 14);
        
		/* add the areas to the theater */
        app.addArea(compositeAreaA);
        app.addArea(sittingAreaB);

        /* set annual pass prices */
        app.setAnnualPassPrice(sittingAreaA1, 120.);
        app.setAnnualPassPrice(standingAreaA2, 100.);
        app.setAnnualPassPrice(sittingAreaB, 129.);

        /* create events */
        Event event1 = new Play("La Casa de Bernarda Alba", descrBernarda , 120, "Federico García Lorca", "Alvaro Zamanillo", "./aux_files/img/bernarda.jpg", "Pepe, Juana");
        Event event2 = new Concert("Rock band", "description...", 60, "Pablo Cuesta", "John Doe", "./aux_files/img/mayertomo.png", "program", "orcherstra", "sol1, sol2");
        Event event3 = new Dance("Nutcracker", nutcrackerDescr, 78, "author", "director", "./aux_files/img/nutcracker.jpg", "dan1, dan2", "conductor", "orchestra");

        
		/* add events */
		app.addEvent(event1);
		app.addEvent(event2);
		app.addEvent(event3);

		/* admin adds performances*/
		event1.addPrice(sittingAreaA1, 12);
		event1.addPrice(standingAreaA2, 25);
		event1.addPrice(sittingAreaB, 17);

		event1.setRestriction(20.);


		event2.addPrice(sittingAreaA1, 19.5);
		event2.addPrice(standingAreaA2, 40);
		event2.addPrice(sittingAreaB, 13.5);

		event3.addPrice(sittingAreaA1, 29.24);
		event3.addPrice(standingAreaA2, 30);
		event3.addPrice(sittingAreaB, 63.5);
		
		/*Admin creates a cycle with all the events*/
		Map<SimpleArea, Double> cycleReductions = new HashMap<>();
		cycleReductions.put(sittingAreaA1, 15.75);
		cycleReductions.put(sittingAreaB,10.75);
		cycleReductions.put(standingAreaA2,5.0);
		
		app.addCycle(new Cycle("All events", app.getEvents(), cycleReductions));
		
        /*Admin disables seats (that will be disable in the date of performance1) */
		List<Integer[]> disableSelection = new ArrayList<>(
                Arrays.asList(new Integer[] { 6, 5 }, new Integer[] { 5, 11 }, new Integer[] { 1, 10 }));
        
        for(Integer[] index: disableSelection){
            sittingAreaA1.getSeat(index[0], index[1]).disable(LocalDate.of(2021,10,12), LocalDate.of(2021,10,14));
        }
        
        /*create performances*/
		event1.addPerformance(LocalDate.of(2021,10,13));
		event1.addPerformance(LocalDate.of(2022, 3, 4));
		event2.addPerformance(LocalDate.of(2021, 9,17));
		event3.addPerformance(LocalDate.of(2021, 1,12));

		System.out.println("State of SittingArea1 with the restrictions (20%) of event1 (taking into account the disabled seats):");
		System.out.println(event1.getPerformances(false).get(0).printAreaState(sittingAreaA1));
        System.out.println("#######################################################");

        
        app.logout(); // admin logs out

		/* 3 clients register */
        app.register("pacuestas","helloworld123");
		app.register("fernandes","abcd123");
		app.register("azamanis","qwerty");



		/******************************************** SELECTION / PURCHASE / RESERVE *************************** */

		
		/* Client 1 reserves a ticket for the first performance of event */
        app.login("pacuestas","helloworld123", ModifiableDate.getModifiableDate());
		Client client1 = (Client)app.getCurrentUser();
		List<Event> events = app.searchEvents(SearchBy.TITLE, "BeRnArDa");
		Performance performance1=events.get(0).getPerformances(false).get(0);
		List <Ticket> tickets=performance1.getAvailableTickets(sittingAreaA1);

		tickets.get(0).reserve(client1);//reserve one available ticket
		
		System.out.println(client1+" reserves a ticket for "+performance1+" the reservation expires in "+ (app.getCurrentDate().plusDays(app.getTimeAfterReservation())));
		
		System.out.println("State of SittingArea1 after client1 reserves a ticket:");
		System.out.println(event1.getPerformances(false).get(0).printAreaState(sittingAreaA1));
        System.out.println("#######################################################");
		app.logout();

		/* Client 2 purchases 5 tickets for performance 1 */
		app.login("fernandes", "abcd123", ModifiableDate.getModifiableDate());
		Client client2 = (Client) app.getCurrentUser();
		CreditCard card2 =  new CreditCard("1234567891234567");

		List<Integer[]> checkSelection = new ArrayList<>(
				Arrays.asList(new Integer[] { 3, 5 }, new Integer[] { 3, 4 }, new Integer[] { 3, 2 },
						new Integer[] { 3, 1 }, new Integer[] { 3, 3 }));
		
		for(Integer[] index: checkSelection) {
			performance1.getSittingTicket(sittingAreaA1,index[0], index[1]).purchase(client2,card2);
		}

		System.out.println("State of SittingArea1 after client2 purchases 5 ticket:");
		System.out.println(event1.getPerformances(false).get(0).printAreaState(sittingAreaA1));
        System.out.println("#######################################################");

		app.logout();

		/*Client 3 purchases 3 tickets for performance1 with automatic selection */
		app.login("azamanis", "qwerty", ModifiableDate.getModifiableDate());
		Client client3 = (Client) app.getCurrentUser();
		CreditCard card3 = new CreditCard("1234567491234567");

		for(Ticket t: performance1.selectAutomatic(sittingAreaA1, 3, AutomaticSelectionType.FURTHEST)){
			t.purchase(client3, card3);
		}

		System.out.println("State of SittingArea1 after client3 purchases 3 tickets with automatic selection (furthest):");
		System.out.println(event1.getPerformances(false).get(0).printAreaState(sittingAreaA1));
        System.out.println("#######################################################");

		/*Client 3 reserves 4 tickets for performance 1 centered */

		for(Ticket t: performance1.selectAutomatic(sittingAreaA1, 4, AutomaticSelectionType.CENTERED)){
			t.reserve(client3);
		}

		System.out.println("State of SittingArea1 after client3 reserves 4 tickets with automatic selection (centered):");
		System.out.println(event1.getPerformances(false).get(0).printAreaState(sittingAreaA1));
		System.out.println("#######################################################");


		/**********************************************BUY WITH PASSES ************************************* */
		client2.buyPass(2021, sittingAreaA1, card2);
		client2.buyPass(2021,standingAreaA2,card2);
		client2.buyPass(2021,sittingAreaB,card2);
		/* client3 purchases an annual pass for */
		System.out.println(app.getCurrentUser()+" purchases an annual pass and uses it for buying a ticket");
		Performance performance3 = app.searchPerformances(SearchBy.TITLE, "CaSa", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 31)).get(0);

		client3.buyPass(2022, sittingAreaA1, card3);
		
		System.out.println(app.getCurrentUser()+" purchases a cycle pass and uses it for buying a ticket");
		client3.buyPass(app.getCycles().get(0),sittingAreaA1,card3);

		performance3.getAvailableTickets(sittingAreaA1).get(0).purchase(client3,new PassPay(client3.getPasses().get(0)));
		performance3.getAvailableTickets(sittingAreaA1).get(0).purchase(client3,new PassPay(client3.getPasses().get(1)));
		
		app.logout();//client3

		System.out.println("#######################################################\n");
		
		/***********************************************EXPIRATION OF RESERVATIONS********************************************* */

        System.out.println("In date "+app.getCurrentDate()+" there are "+performance1.getAvailableTickets().size()+" available tickets");

		
		/*We modify the date to simulate time has elapsed*/
		ModifiableDate.plusDays((int)app.getTimeAfterReservation()+1);


		/*Client1 logs in and his reservation has expired */
		
		app.login("pacuestas","helloworld123", ModifiableDate.getModifiableDate());// this updates the date
		app.setDate(app.getCurrentDate().plusDays(app.getTimeAfterReservation()+1));
		app.logout();

		System.out.println("In date "+app.getCurrentDate() +" there are "+performance1.getAvailableTickets().size()+" available tickets due to expiration of reservations");


		/**********************************************POSTPONING A PERFORMANCE ********************************************* */
	
		System.out.println("#######################################################");
		
        /*Admin postpones performance 3*/
		app.login(adminName, adminPass, ModifiableDate.getModifiableDate());
		performance3.postpone(LocalDate.of(2022,10,10));
        app.logout();
		System.out.println("Performance 3 has been postponed.");

		/*When client 3 logs in he receives a notification */
		app.login("azamanis","qwerty", ModifiableDate.getModifiableDate());
		System.out.println("\nNotification for: "+app.getCurrentUser()+" "+client3.getNotifications().get(0));
		app.logout();
		
		
		/**************************************************WAITING LIST *************************************************** */
		System.out.println("#######################################################");
		System.out.println("\nWaiting list of a performance and receiving notification");
		
		app.login("azamanis","qwerty", ModifiableDate.getModifiableDate());
		app.setMaxTicketsReservation(10000); //To simplify and reserve all the tickets at once
		app.logout();

		app.login("azamanis", "qwerty", ModifiableDate.getModifiableDate());
		tickets = performance3.getAvailableTickets();
        int size = tickets.size();

        for (int i = 0; i < size; i++) {
            tickets.get(i).reserve(client3);
        }
		app.logout();
		
		
		app.login("fernandes", "abcd123", ModifiableDate.getModifiableDate());
		client2 = (Client) app.getCurrentUser();
		client2.addToPerformanceWaitingList(performance3);
		app.logout();
		
		app.login("azamanis", "qwerty", ModifiableDate.getModifiableDate());
		
		tickets = new ArrayList<>();
		tickets.addAll(client3.getTickets());
		for(Ticket t: tickets){
			if((t.getPerformance()==performance3) && (t instanceof Reservation)){
				((Reservation)(t)).cancel(); //Cancel one reservation
				break;
			}
		}
		app.logout();

		app.login("fernandes","abcd123", ModifiableDate.getModifiableDate());
		System.out.println("\nNotification for "+app.getCurrentUser()+" "+client2.getNotifications().get(0));
		app.logout();


		/*********************************************CANCELATION OF PERFORMANCE*************************************** */
		System.out.println("#######################################################");
		System.out.println("Cancellation of performances");
		
		/* Admin cancels performance 3 */
		performance3.cancel();
		System.out.println("Performance 3 has been cancelled. ");

		app.login("azamanis", "qwerty", ModifiableDate.getModifiableDate());
		System.out.println("Notification for " +app.getCurrentUser() +" "+ client3.getNotifications().get(2)); //The two first were for the performance postponed



		/**************************************************** STATS ********************************************************* */
		System.out.println("#######################################################");
		app.setMaxTicketsPurchase(10000);
		
		//Buy one third of tickets for "Rock Band" in sittingAreaB
		Performance perf=event2.getPerformances(false).get(0);
		int n=0;
		
		for(Ticket t: perf.getAvailableTickets(sittingAreaB)) {
			if(n%3==0) {
				t.purchase(client2,card2);
			}
			n++;
		}
		
		// 25% for SittingArea A1
		for(Ticket t: perf.getAvailableTickets(sittingAreaA1)) {
			if(n%4==0) {
				t.purchase(client2,card2);
			}
			n++;
		}
		
		/*Once all performances have occurred the admin gets the stats and orders them */
		app.logout();
		                /************PERSISTANCE***************/
            /*write the app in the file*/
		app.writeToFile("./aux_files/saveFile/AppDemonstrator.dat");


		ModifiableDate.setDate(10, 10, 2024); /* some years pass, so that performances happen */
		
        
        /*read the application from the file, and the admin sees the stats of the app*/
		Application app2 = Application.getInstanceFromFile("./aux_files/saveFile/AppDemonstrator.dat");

        app2.login(adminName, adminPass, ModifiableDate.getModifiableDate());
		
		List <Stat> stats = app2.getSpecificStats();
        
        Stat.sortStats(stats, StatComparator.EVENT, false);
        System.out.println("\nStats grouped by event: \n");
        System.out.println(Stat.show(stats));
        
        System.out.println("--------------------------");
        Stat.sortStats(stats, StatComparator.PERFORMANCE, false);
        System.out.println("Stats grouped by performance: \n");
        System.out.println(Stat.show(stats));
        
        
        System.out.println("--------------------------");
        Stat.sortStats(stats, StatComparator.AREA, false);
        System.out.println("Stats grouped by area: \n");
        System.out.println(Stat.show(stats));

        app2.logout();

                /*write the app in the file to save the changes*/
		app2.writeToFile("./aux_files/saveFile/AppDemonstrator.dat");
	}

}


