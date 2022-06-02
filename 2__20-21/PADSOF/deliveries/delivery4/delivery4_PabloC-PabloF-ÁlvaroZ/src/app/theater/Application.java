package app.theater;

import java.io.*;
import java.util.*;
import java.time.*;

import app.theater.areas.*;
import app.theater.events.*;
import app.theater.events.cycles.*;
import app.theater.users.*;
import app.theater.searches.*;
import app.theater.stats.*;
import app.theater.performances.tickets.*;
import app.theater.performances.*;
import app.theater.util.*;

/**
 * Class representing the Theater Application
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 **/
public class Application implements Serializable{

	/**
	 * generated serial version ID
	 */
	private static final long serialVersionUID = -8810190980695830053L;

	private static Application INSTANCE; // singleton pattern

    private static final String SAVE_FILE_DESTINATION = "./aux_files/saveFile/saveFile.bin"; // binary file where the application is saved

	/* parameters */
	private int maxTicketsPurchase = 10;// max tickets per purchase
	private int maxTicketsReservation = 10;// max tickets per reservation
	private long timeAfterReservation = 3;// time after reservation (days)

	/* elements of the application */
	private String name;
	private RegUser currentUser; // user that is using the application
	private Admin admin; // administrator
	private List<Client> clients; // users

	private List<Event> events; // events
	private List<Cycle> cycles; // cycles of the app
	private CompositeArea areas; // composite area that contains all areas of the theater

	private LocalDate currentDate;

    private int ticketsIDCount = 0; // tickets id to keep track of the next ticket id

	/**
	 * Private constructor of Application
	 */
	private Application() {
		name = "ARCH Theater Hall";
		admin = new Admin("admin", "pass");
		clients = new ArrayList<>();
		events = new ArrayList<>();
		cycles = new ArrayList<>();
		areas = new CompositeArea("TheaterMainArea");
		currentDate = LocalDate.now();
	}

	/**
	 * Get instance (singleton pattern)
	 * 
	 * @return the instance of the application
	 */
	public static Application getInstance() {
        if(INSTANCE == null){
            INSTANCE = new Application();
        }
		return INSTANCE;
	}

    /**
     * Get an instance from a file (and update the instance 
     * of the class). This is the correct way of reading the
     * application from a file to use it later. If there is an error
     * this method returns null and does not change the instance of
     * the class.
     * @param file file where the application is stored
     * @return the new instance or null in case of error. 
     */
    public static Application getInstanceFromFile(String file){
        Application app = readFromFile(file);
        if(app != null){
            INSTANCE = app;
            return INSTANCE;
        }
        return null;
    }
    /**
     * Same as getInstanceFromFile, but the file is the default
     * @return the new instance from the default file or 
     * null in case of error.
     */
	public static Application getInstanceFromFile(){
        return getInstanceFromFile(SAVE_FILE_DESTINATION);
    }
	
	/**
	 * New instance of application so that each unit test in the tester files
	 * can have a new instance of the application.
	 * 
	 * @return a new instance of Application. 
	 */
	public static Application getNewInstanceForTests(){
		INSTANCE = new Application();
		return INSTANCE;
	}
	
	/**
	 * Whether the admin is logged in
	 * @return Whether the admin is logged in
	 */
	public boolean isAdminLogged() {
		return this.admin == this.currentUser;
	}

	/********* Getters and Setters *********/

    /**
     * Get a new id for the tickets and increment the counter
     * @return a new unique id for the tickets
     */
    public int getNewTicketID(){
        return this.ticketsIDCount++;
    }

	/**
	 * Getter: Max tickets in a purchase
	 * 
	 * @return the maxTicketsPurchase
	 */
	public int getMaxTicketsPurchase() {
		return maxTicketsPurchase;
	}

	/**
	 * Only admin: set max tickets in a purchase
	 * 
	 * @param maxTicketsPurchase the maxTicketsPurchase to set
	 */
	public void setMaxTicketsPurchase(int maxTicketsPurchase) {
		this.maxTicketsPurchase = maxTicketsPurchase;
	}

	/**
	 * Only admin: Max tickets in a reservation
	 * 
	 * @return the maxTicketsReservation
	 */
	public int getMaxTicketsReservation() {
		return maxTicketsReservation;
	}

	/**
	 * Only admin: set max tickets in a reservation
	 * 
	 * @param maxTicketsReservation the maxTicketsReservation to set
	 */
	public void setMaxTicketsReservation(int maxTicketsReservation) {
		this.maxTicketsReservation = maxTicketsReservation;
	}

	/**
	 * Only admin: get min time before performance (days)
	 * 
	 * @return the minTimeBeforePerformance
	 */
	public long getTimeAfterReservation() {
		return timeAfterReservation;
	}

