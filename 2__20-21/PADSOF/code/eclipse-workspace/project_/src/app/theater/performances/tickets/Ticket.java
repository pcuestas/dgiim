package app.theater.performances.tickets;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import app.theater.notifications.*;
import app.theater.paymentmethod.PaymentMethod;
import app.theater.performances.Performance;
import app.theater.users.*;
import app.gui.view.tables.ITabulizable;
import app.theater.Application;
import app.theater.areas.*;
import app.theater.areas.locations.*;
import es.uam.eps.padsof.tickets.*;

/**
 * Class representing the Tickets (spots of the theater)
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 **/
public class Ticket implements ITicketInfo, Serializable, ITabulizable{
	private static final long serialVersionUID = -9000329321948489501L;
	
	protected Performance performance;
    protected Client client;
    protected Location location;
    protected int id; // -1 if the ticket is disabled
    protected PaymentMethod paymentMethod;

    /**
     * Constructor of ticket
     * @param location location associated to the ticket
     * @param performance performance of the ticket
     */
    public Ticket(Location location, Performance performance){
        this.performance = performance;
        this.setNewId();
    	this.location = location;
    }
    /**
     * Constructor of ticket ONLY for its subclass(Reservation)
     * @param performance performance of the ticket
     * @param client client of the ticket
     * @param location location of the ticket
     * @param id id of the ticket
     */
    protected Ticket(Performance performance, Client client, Location location, int id) {
		this.performance = performance;
		this.client = client;
		this.location = location;
		this.id = id;
	}

    /**
     * Constructor of ticket ONLY for its subclass(Reservation)
     * @param r reservation ticket
     */
    protected Ticket(Reservation r) {
		this(r.performance,r.client,r.location,r.id);
        this.paymentMethod = r.paymentMethod;
	}

    /**
     * Method to set a new id to this ticket
     */
    private void setNewId(){
        this.id = Application.getInstance().getNewTicketID();
    }


	/**
     * Returns a map of simple area-list of tickets for every 
     * simple area in the theater application. This is called 
     * every time a performance is created.
     * 
     * @param restriction restriction of the event
     * @param performance performance 
     * @return the map of simple area-list of tickets for every 
     *         simple area in the theater application
     */
    public static Map<SimpleArea, List<Ticket>> ticketInit(double restriction, Performance performance){
        List<SimpleArea> areas = Application.getInstance().getSimpleAreas();
        Map<SimpleArea, List<Ticket>> tickets = new HashMap<>();

        for(SimpleArea a: areas){
            tickets.put(a,a.getTickets(restriction, performance));
        }
        return tickets;
    }

    /**
     * Returns whether this ticket is available to be purchased or reserved
     * @return true iff the ticket is available
     */
    public boolean isAvailable(){
        return ((this.client == null) && (this.id != -1)
                && !this.location.isDisabled(performance.getDate())); 
    }
    
    /**
     * Reservation of this ticket: modifies the ticket list 
     * in the performance if successful.
     * 
     * @param client client that makes the reservation
     * @return true if the reservation is successful
     */
    public boolean reserve(Client client){
        LocalDate limitDate = (Application.getInstance().getCurrentDate()).plusDays(Application.getInstance().getTimeAfterReservation());

        if (!client.canReserve(this.performance) 
            || !this.isAvailable() 
            || !limitDate.isBefore(this.performance.getDate())){
            return false;
        }

        this.client = client;
        this.performance.removeTicket(this);

        Reservation newReservation =  new Reservation(this, limitDate);
        this.performance.addTicket(newReservation);

        client.addTicket(newReservation);

        return true;
    }

    
    /**
     * Restricts the ticket (disabled). Purchased/Reserved
     * tickets cannot be restricted
     * @return true iff it can be disabled
     */
    public boolean restrict(){
    	if (client == null) {
            this.id = -1;// mark as restricted
            return true;
        }
    	return false;
    }
    
