package app.theater.performances.tickets;

import app.theater.Application;
import app.theater.paymentmethod.*;

import java.time.*;

/**
 * Class Reservation
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */
public class Reservation extends Ticket {
	private static final long serialVersionUID = 5213882023229099513L;
	
	LocalDate limitDate;

    /**
     * Constructor of reservation
     * @param ticket ticket to be reserved
     * @param limitDate limit date to confirm the reservation
     */
	public Reservation(Ticket ticket, LocalDate limitDate) {
        super(ticket.performance, ticket.client, ticket.location, ticket.id);
        this.limitDate=limitDate;
	}

	/**
	 * Returns false as reserved tickets aren't available
	 * @return false
	 */
    @Override 
    public boolean isAvailable(){
        return false; // reserved tickets are never available
    }

    /**
     * Confirmation of the reservation: modifies the ticket list 
     * in the performance if successful.
     * 
     * @param p purchase method
     * @return true if the confirmation is successful
     */
    public boolean confirm(PaymentMethod p){
        if(!p.pay(this))
            return false;
        
        this.paymentMethod = p;
        
        this.performance.removeTicket(this);
        this.client.removeTicket(this);
        
        Ticket newTicket = new Ticket(this);
        this.performance.addTicket(newTicket);
        this.client.addTicket(newTicket);
        
        return true;
    }

    /**
     * Cancels the reservation: modifies the ticket list 
     * in the performance
     */
    public void cancel(){
        this.performance.removeTicket(this);
        this.client.removeTicket(this);
        this.client = null;

        Ticket newTicket = new Ticket(this);
        this.performance.addTicket(newTicket);
    }

    /**
     * Updates state reservation, checking whether it has expired. This
     * method is to be called on every reservation every time 
     * a user logs in
     */
    public void updateReservation(){
        if(limitDate.isBefore(Application.getInstance().getCurrentDate())){
            this.cancel();
        }
    }

    /**
     * Getter of the limit date to confirm the reservation
     * @return limit date of the reservation
     */
    public LocalDate getLimitDate(){return limitDate;}

    /**
     * Gets the name of the ticket
     * @return "Reserved Ticket"
     */
    @Override
    public String getTitle() {
        return "Reserved Ticket";
    }
    
    /**
     * Returns the information of the reservation
     * @return the information
     */
    public String displayReservation() {
    	return  "Reservation: "+ this.performance.getName() 
    			+".\nLocation: " + this.location
                +". Price: " + this.getPrice()
                +". ID: " + this.id + ".";
    }

   
}