	/**
	 * Only admin: set min time before performance
	 * 
	 * @param timeAfterReservation the minTimeBeforePerformance to set
	 */
	public void setTimeAfterReservation(long timeAfterReservation) {
		this.timeAfterReservation = timeAfterReservation;
	}

	/**
	 * The admin of the app
	 * 
	 * @return Admin
	 */
	public Admin getAdmin() {
		return this.admin;
	}

	/**
	 * The simple areas of the theater
	 * 
	 * @return the simple areas of the theater
	 */
	public List<SimpleArea> getSimpleAreas() {
		return Collections.unmodifiableList(this.areas.getSimpleAreas());
	}

    /**
	 * The events of the theater
	 * 
	 * @return the events of the theater
	 */
    public List<Event> getEvents(){
        return Collections.unmodifiableList(this.events);
    }

	/**
	 * The name of the theater
	 * 
	 * @return the name of the theater
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Only admin: Sets an annual price
	 * 
	 * @param area 		 area 
	 * @param price      price of annual pass in that area
	 */
	public void setAnnualPassPrice(Area area, double price) {
		area.setAnnualPassPrice(price);
	}

	/**
	 * Get the annual pass price in a simple area
	 * 
	 * @param area area
	 * @return the price of the pass in area
	 */
	public double getAnnualPassPrice(SimpleArea area) {
		return area.getAnnualPassPrice();
	}

	/**
	 * Add a new cycle to the theater application
	 * 
	 * @param c cycle to be added
	 * @return false if prices are not correctly set 
	 * in some performance of the cycle
	 */
	public boolean addCycle(Cycle c) {
		if(c.getEvents().isEmpty())
			return false;
		for(Event e:c.getEvents()) {
			if(!e.checkPrices()) {
				return false; // if prices are incorrect, do not add
			}
		}

        for(SimpleArea a:this.getSimpleAreas()){ //Check there is a price for every area
            if(!c.getPrices().containsKey(a)){
                return false;
            }
        }
		
		this.cycles.add(c);
		return true;
	}

	/**************** SEARCH **************/

	/**
	 * search Events that contains input in its title, director or author
	 * 
	 * @param searchBy attribute of event to filter
	 * @param searchOnly type of event to search (concert, play or dance)
	 * @param input substring to use in the match
	 * 
	 * @return the result of the search
	 */
	public List<Event> searchEvents(SearchBy searchBy, ISearchOnly searchOnly, String input) {

		List<Event> result = new ArrayList<>();

		for (Event e : events) {
			if (e.getATR(searchBy).toLowerCase().contains(input.toLowerCase())&& searchOnly.is(e)) {
                    result.add(e);
			}
		}

		return result;
	}
	
	/**
	 * Search Events that contains input in its title, director or author
	 * 
	 * @param searchBy    attribute of event to filter
	 * @param input substring to use in the match
	 * 
	 * @return the result of the search
	 */
	public List<Event> searchEvents(SearchBy searchBy, String input) {
		return searchEvents(searchBy, SearchOnly.ALL, input);
	}

	/**
	 * Search performances that contains input in its title, director or author.
	 * Search only in one type of events.
	 * With bounds for the date of the performance.
	 * 
	 * @param searchBy attribute of event to filter
	 * @param searchOnly type of event to search (concert, play or dance)
	 * @param input substring to use in the match
	 * @param d1 lower limit of dates to search
	 * @param d2 upper limit of dates to search
	 * 
	 * @return the result of the search
	 */
	public List<Performance> searchPerformances(SearchBy searchBy, ISearchOnly searchOnly, 
												String input, LocalDate d1, LocalDate d2)
	{
		List <Event> result = this.searchEvents(searchBy, searchOnly, input);
		List <Performance> output = new ArrayList<>();
		Interval interval = new Interval(d1,d2);

		for(Event event: result ){
			for(Performance performance: event.getPerformances()){
                if(interval.belongsToInterval(performance.getDate())){
                    output.add(performance);
                }
			}
		}
        return output;
	}
	
	/**
	 * Search performances that contains input in its title, director or author.
	 * With bounds for the date of the performance.
	 * 
	 * @param searchBy attribute of event to filter
	 * @param input substring to use in the match
	 * @param d1 lower limit of dates to search
	 * @param d2 upper limit of dates to search
	 * 
	 * @return the result of the search
	 */
	public List<Performance> searchPerformances(SearchBy searchBy, String input, 
												LocalDate d1, LocalDate d2)
	{
		return searchPerformances(searchBy, SearchOnly.ALL, input, d1, d2);
	}
	