    /**
     * Updates the state of tickets if the performance was postponed
     * @param notification notification to be sent to clients
     */
    public void postpone(PostponedNotification notification) {    		
    	if(this.client!=null){
    		this.client.addNotification(notification);
    	} 
    	if(!this.isAvailable()
    	   && this.performance.getEvent().getRestriction() == 0.0
    	   && !this.location.isDisabled(this.performance.getDate())) {
    		//if there is no restriction and this is available in the new date, make available
    		this.setNewId();
    	}
    }
    
    /**
     * Called after cancellation of performance
     * @param notification notification to be sent to client
     */
    public void remove(CancelledNotification notification) {
    	if(client != null) {
    		client.addNotification(notification);
            client.removeTicket(this);
            if(paymentMethod != null){// purchased ticket
                paymentMethod.payback(this);
            }
    	}   
    }

    /**
     * Makes the purchase of this ticket by client u and 
     * payment method p. If it works correctly, a pdf
     * will be generated. 
     * 
     * @param u buyer
     * @param p payment method
     * @return true if the purchase is successful. False if
     * the ticket is not available or if the payment is not successful.
     */
    public boolean purchase(Client u, PaymentMethod p){
        if (!u.canPurchase(this.performance) || !isAvailable())
            return false;

        if(!p.pay(this))
            return false;
        
        this.paymentMethod = p;
        this.client = u;
        u.addTicket(this);

        return true;
    }

    /**
     * Whether the ticket is purchased
     * @return true iff the ticket has been purchased
     */
    public boolean isPurchased(){
        return (this.paymentMethod != null);
    }

    /**
     * Info of the ticket as a string (performance, location, and price)
     * @return string with the ticket's info
     */
    @Override
    public String toString(){
    	return "Ticket["+ this.performance.getName()
                +". Location: " + this.location
                +", Price: " + this.getPrice()
                +", ID: " + this.id
                +" ]";
    }
    
    /**
     * The price of the ticket
     * @return the price of the ticket
     */
    public double getPrice(){
        return (this.performance.getEvent().getPrice(this.location.getArea()));
    }
    
    /*Getters and setters*/

   /**
    * Performance of the ticket
    * @return performance of the ticket
    */
    public Performance getPerformance() {return performance;}

    /**
     * The payment method of the ticket
     * @return the payment method, null if it is not purchased
     */
    public PaymentMethod getPaymentMethod() {return paymentMethod;}
    
    /**
     * The area of the ticket
     * @return the area of the ticket
     */
    public SimpleArea getArea() {return location.getArea();}

    /**
     * Getter of the ticket's location
     * @return the ticket's location
     */
    public Location getLocation(){return this.location;}


    /* Implementation of ITicketInfo */
    /**
     * Gets the name of the theater
     * @return the name of the theater
     */
    @Override
    public String getTheaterName () { 
        return Application.getInstance().getName(); 
    }
    
    /**
     * Gets the event's name of the ticket
     * @return the name of the event
     */
    @Override
    public String getEventName () { 
        return this.performance.getEvent().getTitle(); 
    }
    
    /**
     * Gets the date of the performance of the ticket
     * @return the date
     */
    @Override
    public String getEventDate () { 
        return (this.performance.getDate()+""); 
    }
    
    /**
     * Gets the seat number the ticket is assigned to
     * @return string representing the seat
     */
    @Override
    public String getSeatNumber () { 
        return this.location.toString(); 
    }
    
    /**
     * Gets the location of the picture of the event associated to the ticket
     * @return the location of the picture
     */
    @Override
    public String getPicture () { 
        return this.performance.getEvent().getPicture(); 
    }
    
    /**
     * Gets the id of the ticket
     * @return the id
     */
    @Override
    public int getIdTicket() {
    	return this.id;
    }

    /**
     * Gets the name of the ticket
     * @return "Purchased Ticket"
     */
    @Override
    public String getTitle(){
        return "Purchased Ticket";
    }

    /**
     * Gets the reduced information of the ticket
     * @return the information
     */
    @Override
    public String getSmallInfo(){
        return this.getEventName() + "(" + this.getEventDate() 
        		+ ") Location: " + this.location;
    }

}
