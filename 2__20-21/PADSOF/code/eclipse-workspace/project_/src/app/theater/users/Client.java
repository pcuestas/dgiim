package app.theater.users;

import java.util.*;

import app.theater.Application;
import app.theater.areas.SimpleArea;
import app.theater.events.cycles.Cycle;
import app.theater.notifications.*;
import app.theater.passes.AnnualPass;
import app.theater.passes.CyclePass;
import app.theater.passes.Pass;
import app.theater.paymentmethod.*;
import app.theater.performances.Performance;
import app.theater.performances.tickets.*;

/**
 * Class Client
 * 
 * @author Pablo Cuesta Sierra (pablo.cuestas@estudiante.uam.es), Pablo
 *         Fernández Alegre (pablo.fernandezalegre@estudiante.uam.es) and Álvaro
 *         Zamanillo Sáez (alvaro.zamanillo@estudiante.uam.es)
 *
 */

public class Client extends RegUser {
	/**
	 * generated serial version UID
	 */
	private static final long serialVersionUID = -9054926234525034263L;
	
	private List<Notification> notifications = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private Set<Performance> waitingListPerformances = new HashSet<>();
    private List<Pass> passes=new ArrayList<>();

    /**
     * Constructor of client
     * @param username client's username
     * @param password client's password
     */
    public Client(String username, String password){
        super(username, password);
    }

    /**
     * Gets the tickets of the client
     * @return list with the tickets
     */
    public List<Ticket> getTickets(){
        return Collections.unmodifiableList(this.tickets);
    }

    /**
     * Gets the passes of the client
     * @return list of passes
     */
    public List<Pass> getPasses(){
        return Collections.unmodifiableList(this.passes);
    }

    /**
     * Adds a ticket to the client
     * @param ticket ticket to be added
     */
    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
    }

    /**
     * Removes a ticket from the client
     * @param ticket ticket to be removed
     */
    public void removeTicket(Ticket ticket){
        this.tickets.remove(ticket);
    }

    /**
     * Adds a notification to the client
     * @param notification notification to be added
     */
    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    /**
     * Removes the notification from the client 
     * @param n notification to delete
     */
    public void removeNotification(Notification n){
        this.notifications.remove(n);
    }

    /**
     * Checks if the client can buy one more ticket for the perfromance
     * @param p performance 
     * @return true iff the client can buy one more ticket for the perfromance
     */
    public boolean canPurchase(Performance p){
        int n=0;

        for(Ticket t: tickets){
            if(t.getPerformance()==p && t.isPurchased()){
                n++;
            }
        }

        return (n < Application.getInstance().getMaxTicketsPurchase());
    }

    /**
     * Checks if the client can reserve another ticket for a given performance
     * @param p performance
     * @return true iff the client can reserve another ticket
     */
    public boolean canReserve(Performance p){
        int n=0;

        for(Ticket t: tickets){
            if(t.getPerformance()==p && (!t.isPurchased())){
                n++;
            }
        }

        return (n < Application.getInstance().getMaxTicketsReservation());
    }

    /**
     * Returns the notifications of the client
     * @return list with the notifications
     */
    public List<Notification> getNotifications(){
        return Collections.unmodifiableList(this.notifications);
    }

    /**
     * Adds a client to the waiting list if there are not available tickets for it
     * @param p performance
     * @return true iff the client was added
     */
    public boolean addToPerformanceWaitingList(Performance p){
        if(p.areAvailableTickets()){
            return false;
        }

        if(waitingListPerformances.contains(p))
            return false;
            
        waitingListPerformances.add(p);
        return true;
    }

    /**
     * Gets the list of performances for which the client is in the waiting list
     * @return set with the performances
     */
    public Set<Performance> getWaitingListPerformances(){
    	return waitingListPerformances;
    }


    /**
     * Buys a cycle pass for the user
     * @param cycle cycle of the pass
     * @param area area of the pass
     * @param card credit card to pay the pass with
     * @return true iff the pas was bought correctly
     */
	public boolean buyPass(Cycle cycle, SimpleArea area, CreditCard card) {

		Pass pass = new CyclePass(area, cycle);

		// Check if the user already has this pass
		if (passes.contains(pass))
			return false;

		if (card.charge(pass.toString(), cycle.getPrices().get(area), true)) {
			this.passes.add(pass);
			return true;
		} else {
			return false;
		}
	}

	/**
     * Buys an annual pass for the user
     * @param year year of the pass
     * @param area area of the pass
     * @param card credit card to pay the pass with
     * @return true iff the pass was bought correctly
     */
    public boolean buyPass(int year, SimpleArea area, CreditCard card){
        Pass pass = new AnnualPass(area, year);
        
        //Check if the user already has this pass
        if(passes.contains(pass))
            return false;

        double price = Application.getInstance().getAnnualPassPrice(area);

        //Check if the area has a specified price for the annual pass
        if(price == -1)
            return false;
        
		if(card.charge(pass.toString(), price, true)){
	        this.passes.add(pass);
			return true;
		} else {
			return false;
		}
    }

    /**
     * Method toString
     * @return string
     */
    @Override
    public String toString(){
        return "Client: "+this.getName();
    }

    /**
     * Updates the client object when the client makes login
     */
	public void updateLogin() {
		List<Performance> perf = new ArrayList<>(this.waitingListPerformances); 
		/*Check if there are available tickets for the performances*/
		for(Performance p: perf){
			if(p.hasHappened()) {
				this.waitingListPerformances.remove(p);
			}else if(p.areAvailableTickets()){
                this.addNotification(new AvailableNotification(p));
            }
        }
	}

	/**
	 * Remove the AvailableNotifications of this client at logout
	 */
	public void removeAvailableNotifications() {
		List<Notification> list = new ArrayList<> (this.notifications);
		
		/*Delete AvailableNotifications*/
		for(Notification notification: list) {
			if(notification instanceof AvailableNotification){
				this.removeNotification(notification);
			}
		}
	}

}