	/**
	 * Search performances that contains input in its title, director or author.
	 * Search only in one type of events.
	 * 
	 * @param searchBy attribute of event to filter
	 * @param searchOnly type of event to search (concert, play or dance)
	 * @param input substring to use in the match
     *
	 * @return the result of the search
	 */
	public List<Performance> searchPerformances(SearchBy searchBy, 
												ISearchOnly searchOnly, 
												String input)
    {
		return searchPerformances(searchBy, searchOnly, input, LocalDate.MIN, LocalDate.MAX);
	}

    /** register and login clients */

    /**
     * Register a user 
     * @param userName username to register
     * @param password the password of the username 
     * @return false iff the username already exists
     */
	public boolean register(String userName, String password) {
		if(userName.equals(admin.getName())) {
			return false;
		}
		
		Client newUser = new Client(userName, password);

		if (this.clients.contains(newUser)) {
			return false;
		}
		this.clients.add(newUser);
		return true;
	}

    /**
     * The user with this username from the app
     * @param userName username of the user that is going 
     * to be returned
     * @return the user with this username or null if it
     * does not exist
     */
	public RegUser getUser(String userName) {
		for (RegUser r : this.clients) {
			if (r.getName().equals(userName)) {
				return r;
			}
		}
        if(admin.getName().equals(userName)){
            return admin;
        }
		return null;
	}

	/**
     * Logs out the current user
     * @return false iff there was no user logged in
     */
	public boolean logout() {
		if(this.currentUser == null) return false;
		
        RegUser user = this.currentUser;
    	this.currentUser = null;
    	
		if(user == this.admin){
			return true;
		}
		
		((Client)user).removeAvailableNotifications();	
		return true;
	}
	

    /**
     * Login of a user using its username and password
     * @param userName the username
     * @param password the password
     * 
     * @return true if the user was correctly logged in 
     * false if the user does not exist in the app, 
     * or if there is already a logged in user, 
     * or if the password is not correct.
     */
	public boolean login(String userName, String password) {
		return login (userName, password, LocalDate.now());
	}

    /**
     * Login of a user using its username and password 
     * (for the simulation using the current date field)
     * @param userName the username
     * @param password the password
     * @param currentDate date to update the date of the application 
     * (to be used only for simulation)
     * 
     * @return true if the user was correctly logged in 
     * false if the user does not exist in the app, 
     * or if there is already a logged in user, 
     * or if the password is not correct.
     */
	public boolean login(String userName, String password, LocalDate currentDate) {
		RegUser user = this.getUser(userName); //will return admin if username corresponds

		if (user == null || this.currentUser != null) {
			return false;
		}

		if (!user.getPassword().equals(password)) {
			return false;
		}

        this.currentDate = currentDate;
        this.currentUser = user;

		if(user instanceof Client){
			((Client)user).updateLogin();
		}
		
        updateReservations();

		return true;
	}

	/**
	 * Updates all of the reservations of the clients of the app
	 */
	private void updateReservations() {
		/*Check if the reservations have expired*/
        for(Client c: clients){
        	List <Ticket> tickets = new ArrayList<>();
        	tickets.addAll(c.getTickets());// we take a copy because it may be modified in the loop
			for(Ticket t: tickets){
                if(t instanceof Reservation){
                    ((Reservation)t).updateReservation();
                }
			}
        }
	}

    /**
     * Add an area to the app if there are no future performances
     * @param a area to add
     * @return true if the area was added
     */
	public boolean addArea(Area a) {
		/* not allowed to add areas if there are future performances */
		if (this.areFuturePerformances()) {
			return false;
		}
		this.areas.addArea(a);
		return true;
	}

    /**
     * Removes an area from the app if there are no future performances
     * @param a area to remove (of any kind)
     * @return true if the area was removed correctly 
     * (false if the area was not in the app or if there 
     * are future performances)
     */
    public boolean removeArea(Area a){
		if (this.areFuturePerformances()) {
			return false;
		}
	    return this.areas.removeArea(a);        
    }

    /**
     * True iff there are future performances in any event of the app
     * @return True iff there are future performances in any event of the app
     */
	public boolean areFuturePerformances() {
		for (Event e : this.events) {
			if (!e.getPerformances(false).isEmpty()) {
				return true;
			}
		}
		return false;
	}

    /**
     * Add a new event to the application.
     * The event is only added if it is not already in the application 
     * @param event event to add
     * @return true if added
     */
	public boolean addEvent(Event event) {
        if(!this.events.contains(event)){
		    this.events.add(event);
		    return true;
        }
        return false;
	}

    /**
     * The current user of the app
     * @return the current user
     */
	public RegUser getCurrentUser() {
		return this.currentUser;
	}

