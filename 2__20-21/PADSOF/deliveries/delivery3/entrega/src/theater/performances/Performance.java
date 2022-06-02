package app.theater.performances;

import java.util.*;

import app.theater.*;
import app.theater.areas.*;
import app.theater.areas.locations.*;
import app.theater.events.*;
import app.theater.util.*;
import app.theater.stats.*;
import app.theater.notifications.*;
import app.theater.paymentmethod.*;
import app.theater.performances.tickets.*;

import java.io.Serializable;
import java.time.*;

/**
 * Class Performance
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public class Performance implements Serializable{
	private static final long serialVersionUID = -2103405173679772753L;
	
	private LocalDate date;
	private Map<SimpleArea, List<Ticket>> tickets;
	private Event event;
	
	/**
	 * Constructor of Performance
	 * @param event       	event represented in the performance
	 * @param date			date of the performance
	 */
	public Performance(Event event, LocalDate date) {
		this.date=date;
		this.event=event;
		this.tickets=Ticket.ticketInit(event.getRestriction(), this);
	}
	
	/**
	 * Cancels a performance
	 */
	public void cancel() {
        CancelledNotification notification=new CancelledNotification(this.getName());
		 
		for(List<Ticket> ts:tickets.values()){
            for (Ticket t:ts) {
                t.remove(notification);
            }
        }

		this.event.removePerformance(this);
	}
	
	/**
	 * Postpones a performance
	 * @param date		new date of the performance
	 */
	public void postpone(LocalDate date) {
		PostponedNotification notification = new PostponedNotification(this);
		for(List<Ticket> ts:tickets.values()){
            for (Ticket t:ts) {
			    t.postpone(notification);
            }
        }
		this.date=date;
	}
	

	/**
	 * Removes a ticket from this performance
	 * @param t				Ticket to be removed
	 */
    public void removeTicket(Ticket t){
        this.tickets.get(t.getArea()).remove(t);
    }
	
    /**
     * Add a ticket to this performance
     * @param t				ticket to be added
     */
    public void addTicket(Ticket t){
        this.tickets.get(t.getArea()).add(t);
    }
	
    /**
     * Gets all available tickets for a performance
     * @return List with all the tickets available
     */
	public List<Ticket> getAvailableTickets() {
        List<Ticket> available = new ArrayList<>();

        for(List<Ticket> ts:tickets.values()){
            for (Ticket t:ts) {
                if(t.isAvailable()){
                    available.add(t);
                }
            }
        }

		return available;
	}

    /**
     * The list of available tickets of the performance in
     * a simple area
     * @param a simple area 
     * @return the list of available tickets in a
     */
	public List<Ticket> getAvailableTickets(SimpleArea a) {
        List<Ticket> available = new ArrayList<>();

        for(Ticket t:tickets.get(a)){
            if(t.isAvailable()){
                available.add(t);
            }
        }

		return available;
	}

    

    /**
     * Check if there are available tickets for a performance (in any area)
     * @return true iff there are tickets
     */
    public boolean areAvailableTickets(){
        return (!getAvailableTickets().isEmpty());
    }
    	
    /** 
     * The list of purchased tickets in a simple area
     * @param area simple area
     * @return the list of purchased tickets in area
     */
	public List<Ticket> getPurchasedTickets(SimpleArea area) {
        List<Ticket> purchased = new ArrayList<>();
        
        for(Ticket t:tickets.get(area)){
            if(t.isPurchased()){
                purchased.add(t);
            }
        }

		return purchased;
	}

    /** 
     * The list of tickets in a simple area
     * @param area simple area
     * @return the list of tickets in area
     */
	public List<Ticket> getTickets(SimpleArea area) {
        List<Ticket> list = new ArrayList<>();
        
        for(Ticket t:tickets.get(area)){
            list.add(t);
        }

		return list;
	}

	/**
	 * Gets a string representative for the performance
	 * @return String representative for the performance
	 */
	public String getName() {
		return this.event.getTitle()+", date: "
               + this.date.toString();
	}
	
	/**
	 * to string method
	 * @return the info of the performance
	 */
	@Override public String toString() {
		return this.getName();
	}

	/**
	 * Getter for the performance date
	 * @return LocalDate of the performance
	 */
	public LocalDate getDate(){
		return this.date;
	}

	/**
	 * Getter for the performance event
	 * @return Event of the performance
	 */
	public Event getEvent(){
		return this.event;
	}


    /*************Stats******** */

    /**
     * The stats of the performance for a particular area
     * @param area area to get the stats from 
     * @return The stats of the performance for area
     */
	public Stat getStats(Area area){
		int withPass = 0;
		double revenue = 0;
		int attendance = 0;
        int totalTickets=0;
		
        for(SimpleArea simpleArea: area.getSimpleAreas()){
            totalTickets += this.getTickets(simpleArea).size();
            for (Ticket t:getPurchasedTickets(simpleArea)){ 
                attendance += 1;
                if (t.getPaymentMethod() instanceof PassPay){
                    withPass += 1;
				}
                else{
                    revenue += this.getEvent().getPrice(simpleArea);
                }
            }
        }
		
		return new Stat(this.event, this, area, revenue, attendance, withPass, totalTickets);
	}


	/**
	 * Returns the stats for a given performance
	 * @return Stats for this performance
	 */
	public Stat getStats() {
		int attendance=0;
		double revenue=0;
		int withPass=0;
        int totalTickets=0;

		for(Area a: Application.getInstance().getSimpleAreas()){
            Stat areaStat = this.getStats(a);
			attendance += areaStat.getAttendance();
			withPass += areaStat.getPurchasedWithPass();
			revenue += areaStat.getRevenue();
            totalTickets += areaStat.getTotalTickets();
		}

		return (new Stat(this.event, this, null, revenue, attendance, withPass, totalTickets));
	}

    /**
     * The list of stats of all simple areas for this performance
     * @return the list of stats of all simple areas for this performance 
     */
    public List<Stat> getStatsAreas(){
        List<Stat> list = new ArrayList<>();
        
        for(SimpleArea a: Application.getInstance().getSimpleAreas()){
            list.add(this.getStats(a));
        }
        return list;
    }



    /*************** Selection of (sitting) tickets *********** */
  
	
	/**
	 * Select automatically a list of tickets from a sitting area for this performance
     * 
	 * @param area sitting area
	 * @param numTickets number of tickets to select
	 * @param type type of automatic selection
	 * @return the list of automatically selected tickets, or null if there are less than numTickets available tickets 
	 */
	public List<Ticket> selectAutomatic(SittingArea area, int numTickets, AutomaticSelectionType type) {

		if(getAvailableTickets().size() < numTickets){
			return null;
		}
        
        int centerCol = area.getColumns()/2;
        int centerRow = 0;
        
		switch(type){
            case FURTHEST:
                return selectAutomaticClosestToCenter(area, numTickets, Point.getFurthestPoint(this, area));

            case CENTERED:
                centerRow = area.getRows()/2;
                break;

            case CENTEREDLOWER:            
                centerRow = 0;
                break;
                
            case CENTEREDUPPER:
                centerRow = area.getRows()-1;
                break;   
            
            default:
                break;
        }

        return selectAutomaticClosestToCenter(area, numTickets, new Point(centerRow, centerCol));
	}
    

	/**
	 * Returns the ticket associated to a seat
	 * @param a sitting area of the seat
	 * @param row row of the seat
	 * @param column column of the seat
	 * @return the ticket if the ticket exists, null if not
	 */
    public Ticket getSittingTicket(SittingArea a, int row, int column){
        
        for(Ticket t:this.tickets.get(a)){
            Seat seat=((Seat)(t.getLocation()));
            if(seat.getColumn()==column && seat.getRow()==row){
                return t;
            }
        }
        return null;
    }

    /**
     * Selects the available tickets closest to the center provided in centerRow, centerCol. Using distance
     * @param area sitting area where to look 
     * @param numTickets number of tickets to select
     * @param center center
     * @return the list of tickets
     */
    private List<Ticket> selectAutomaticClosestToCenter(SittingArea area, int numTickets, Point center){

		List<Ticket> list = new ArrayList<>();
        
        Map<Integer, List<Ticket>> map = getMapDistanceAvailableToCenter(area, center);// map of available tickets with their distance

        int n = 0;
        int distance = 0;
   
		while(n < numTickets){
			List<Ticket> ticketsDistance = map.get(distance);
			while(ticketsDistance == null) {
				distance++;
				ticketsDistance = map.get(distance);
			}
            for(Ticket t: ticketsDistance){
                list.add(t);
                n++;
                if(n == numTickets){
                    break;
                }
            }
            distance++;
        }
        return list;
	}

    /**
     * The map of available tickets assigned to the distance that they are to the center
     * 
     * @param area area where to look
     * @param center center
     * @return The map of tickets assigned to the distance that they are to the center
     */
    public Map<Integer, List<Ticket>> getMapDistanceAvailableToCenter(SittingArea area, Point center){
        Map<Integer, List<Ticket>> map = new HashMap<>();
        
        for (Ticket t : this.getAvailableTickets(area)) {
            Seat seat = ((Seat) (t.getLocation()));
            int d = Math.abs(center.getColumn() - seat.getColumn()) 
                    + Math.abs(center.getRow() - seat.getRow()); // 1-norm
            
            if (!map.containsKey(d)){
                map.put(d,new ArrayList<>());
            }
            map.get(d).add(t);
        }
        return map;
    }

    /**
     * Method that prints the state of a sitting area with different
     * letters if the seats are available/purchased/reserved/disabled 
     * @param a area to be printed
     * @return string containing the state 
     */
    public String printAreaState(SittingArea a){
        List<Ticket> tickets = getTickets(a); 

        char[] ret = new char[a.getColumns()*(a.getRows()+1)];

        for(int i = 1; i <= a.getRows(); i++){
            ret[i * (a.getColumns()+1)-1] = '\n';
        }

        for(Ticket t : tickets) {
            Seat seat = (Seat) (t.getLocation());
            int row = seat.getRow();
            int column = seat.getColumn();
            char symbol;

            if (t.isPurchased()) {
                symbol = 'P';
            }  else if (t instanceof Reservation) {
                symbol = 'R';
            } else if (!t.isAvailable()) {
                symbol = 'X';
            }else {
                symbol = 'a';
            }

            ret[(row) * (a.getColumns() + 1) + column] = symbol;
        }
        return String.valueOf(ret);
    }

    /**
     * True if this performance has happened
     * @return True iff this performance has happened
     */
	public boolean hasHappened() {
		return this.date.isBefore(Application.getInstance().getCurrentDate());
	}

   
}