    /**
     * Set the date of the application (mainly for the 
     * ApplicationExampleUse.java)
     * @param newDate new date for the app
     */
	public void setDate(LocalDate newDate){
		this.currentDate=newDate;
	}

    /**
     * The date of the application
     * @return the current date of the application
     */
	public LocalDate getCurrentDate() {
		return this.currentDate;
	}
	
    /**
     * All cycles of the app
     * @return the list of cycles
     */
	public List<Cycle> getCycles(){
		return this.cycles;
	}

    /**
     * Returns true if a new performance can be set on the date
     * @param date the date where the performance is going to be 
     * @return true iff there are no performances on that date
     */
    public boolean canAddNewPerformance(LocalDate date){
        for(Event e:this.events){
            for(Performance p:e.getPerformances()){
                if(p.getDate().equals(date)){
                    return false;
                }
            }
        }
        return true;
    }

    /* STATS */
	/**
	 * Gets an stat for an event, a performance and an area
	 * @param event event of the stat
	 * @param performance performance of the stat
	 * @param area area of the stat
	 * @return the stat
	 */
    public Stat getSpecificStat(Event event, Performance performance, Area area){
        return performance.getStats(area);
    }

	/**
	 * Gets all the specific (with event, performance and area) stats for the application
	 * @return list with the stats
	 */
    public List<Stat> getSpecificStats(){
        List<Stat> list = new ArrayList<>();
        
        for(Event e:this.events){
            for(Performance p:e.getPerformances()){
                for(SimpleArea a:this.getSimpleAreas()){
                    list.add(getSpecificStat(e,p,a));
                }
            }
        }
        return list;
    }

    /**
     * List of stats of each event of the app
     * @return  the list of stats of each event of the app
     */
    public List<Stat> getStatsEvents(){
        List<Stat> list = new ArrayList<>();
        for(Event e: this.events){
            list.add(e.getStats());
        }
        return list;
    }

    /**
     * Stats from an area of the app 
     * @param area the area of which to search the stats
     * @return the stats from the area
     */
    public Stat getStats(Area area){
        int attendance = 0;
        int totalTickets = 0;
        double revenue = 0;
        int withPass = 0;

        for(Event e: this.events){
            Stat eventStat = e.getStats(area);
            attendance += eventStat.getAttendance();
            totalTickets += eventStat.getTotalTickets();
            withPass += eventStat.getPurchasedWithPass();
            revenue += eventStat.getRevenue();
        }
        return (new Stat(null, null, area, revenue, attendance, withPass, totalTickets));
    }

    /**
     * List of stats from each simple area 
     * 
     * @return list of stats from each simple area
     */
    public List<Stat> getStatsAreas(){
        List<Stat> list = new ArrayList<>();

        for(Area a: this.getSimpleAreas()){
            list.add(this.getStats(a));
        }

        return list;
    }

	/***************PERSISTANCE *************** */

	/**
	 * Writes/Saves the application in the default file
	 */
	public void writeToFile() {
		writeToFile(SAVE_FILE_DESTINATION);
	}

	/**
	 * Reads/Loads the application from the default file
	 * @return the application
	 */
    public static Application readFromFile(){
        return readFromFile(SAVE_FILE_DESTINATION);
    }

	/**
	 * Writes/Saves the application in the specified file
	 * @param file file where the application is going to be written
     * 
	 */
	public void writeToFile(String file){
		try{
            FileOutputStream outFile = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(outFile);

            out.writeObject(this);

            outFile.close();
			out.close();

		}catch(IOException e){
			System.out.println("Error when writing application to file (Application.writeToFile())");
        }
	}

	/**
	 * Reads/Loads the application from the specified file
	 * @param file file from where the application is going to be read
     * @return the read app
	 */
    public static Application readFromFile(String file){
        
		try{
            FileInputStream inputFile = new FileInputStream(file);
			ObjectInputStream input = new ObjectInputStream(inputFile);

			Application app = (Application)input.readObject();
	
            inputFile.close();
			input.close();

	        return app;

		}catch(IOException | ClassNotFoundException e){
			System.out.println("The application could not be read from the file. A new instance will be created.");
            return null;
        }
	}

    /**
     * Area with that name
     * @param name name of the area
     * @return the area with that name
     */
	public Area getArea(String name){
		if (name.equals(this.areas.getName())) return this.areas;
		return this.areas.getArea(name);
	}
	
	/**
	 * Search the event with that title
	 * @param title title of the event
	 * @return the event of the theater with the specified title or null if it does not exist
	 */
	public Event getEvent(String title) {
		for(Event e: this.events) {
			if(e.getTitle().equals(title)) return e;
		}
		
		return null;
	}
}
